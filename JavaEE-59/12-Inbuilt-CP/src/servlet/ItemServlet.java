package servlet;

import javax.annotation.Resource;
import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {

    @Resource(name = "java:comp/env/db/pool")
    DataSource ds;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try ( Connection connection = ds.getConnection()) {
            PreparedStatement psmt = connection.prepareStatement("select * from Item");
            ResultSet rst = psmt.executeQuery();
            JsonArrayBuilder array = Json.createArrayBuilder();

            while (rst.next()) {
                JsonObjectBuilder object = Json.createObjectBuilder();
                object.add("code",rst.getString("code"));
                object.add("description",rst.getString("description"));
                object.add("qtyOnHand",rst.getString("qtyOnHand"));
                object.add("unitPrice",rst.getDouble("unitPrice"));
                array.add(object.build());
            }

            JsonObjectBuilder responseObject = Json.createObjectBuilder();
            responseObject.add("state","done");
            responseObject.add("message","Successfully done");
            responseObject.add("data",array.build());
            resp.getWriter().print(responseObject.build());

        } catch (SQLException e) {

            JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            jsonObject.add("state","error");
            jsonObject.add("message",e.getMessage());
            resp.getWriter().print(jsonObject.build());
            resp.setStatus(400);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        String name = req.getParameter("description");
        String qtyOnHand = req.getParameter("qtyOnHand");
        String unitPrice = req.getParameter("unitPrice");
        try ( Connection connection = ds.getConnection()) {
            PreparedStatement pstm2 = connection.prepareStatement("insert into Item values(?,?,?,?)");
            pstm2.setObject(1, code);
            pstm2.setObject(2, name);
            pstm2.setObject(3, qtyOnHand);
            pstm2.setObject(4, unitPrice);
            boolean output = pstm2.executeUpdate() > 0;
            if (output) {
                JsonObjectBuilder jsonObject = Json.createObjectBuilder();
                jsonObject.add("state","done");
                jsonObject.add("message","successful");
                resp.getWriter().print(jsonObject.build());
            }

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
        String id = req.getParameter("code");

        try ( Connection connection = ds.getConnection()) {
            PreparedStatement pstm1 = connection.prepareStatement("delete from Item where code=?");
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
        }catch (SQLException e) {
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
        String code = customer.getString("code");
        String name = customer.getString("description");
        String qtyOnHand = customer.getString("qtyOnHand");
        String unitPrice = customer.getString("unitPrice");
        try ( Connection connection = ds.getConnection()) {
            PreparedStatement pstm3 = connection.prepareStatement("update Item set description=?,qtyOnHand=?,unitPrice=? where code=?");
            pstm3.setObject(4, code);
            pstm3.setObject(1, name);
            pstm3.setObject(2, qtyOnHand);
            pstm3.setObject(3, unitPrice);
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
        } catch (SQLException e) {

            JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            jsonObject.add("state","error");
            jsonObject.add("message",e.getMessage());
            resp.getWriter().print(jsonObject.build());
            resp.setStatus(400);
        }
    }
}
