package bo.custom.impl;

import bo.custom.ItemBO;
import dao.DAOFactory;
import dao.custom.impl.ItemDAOImpl;
import dto.ItemDTO;
import entity.Item;

import java.sql.SQLException;

public class ItemBOImpl implements ItemBO{

ItemDAOImpl itemDAO = (ItemDAOImpl) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public boolean addItem(ItemDTO itemDTO) throws SQLException {
        Item item = new Item(itemDTO.getCode(), itemDTO.getDescription(), itemDTO.getQtyOnHand(), itemDTO.getUnitPrice());
        return itemDAO.add(item);
    }

    @Override
    public boolean deleteItem(String id) throws SQLException {
        return itemDAO.delete(id);
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO) throws SQLException {
        return itemDAO.update(new Item(itemDTO.getCode(), itemDTO.getDescription(),itemDTO.getQtyOnHand(),itemDTO.getUnitPrice()));

    }
}
