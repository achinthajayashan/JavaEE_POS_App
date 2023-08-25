package servlet;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

//        String cusName = jsonObject.getString("cusName");
//        String cusAddress = jsonObject.getString("cusAddress");
//        String cusContact = jsonObject.getString("cusContact");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin","*");
        resp.addHeader("Access-Control-Allow-Headers","content-type");
    }
}
