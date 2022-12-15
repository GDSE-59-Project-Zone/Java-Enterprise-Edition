package servlet;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    public CustomerServlet(){
        System.out.println("Customer Servlet Constructor Called");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("Customer Servlet Init Method Invoked");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            BasicDataSource bds= new BasicDataSource();
            bds.setDriverClassName("com.mysql.jdbc.Driver");
            bds.setUrl("jdbc:mysql://localhost:3306/company");
            bds.setUsername("root");
            bds.setPassword("sanu1234");

            //how many total connection you need inside the pool
            bds.setMaxTotal(2);

            //How many connections should be initialized from the total connections
            bds.setInitialSize(2);

            Connection connection = bds.getConnection();

            PreparedStatement psmt = connection.prepareStatement("select * from Customer");
            ResultSet rst = psmt.executeQuery();
            JsonArrayBuilder array = Json.createArrayBuilder();

            while (rst.next()) {
                JsonObjectBuilder object = Json.createObjectBuilder();
                object.add("id",rst.getString("id"));
                object.add("name",rst.getString("name"));
                object.add("address",rst.getString("address"));
                object.add("salary",rst.getDouble("salary"));
                array.add(object.build());
            }

            JsonObjectBuilder responseObject = Json.createObjectBuilder();
            responseObject.add("state","done");
            responseObject.add("message","Successfully done");
            responseObject.add("data",array.build());
            resp.getWriter().print(responseObject.build());

        }catch (SQLException e) {
            JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            jsonObject.add("state","error");
            jsonObject.add("message",e.getMessage());
            resp.getWriter().print(jsonObject.build());
            resp.setStatus(400);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String salary = req.getParameter("salary");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "sanu1234");
            PreparedStatement pstm2 = connection.prepareStatement("insert into Customer values(?,?,?,?)");
            pstm2.setObject(1, id);
            pstm2.setObject(2, name);
            pstm2.setObject(3, address);
            pstm2.setObject(4, salary);
            boolean output = pstm2.executeUpdate() > 0;
            if (output) {
                JsonObjectBuilder jsonObject = Json.createObjectBuilder();
                jsonObject.add("state","done");
                jsonObject.add("message","successful");
                resp.getWriter().print(jsonObject.build());
            }

        } catch (ClassNotFoundException e) {
            JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            jsonObject.add("state","error");
            jsonObject.add("message",e.getMessage());
            resp.getWriter().print(jsonObject.build());
            resp.setStatus(500);

        } catch (SQLException e) {
            JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            jsonObject.add("state","error");
            jsonObject.add("message",e.getMessage());
            resp.getWriter().print(jsonObject.build());
            resp.setStatus(400);
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "sanu1234");
            PreparedStatement pstm1 = connection.prepareStatement("delete from Customer where id=?");
            pstm1.setObject(1, id);
            boolean execute = pstm1.executeUpdate() > 0;
            JsonObjectBuilder jObject = Json.createObjectBuilder();
            if (execute) {
                jObject.add("state","done");
                jObject.add("message","Successfully Deleted..!");
            }else{
                jObject.add("state","error");
                jObject.add("message","No such Customer to Delete..!");
                resp.setStatus(400);
            }
            resp.getWriter().print(jObject.build());
        } catch (ClassNotFoundException e) {
            JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            jsonObject.add("state","error");
            jsonObject.add("message",e.getMessage());
            resp.getWriter().print(jsonObject.build());
            resp.setStatus(500);
        } catch (SQLException e) {
            JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            jsonObject.add("state","error");
            jsonObject.add("message",e.getMessage());
            resp.getWriter().print(jsonObject.build());
            resp.setStatus(400);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject customer = reader.readObject();
        String id = customer.getString("id");
        String name = customer.getString("name");
        String address = customer.getString("address");
        String salary = customer.getString("salary");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "sanu1234");
            PreparedStatement pstm3 = connection.prepareStatement("update Customer set name=?,address=?,salary=? where id=?");
            pstm3.setObject(4, id);
            pstm3.setObject(1, name);
            pstm3.setObject(2, address);
            pstm3.setObject(3, salary);
            boolean execute3 = pstm3.executeUpdate() > 0;
            JsonObjectBuilder responseObject = Json.createObjectBuilder();

            if (execute3) {
                responseObject.add("state","done");
                responseObject.add("message","Successfully Updated..!");
            }else{
                responseObject.add("state","Error");
                responseObject.add("message","No Customer For the Given ID..!");
            }
            resp.getWriter().print(responseObject.build());
        } catch (ClassNotFoundException e) {

            JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            jsonObject.add("state","error");
            jsonObject.add("message",e.getMessage());
            resp.getWriter().print(jsonObject.build());
            resp.setStatus(500);

        } catch (SQLException e) {

            JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            jsonObject.add("state","error");
            jsonObject.add("message",e.getMessage());
            resp.getWriter().print(jsonObject.build());
            resp.setStatus(400);
        }
    }


    @Override
    public void destroy() {
        System.out.println("Customer Servlet Destroyed");
    }
}
