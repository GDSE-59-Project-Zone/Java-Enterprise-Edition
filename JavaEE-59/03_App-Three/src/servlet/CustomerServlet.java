package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String salary = req.getParameter("salary");
        String option = req.getParameter("option");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "sanu1234");
            switch (option) {
                case "delete":
                    PreparedStatement pstm1 = connection.prepareStatement("delete from Customer where id=?");
                    pstm1.setObject(1, id);
                    boolean execute = pstm1.executeUpdate() > 0;
                    break;
                case "add":
                    PreparedStatement pstm2 = connection.prepareStatement("insert into Customer values(?,?,?,?)");
                    pstm2.setObject(1, id);
                    pstm2.setObject(2, name);
                    pstm2.setObject(3, address);
                    pstm2.setObject(4, salary);
                    boolean execute2 = pstm2.executeUpdate() > 0;
                    break;
                case "update":
                    PreparedStatement pstm3 = connection.prepareStatement("update Customer set name=?,address=?,salary=? where id=?");
                    pstm3.setObject(4, id);
                    pstm3.setObject(1, name);
                    pstm3.setObject(2, address);
                    pstm3.setObject(3, salary);
                    boolean execute3 = pstm3.executeUpdate() > 0;
                    break;
            }
            resp.sendRedirect("customer.jsp");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
