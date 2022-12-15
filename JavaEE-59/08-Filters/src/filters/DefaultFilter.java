package filters;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")// Default Filter
public class DefaultFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Default Filter Initialized");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Default Filter Do Filter Invoked");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String servletPath = req.getServletPath();
        System.out.println(servletPath);

        if (servletPath.equals("/customer")) {
            //if this method invoked any-where inside doFilter method request will be forwarded to the requested servlet
            filterChain.doFilter(servletRequest,servletResponse);
        }else if(servletPath.equals("/item")){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            servletResponse.getWriter().write("<h1>Not Authorised</h1>");
        }



    }

    @Override
    public void destroy() {
        System.out.println("Default Filter Destroy method invoked");
    }
}
