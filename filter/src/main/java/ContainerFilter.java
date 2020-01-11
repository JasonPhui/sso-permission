
import com.alibaba.dubbo.common.utils.StringUtils;
import com.caucho.hessian.client.HessianProxyFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ph
 * @description 过滤器容器，可以同时提供多个功能，并通过spring注入如登录过滤和权限过滤器
 * @date 2020/1/11
 */
public class ContainerFilter extends ParamInitializing implements Filter {

    private static final String URL_FUZZY_MATCH = "/*";

    private boolean isServer = false;

    private String[] excludeUrls;

    private AbstractFilter[] filters;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //判断是否是登录服务端，如果是服务端的话，从request中直接获取loginUrl
        if(isServer) {
            loginUrl = filterConfig.getServletContext().getContextPath();
        } else if (StringUtils.isBlank(loginUrl)) {
            throw new IllegalArgumentException("loginUrl不能为null");
        }
        //从init params中获取excludeUrls, 使其在doFilter中进行url过滤
        String url = filterConfig.getInitParameter("loginUrl");
        if (StringUtils.isBlank(url)) {
            excludeUrls = url.split(",");
        }
        //判断AuthenticationService是否为null,若为null表示该访问模块并没有使用dubbo的rpc服务或者没有在spring中注入，此时可
        //通过hessian获取对应的认证服务
        if (authenticationRpcService == null) {
            try {
                authenticationRpcService = (AuthenticationRpcService)new HessianProxyFactory()
                        .create(AuthenticationRpcService.class, loginUrl + "/rpc/authenticationRpcService");
            }catch (MalformedURLException e) {
                throw new IllegalArgumentException("authenticationRpcService初始化失败");
            }
        }
        //实际过滤器列表验证、参数设置和执行过滤器的init方法
        if ( filters == null ||filters.length == 0 ) {
            throw new IllegalArgumentException("filters不能为null");
        }
        for (AbstractFilter filter: filters) {
            filter.setLoginUrl(loginUrl);
            filter.setAuthenticationRpcService(authenticationRpcService);

            filter.init(filterConfig);
        }

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        //判断访问的url是否需要排除
        if (isExcludeUrl(httpRequest.getContextPath())) {
            return;
        }

        HttpServletResponse httpResponse = (HttpServletResponse)response;
        //执行实际过滤器中的方法，判断是否可以访问
        for (AbstractFilter filter: filters) {
            if (!filter.isAccessAllow(httpRequest, httpResponse)) {
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private boolean isExcludeUrl(String url) {
        if (url == null || url.length() == 0) {
            return false;
        }
        //将列表根据指定的模式分解为两部分，在这里分为精准匹配和模糊匹配的url
        Map<Boolean, List<String>> urlMap = Arrays.stream(excludeUrls)
                .collect(Collectors.partitioningBy(u -> u.endsWith(URL_FUZZY_MATCH)));
        //先进行精准匹配过滤
        List<String> urls = urlMap.get(false);
        if (urls.contains(url)) {
            return true;
        }
        //再进行模糊匹配过滤
        urls = urlMap.get(true);
        for (String u: urls) {
            if (url.startsWith(u.replace(URL_FUZZY_MATCH, ""))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {
        if (filters == null || filters.length == 0) {
            return;
        }
        for (AbstractFilter filter: filters) {
            filter.destroy();
        }
    }

    public boolean isServer() {
        return isServer;
    }

    public void setServer(boolean server) {
        isServer = server;
    }

    public String[] getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(String[] excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    public AbstractFilter[] getAbstractFilters() {
        return filters;
    }

    public void setAbstractFilters(AbstractFilter[] abstractFilters) {
        this.filters = abstractFilters;
    }
}
