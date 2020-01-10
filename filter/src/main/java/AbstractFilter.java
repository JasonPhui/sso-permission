import javax.servlet.*;
import java.io.IOException;

/**
 * @author ph
 * @description 用于提供一些公用方法
 * @date 2020/1/11
 */
public abstract class AbstractFilter implements Filter {


    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

    }

    public void destroy() {

    }
}
