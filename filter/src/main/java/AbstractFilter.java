import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ph
 * @description 用于提供一些公用方法
 * @date 2020/1/11
 */
public abstract class AbstractFilter extends ParamInitializing implements Filter {

    /**
     * 判断请求是否可以访问核心方法，有实际过滤器实现。
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return boolean
     * @throws IOException 抛出异常
     */
    public abstract boolean isAccessAllow(HttpServletRequest request, HttpServletResponse response) throws IOException;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

    }

    @Override
    public void destroy() {

    }
}
