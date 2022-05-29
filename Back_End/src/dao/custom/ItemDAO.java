package dao.custom;

import dao.CrudDAO;
import entity.Item;

import javax.json.JsonArrayBuilder;
import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<Item,String> {
    JsonArrayBuilder loadItemID() throws SQLException;
    JsonArrayBuilder selectCusData(String id) throws SQLException;
    boolean updateQty(String id,int qty) throws SQLException;

}
