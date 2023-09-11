package servlet;

import bo.BOFactory;
import bo.custom.PurchaseOrderBO;
import dto.CustomerDTO;
import dto.OrderDTO;
import dto.OrderDetailsDTO;
import org.apache.commons.dbcp2.BasicDataSource;
import util.ResponseUtil;

import javax.json.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(urlPatterns = "/placeOrder")
public class PurchaseOrderServletAPI extends HttpServlet {

    PurchaseOrderBO purchaseOrderBO= (PurchaseOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PO);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext =getServletContext();
        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");
        PrintWriter writer = resp.getWriter();
        try (Connection connection=pool.getConnection()){

            JsonArrayBuilder allOrders = Json.createArrayBuilder();
            ArrayList<OrderDTO> all = purchaseOrderBO.getAllOrders(connection);

            for (OrderDTO orderDTO:all){
                JsonObjectBuilder order = Json.createObjectBuilder();
                order.add("oID",orderDTO.getOrderID());
                order.add("oDate", String.valueOf(orderDTO.getDate()));
                order.add("oCusID",orderDTO.getCustomerID());

                allOrders.add(order).build();


            }
            writer.println(allOrders.build());

        } catch (ClassNotFoundException e) {
            resp.getWriter().println(e.getMessage());
        } catch (SQLException e) {
            resp.getWriter().println(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


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
        String oQtyOnHnd =jsonObject.getString("oQtyOnHnd");
        JsonArray oCartItems = jsonObject.getJsonArray("oCartItems");

        ServletContext servletContext =getServletContext();
        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");

        try (Connection connection= pool.getConnection()){



            ArrayList<OrderDetailsDTO> orderDetailsDTOS = new ArrayList<>();

            for (int i = 0; i < oCartItems.size(); i++) {
                String iId= oCartItems.getJsonArray(i).getString(0);
                int iQty= Integer.parseInt(oCartItems.getJsonArray(i).getString(3));
                double iPrice= Double.parseDouble(oCartItems.getJsonArray(i).getString(2));

                System.out.println(iId);
                System.out.println(iQty);
                System.out.println(iPrice);
                orderDetailsDTOS.add(new OrderDetailsDTO(iId,oID,iQty,iPrice));
            }

            OrderDTO orderDTO = new OrderDTO(oID,oDate,oCusID,orderDetailsDTOS);
            if (purchaseOrderBO.purchaseOrder(connection,orderDTO)){
                resp.getWriter().print(ResponseUtil.getJson("OK","Successfully Added !"));
            }


        } catch (ClassNotFoundException e) {
            resp.getWriter().print(ResponseUtil.getJson("Error",e.getMessage()));

        } catch (SQLException e) {
            resp.getWriter().print(ResponseUtil.getJson("Error",e.getMessage()));

        }

    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

//    @Override
//    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.addHeader("Access-Control-Allow-Origin","*");
//        JsonReader reader = Json.createReader(req.getReader());
//        JsonObject jsonObject = reader.readObject();
//        String itemID = jsonObject.getString("itemID");
//        int newQty = Integer.parseInt(jsonObject.getString("newQty"));
//
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaee_pos_app", "root", "12345678");
//
//            PreparedStatement pstm3 = connection.prepareStatement("update item set qty_on_hnd=? where item_ID=?");
//            pstm3.setObject(2, itemID);
//            pstm3.setObject(1, newQty);
//            if (pstm3.executeUpdate() > 0) {
//                resp.addHeader("Content-Type", "application/json");
//                JsonObjectBuilder response = Json.createObjectBuilder();
//                response.add("state", "Ok");
//                response.add("message", "Successfully Updated.!");
//                response.add("data", "");
//                resp.getWriter().print(response.build());
//            }
//        } catch (ClassNotFoundException e) {
//            resp.addHeader("Content-Type", "application/json");
//            JsonObjectBuilder response = Json.createObjectBuilder();
//            response.add("state", "Error");
//            response.add("message", e.getMessage());
//            response.add("data", "");
//            resp.setStatus(400);
//            resp.getWriter().print(response.build());
//        } catch (SQLException e) {
//            resp.addHeader("Content-Type", "application/json");
//            JsonObjectBuilder response = Json.createObjectBuilder();
//            response.add("state", "Error");
//            response.add("message", e.getMessage());
//            response.add("data", "");
//            resp.setStatus(400);
//            resp.getWriter().print(response.build());
//        }
//    }
}
