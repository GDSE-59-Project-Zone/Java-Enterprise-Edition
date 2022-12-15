package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/customer")
public class CustomerFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("CustomerFilter Initialised");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Customer Do Filter Method Invoked");
        String name = servletRequest.getParameter("name");
        if (name.equals("ijse")){
            servletResponse.getWriter().write("<h1>Customer Authorised</h1>");
        }else{
            servletResponse.getWriter().write("<h1>"+name+" : is Not Authorised</h1>");
        }

    }

    @Override
    public void destroy() {
        System.out.println("Customer Filter Destroyed");
    }
}
