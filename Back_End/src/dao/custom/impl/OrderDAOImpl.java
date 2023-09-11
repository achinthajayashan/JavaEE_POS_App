package dao.custom.impl;

import dao.SQLUtil;
import dao.custom.OrderDAO;
import entity.Customer;
import entity.Orders;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public ArrayList<Orders> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Orders> allOrders = new ArrayList<>();
        ResultSet rst = SQLUtil.execute(connection,"SELECT * FROM Orders");
        while (rst.next()) {
            Orders orders = new Orders(rst.getString(1), rst.getDate(2), rst.getString(3));
            allOrders.add(orders);
        }
        return allOrders;
    }

    @Override
    public boolean save(Connection connection, Orders entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(connection,"INSERT INTO Orders VALUES (?,?,?)",entity.getOrderID(), entity.getDate(),entity.getCustomerID());
    }

    @Override
    public boolean update(Connection connection, Orders entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Connection connection, String Id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
