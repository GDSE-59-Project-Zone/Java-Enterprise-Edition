package servlet;

import model.CustomerDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ArrayList<CustomerDTO> allCustomers = new ArrayList();
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "sanu1234");
            PreparedStatement psmt = connection.prepareStatement("select * from Customer");
            ResultSet rst = psmt.executeQuery();
            while (rst.next()) {
                String id = rst.getString("id");
                String name = rst.getString("name");
                String address = rst.getString("address");
                double salary = rst.getDouble("salary");
                allCustomers.add(new CustomerDTO(id, name, address, salary));
            }

            //json format
            String customersJSON = "[";
            for (CustomerDTO customer : allCustomers) {
                String id = customer.getId();
                String name = customer.getName();
                String address = customer.getAddress();
                double salary = customer.getSalary();
                String cusOb="{\"id\":\""+id+"\",\"name\":\""+name+"\",\"address\":\""+address+"\",\"salary\":"+salary+"},";
                customersJSON+=cusOb;
            }
            String substring = customersJSON.substring(0, customersJSON.length() - 1);
            substring+="]";

            resp.getWriter().write(substring);


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
