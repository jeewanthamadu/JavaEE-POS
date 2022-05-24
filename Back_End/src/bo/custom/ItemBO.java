package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;

public interface ItemBO extends SuperBO {
    boolean addItem(ItemDTO itemDTO);
    boolean deleteItem(String id);
    boolean updateItem(ItemDTO itemDTO);
}
