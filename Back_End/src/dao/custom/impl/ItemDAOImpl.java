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
    public boolean delete(String id) {
        return false;
    }

    @Override
    public boolean update(Item item) {
        return false;
    }
}
