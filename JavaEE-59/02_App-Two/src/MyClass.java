import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/item")
public class MyClass extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Request received");

        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String qty = req.getParameter("qty");
        String price = req.getParameter("price");

        System.out.println(code);
        System.out.println(name);
        System.out.println(qty);
        System.out.println(price);

        PrintWriter writer = resp.getWriter();
        writer.write("<h1>Response Generated</h1>");
        writer.write("<table border='2' width='100%'>");
        writer.write("<thead>");
        writer.write("<tr><th>Code</th><th>Name</th><th>Qty</th><th>Price</th></tr>");
        writer.write("</thead>");
        writer.write("<tbody>");
        writer.write("<tr><td>"+code+"</td><td>"+name+"</td><td>"+qty+"</td><td>"+price+"</td></tr>");
        writer.write("</tbody>");
        writer.write("</table>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Do Post Method Invoked");

        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String qty = req.getParameter("qty");
        String price = req.getParameter("price");

        System.out.println(code);
        System.out.println(name);
        System.out.println(qty);
        System.out.println(price);

        PrintWriter writer = resp.getWriter();
        writer.write("<h1>Response Generated</h1>");
        writer.write("<table border='2' width='100%'>");
        writer.write("<thead>");
        writer.write("<tr><th>Code</th><th>Name</th><th>Qty</th><th>Price</th></tr>");
        writer.write("</thead>");
        writer.write("<tbody>");
        writer.write("<tr><td>"+code+"</td><td>"+name+"</td><td>"+qty+"</td><td>"+price+"</td></tr>");
        writer.write("</tbody>");
        writer.write("</table>");
    }
}
