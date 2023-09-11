package servlet;

import bo.BOFactory;
import bo.custom.ItemBO;
import dto.CustomerDTO;
import dto.ItemDTO;
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

@WebServlet(urlPatterns = "/item")
public class ItemServletAPI extends HttpServlet {

    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext =getServletContext();
        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");
        try(Connection connection = pool.getConnection()) {

            PrintWriter writer = resp.getWriter();

            JsonArrayBuilder allItems = Json.createArrayBuilder();

            ArrayList<ItemDTO> all = itemBO.getAllItems(connection);

            for (ItemDTO itemDTO:all) {

                JsonObjectBuilder item = Json.createObjectBuilder();

                item.add("code",itemDTO.getItemCode());
                item.add("desc",itemDTO.getItemName());
                item.add("unitPrice",itemDTO.getItemPrice());
                item.add("qty",itemDTO.getItemQty());

                allItems.add(item.build());
            }

            writer.print(allItems.build());


        } catch (ClassNotFoundException e) {
            resp.getWriter().println(e.getMessage());
        } catch (SQLException e) {
            resp.getWriter().println(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("txtItemID");
        String description = req.getParameter("txtItemName");
        double unitPrice = Double.parseDouble(req.getParameter("txtItemPrice"));
        int qty = Integer.parseInt(req.getParameter("txtItemQty"));

        ServletContext servletContext =getServletContext();
        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");

        try (Connection connection = pool.getConnection()){
            ItemDTO itemDTO = new ItemDTO(code,description,unitPrice,qty);
            if (itemBO.saveItem(connection,itemDTO)) {
                resp.getWriter().print(ResponseUtil.getJson("OK","Successfully Updated !"));
            }

        } catch (ClassNotFoundException e) {
            resp.getWriter().print(ResponseUtil.getJson("Error",e.getMessage()));

        } catch (SQLException e) {
            resp.getWriter().print(ResponseUtil.getJson("Error",e.getMessage()));

        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String code = jsonObject.getString("code");
        String desc = jsonObject.getString("desc");
        double unitPrice = Double.parseDouble(jsonObject.getString("unitPrice"));
        int qty = Integer.parseInt(jsonObject.getString("qty"));

        ServletContext servletContext =getServletContext();
        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");

        try (Connection connection= pool.getConnection()){
            ItemDTO itemDTO= new ItemDTO(code,desc,unitPrice,qty);
           if (itemBO.updateItem(connection,itemDTO)) {
               resp.getWriter().print(ResponseUtil.getJson("OK","Successfully Updated !"));
            }
        } catch (ClassNotFoundException e) {
            resp.getWriter().print(ResponseUtil.getJson("Error",e.getMessage()));
        } catch (SQLException e) {
            resp.getWriter().print(ResponseUtil.getJson("Error",e.getMessage()));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemID = req.getParameter("code");

        ServletContext servletContext =getServletContext();
        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");

        try (Connection connection= pool.getConnection()){
            if (itemBO.deleteItem(connection,itemID)) {
                resp.getWriter().print(ResponseUtil.getJson("OK","Successfully Deleted !"));
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

}
