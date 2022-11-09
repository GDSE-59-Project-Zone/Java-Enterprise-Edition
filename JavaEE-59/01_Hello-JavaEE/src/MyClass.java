import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/customer")
public class MyClass extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id=req.getParameter("id");
        String name=req.getParameter("name");
        String address=req.getParameter("address");
        String salary=req.getParameter("salary");
        System.out.println(id+" "+name+ " "+address+" "+salary);

        PrintWriter writer = resp.getWriter();
        writer.write("<h1>Hello I am your response</h1>");
        writer.write("<ul>");
        writer.write("<li>"+id+"</li>");
        writer.write("<li>"+name+"</li>");
        writer.write("<li>"+address+"</li>");
        writer.write("<li>"+salary+"</li>");
        writer.write("</ul>");

    }
}
