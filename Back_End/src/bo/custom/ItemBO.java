package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;

import java.sql.SQLException;

public interface ItemBO extends SuperBO {
    boolean addItem(ItemDTO itemDTO) throws SQLException;
    boolean deleteItem(String id) throws SQLException;
    boolean updateItem(ItemDTO itemDTO) throws SQLException;
}
