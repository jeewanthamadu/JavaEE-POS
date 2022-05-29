package dao.custom;

import dao.CrudDAO;
import entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailsDAO extends CrudDAO<OrderDetails,String> {

    boolean saveOrderDetails(String id, ArrayList<OrderDetails> orderDetails) throws SQLException;

}
