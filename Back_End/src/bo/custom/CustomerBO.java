package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    public ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException, ClassNotFoundException;

    public boolean saveCustomer(Connection connection, CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

    public boolean updateCustomer(Connection connection ,CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;
    public boolean deleteCustomer(Connection connection,String customerID) throws SQLException, ClassNotFoundException;
}
