package servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/b")
public class B extends HttpServlet {
    public B(){
        System.out.println("B Constructor");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("B init");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("B do Get");
        ServletContext servletContext = getServletContext();
        int count = (int) servletContext.getAttribute("count");
        resp.getWriter().print("<h1>"+count+"</h1>");

        count=count+1;
        servletContext.setAttribute("count",count);


    }

    @Override
    public void destroy() {
        System.out.println("B destroyed");
    }
}
