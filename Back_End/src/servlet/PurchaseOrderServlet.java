package servlet;

import javax.json.*;
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

@WebServlet(urlPatterns = "/placeOrder")
public class PurchaseOrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin","*");
        System.out.println("dddddd");
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
//        JsonArray jsonArray=reader.readArray();
        String oID = jsonObject.getString("oID");
        String oDate = jsonObject.getString("oDate");
        String oCusID = jsonObject.getString("oCusID");
        String oItemID = jsonObject.getString("oItemID");
        String oItemName = jsonObject.getString("oItemName");
        String oUnitPrice = jsonObject.getString("oUnitPrice");
        String oQty = jsonObject.getString("oQty");
        JsonArray oCartItems = jsonObject.getJsonArray("oCartItems");


        System.out.println(oID);
        System.out.println(oDate);
        System.out.println(oCusID);
        System.out.println(oItemID);
        System.out.println(oItemName);
        System.out.println(oUnitPrice);
        System.out.println(oQty);
        System.out.println(oCartItems);

        for (int i = 0; i < oCartItems.size(); i++) {

            System.out.println(oCartItems.getJsonArray(i).getString(0));
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaee_pos_app", "root", "12345678");

            PreparedStatement pstm = connection.prepareStatement("insert into orders values(?,?,?)");
            pstm.setObject(1, oID);
            pstm.setObject(2, oDate);
            pstm.setObject(3, oCusID);
            resp.addHeader("Content-Type", "application/json");

            if (pstm.executeUpdate() > 0) {
                for (int i = 0; i < oCartItems.size(); i++) {
                    PreparedStatement pstm2 = connection.prepareStatement("insert into order_detail values(?,?,?,?)");
                    pstm2.setObject(1, oCartItems.getJsonArray(i).getString(0));
                    pstm2.setObject(2, oID);
                    pstm2.setObject(3, oCartItems.getJsonArray(i).getString(3));
                    pstm2.setObject(4, oCartItems.getJsonArray(i).getString(2));

                    if (pstm2.executeUpdate()>0){
                        resp.addHeader("Content-Type", "application/json");
                        JsonObjectBuilder response = Json.createObjectBuilder();
                        response.add("state", "Ok");
                        response.add("message", "Successfully Added.!");
                        response.add("data", "");
                        resp.getWriter().print(response.build());
                    }

                }

//                JsonObjectBuilder response = Json.createObjectBuilder();
//                response.add("state", "Ok");
//                response.add("message", "Successfully Added.!");
//                response.add("data", "");
//                resp.getWriter().print(response.build());
            }

        } catch (ClassNotFoundException e) {
            resp.addHeader("Content-Type", "application/json");
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("state", "Error");
            response.add("message", e.getMessage());
            response.add("data", "");
            resp.setStatus(400);
            resp.getWriter().print(response.build());

        } catch (SQLException e) {
            resp.addHeader("Content-Type", "application/json");
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("state", "Error");
            response.add("message", e.getMessage());
            response.add("data", "");
            resp.setStatus(400);
            resp.getWriter().print(response.build());

        }

    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin","*");
        resp.addHeader("Access-Control-Allow-Headers","content-type");
    }
}
