package dao.custom.impl;

import dao.DAOFactory;
import dao.custom.ItemDAO;
import dao.custom.OrderDetailsDAO;
import entity.OrderDetails;
import servlet.OrderServlet;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {

    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public boolean add(OrderDetails orderDetails) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public boolean update(OrderDetails orderDetails) {
        return false;
    }

    @Override
    public JsonArrayBuilder getAll() throws SQLException {
        return null;
    }

    @Override
    public JsonObjectBuilder generateID() throws SQLException {
        return null;
    }

    @Override
    public JsonArrayBuilder search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean saveOrderDetails(String id, ArrayList<OrderDetails> orderDetails) throws SQLException {
        Connection connection = OrderServlet.ds.getConnection();
        for (OrderDetails item : orderDetails) {
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO orderdetails VALUES(?,?,?,?,?)");
            pstm.setObject(1,item.getoId() );
            pstm.setObject(2,item.getItemCode() );
            pstm.setObject(3,item.getQty());
            pstm.setObject(4,item.getUnitPrice());
            pstm.setObject(5,item.getTotal());

            if (pstm.executeUpdate() > 0) {
                if (itemDAO.updateQty(item.getItemCode(),item.getQty())) {

                } else {
                    connection.close();
                    return false;
                }
            } else {
                connection.close();
                return false;
            }

        }
connection.close();
        return true;
    }
}
