package bo.custom.impl;

import bo.custom.ItemBO;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dto.CustomerDTO;
import dto.ItemDTO;
import entity.Customer;
import entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {

    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    @Override
    public ArrayList<ItemDTO> getAllItems(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> allItems= new ArrayList<>();
        ArrayList<Item> all = itemDAO.getAll(connection);
        for (Item c : all) {
            allItems.add(new ItemDTO(c.getItemCode(),c.getItemName(),c.getItemPrice(),c.getItemQty()));
        }
        return allItems;
    }

    @Override
    public boolean saveItem(Connection connection,ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return itemDAO.save(connection,new Item(itemDTO.getItemCode(),itemDTO.getItemName(),itemDTO.getItemPrice(),itemDTO.getItemQty()));
    }

    @Override
    public boolean updateItem(Connection connection,ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return itemDAO.update(connection,new Item(itemDTO.getItemCode(),itemDTO.getItemName(),itemDTO.getItemPrice(),itemDTO.getItemQty()));
    }

    @Override
    public boolean deleteItem(Connection connection,String itemCode) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(connection,itemCode);
    }
}
