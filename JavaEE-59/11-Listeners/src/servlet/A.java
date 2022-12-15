package servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/a")
public class A extends HttpServlet {

    public A(){
        System.out.printf("A constructor");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("A Initialed");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        int count = (int) servletContext.getAttribute("count");
        System.out.println(count);

        resp.getWriter().print("<h1>"+count+"</h1>");


        count=count+1;
        servletContext.setAttribute("count",count);

        System.out.println("A  do get Method Invoked");
    }

    @Override
    public void destroy() {
        System.out.println("A destroyed");
    }
}
