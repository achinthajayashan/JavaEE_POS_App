package servlet;

import bo.BOFactory;
import bo.custom.CustomerBO;
import dto.CustomerDTO;
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

@WebServlet(urlPatterns = "/customer")
public class CustomerServletAPI extends HttpServlet {

    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext =getServletContext();
        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");
        try (Connection connection = pool.getConnection()){

            PrintWriter writer = resp.getWriter();

            JsonArrayBuilder allCustomers = Json.createArrayBuilder();

            ArrayList<CustomerDTO> all = customerBO.getAllCustomers(connection);

            for (CustomerDTO customerDTO:all){
                JsonObjectBuilder customer = Json.createObjectBuilder();

                customer.add("id",customerDTO.getCusID());
                customer.add("name",customerDTO.getCusName());
                customer.add("address",customerDTO.getCusAddress());
                customer.add("contact",customerDTO.getCusTel());

                allCustomers.add(customer.build());
            }
            writer.print(allCustomers.build());


        } catch (ClassNotFoundException e) {
            resp.getWriter().println(e.getMessage());
        } catch (SQLException e) {
            resp.getWriter().println(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusID = req.getParameter("cusID");
        String cusName = req.getParameter("cusName");
        String cusAddress = req.getParameter("cusAddress");
        int cusContact = Integer.parseInt(req.getParameter("cusContact"));

        ServletContext servletContext =getServletContext();
        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");
        try (Connection connection = pool.getConnection()){
            CustomerDTO customerDTO = new CustomerDTO(cusID,cusName,cusAddress,cusContact);

            if (customerBO.saveCustomer(connection,customerDTO)) {
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
        String cusID = jsonObject.getString("cusID");
        String cusName = jsonObject.getString("cusName");
        String cusAddress = jsonObject.getString("cusAddress");
        int cusContact = Integer.parseInt(jsonObject.getString("cusContact"));

        ServletContext servletContext =getServletContext();
        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");

        try (Connection connection = pool.getConnection()){

            CustomerDTO customerDTO =new CustomerDTO(cusID,cusName,cusAddress,cusContact);
            if (customerBO.updateCustomer(connection,customerDTO)) {
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
        String cusID = req.getParameter("cusID");

        ServletContext servletContext =getServletContext();
        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");

        try (Connection connection = pool.getConnection()){
            if (customerBO.deleteCustomer(connection,cusID)) {

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