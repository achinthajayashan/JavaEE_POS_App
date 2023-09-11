package dao.custom.impl;

import dao.custom.CustomerDAO;
import entity.Customer;
import dao.SQLUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomers = new ArrayList<>();
        ResultSet rst = SQLUtil.execute(connection,"SELECT * FROM Customer");
        while (rst.next()) {
            Customer customer = new Customer(rst.getString(1), rst.getString(2), rst.getString(3),  rst.getInt(4));
            allCustomers.add(customer);
        }
        return allCustomers;
    }

    @Override
    public boolean save(Connection connection, Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(connection, "INSERT INTO Customer VALUES (?,?,?,?)", entity.getCusID(), entity.getCusName(), entity.getCusAddress(), entity.getCusTel());

    }

    @Override
    public boolean update(Connection connection, Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(connection, "UPDATE Customer SET customer_name=?, address=?, contact=? WHERE customer_ID=?",entity.getCusName() , entity.getCusAddress(), entity.getCusTel(), entity.getCusID());
    }

    @Override
    public boolean delete(Connection connection, String Id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(connection, "DELETE FROM Customer WHERE customer_ID=?", Id);
    }
}
