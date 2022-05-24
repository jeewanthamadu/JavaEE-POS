package dao.custom.impl;

import dao.custom.CustomerDAO;
import entity.Customer;
import servlet.CustomerServlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean add(Customer customer) throws SQLException {
        Connection conn = CustomerServlet.ds.getConnection();
        PreparedStatement pstm = conn.prepareStatement("INSERT INTO customer VALUES(?,?,?,?)");
        pstm.setObject(1,customer.getId());
        pstm.setObject(2,customer.getName());
        pstm.setObject(3,customer.getAddress());
        pstm.setObject(4,customer.getSalary());

        boolean b = pstm.executeUpdate() > 0;
        conn.close();
        return b;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Connection conn =CustomerServlet.ds.getConnection();
        PreparedStatement pstm = conn.prepareStatement("DELETE FROM customer WHERE id=?");
        pstm.setObject(1,id);
        boolean b = pstm.executeUpdate()>0;
        conn.close();
        return b;
    }

    @Override
    public boolean update(Customer customer) throws SQLException {
        Connection conn =CustomerServlet.ds.getConnection();
        PreparedStatement pstm = conn.prepareStatement("UPDATE customer SET name=?, address=?, salary=? WHERE id=?");
        pstm.setObject(1, customer.getName());
        pstm.setObject(2, customer.getAddress());
        pstm.setObject(3, customer.getSalary());

        pstm.setObject(4, customer.getId());

        boolean b = pstm.executeUpdate() > 0;
        conn.close();
        return b;
    }
}
