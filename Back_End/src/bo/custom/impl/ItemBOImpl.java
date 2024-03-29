package bo.custom.impl;

import bo.custom.ItemBO;
import dao.DAOFactory;
import dao.custom.impl.ItemDAOImpl;
import dto.ItemDTO;
import entity.Item;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.SQLException;

public class ItemBOImpl implements ItemBO{

ItemDAOImpl itemDAO = (ItemDAOImpl) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public JsonArrayBuilder getAllItems() throws SQLException {
        return itemDAO.getAll();
    }

    @Override
    public JsonObjectBuilder generateItemID() throws SQLException {
        return itemDAO.generateID();
    }

    @Override
    public JsonArrayBuilder searchItem(String id) throws SQLException {
        return itemDAO.search(id);
    }

    @Override
    public JsonArrayBuilder loadItemIds() throws SQLException {
        return itemDAO.loadItemID();
    }

    @Override
    public JsonArrayBuilder selectItemData(String id) throws SQLException {
        return itemDAO.selectCusData(id);
    }

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
