package bo.custom;

import bo.SuperBO;
import dao.custom.OrderDAO;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface PurchaseOrderBO extends SuperBO {

    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;

    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

    public boolean purchaseOrder(Connection connection,OrderDTO dto)throws SQLException, ClassNotFoundException;

    public ArrayList<OrderDTO> getAllOrders(Connection connection) throws SQLException, ClassNotFoundException;
}
