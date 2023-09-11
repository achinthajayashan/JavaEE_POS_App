package bo.custom.impl;

import bo.custom.CustomerBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dto.CustomerDTO;
import entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    @Override
    public ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomers= new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll(connection);
        for (Customer c : all) {
            allCustomers.add(new CustomerDTO(c.getCusID(),c.getCusName(),c.getCusAddress(),c.getCusTel()));
        }
        return allCustomers;
    }

    @Override
    public boolean saveCustomer(Connection connection, CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.save(connection,new Customer(customerDTO.getCusID(),customerDTO.getCusName(),customerDTO.getCusAddress(),customerDTO.getCusTel()));
    }

    @Override
    public boolean updateCustomer(Connection connection, CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.update(connection,new Customer(customerDTO.getCusID(),customerDTO.getCusName(),customerDTO.getCusAddress(),customerDTO.getCusTel()));
    }

    @Override
    public boolean deleteCustomer(Connection connection, String customerID) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(connection,customerID);
    }
}
