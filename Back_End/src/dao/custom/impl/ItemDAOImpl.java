package dao.custom.impl;

import dao.custom.ItemDAO;
import entity.Item;
import servlet.ItemServlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean add(Item item) throws SQLException {
       Connection conn = ItemServlet.ds.getConnection();
        PreparedStatement pstm = conn.prepareStatement("INSERT INTO item VALUES(?,?,?,?)");
        pstm.setObject(1,item.getCode());
        pstm.setObject(2,item.getDescription());
        pstm.setObject(3,item.getQtyOnHand());
        pstm.setObject(4,item.getUnitPrice());

        boolean b = pstm.executeUpdate()>0;
        conn.close();
        return b;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Connection connection =ItemServlet.ds.getConnection();
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM item WHERE code=?");
        pstm.setObject(1, id);

        boolean b = pstm.executeUpdate() > 0;
        connection.close();
        return b;
    }

    @Override
    public boolean update(Item item) throws SQLException {

        Connection connection =ItemServlet.ds.getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE item SET description=?, qtyOnHand=?, unitPrice=? WHERE code=?");
        pstm.setObject(1, item.getDescription());
        pstm.setObject(2,item.getQtyOnHand());
        pstm.setObject(3, item.getUnitPrice());
        pstm.setObject(4, item.getCode());


        boolean b = pstm.executeUpdate() > 0;
        connection.close();
        return b;
    }
}
