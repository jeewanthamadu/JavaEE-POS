package dao.custom.impl;

import dao.custom.OrderDAO;
import entity.Orders;
import servlet.OrderServlet;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAOImpl implements OrderDAO {

    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();


    @Override
    public boolean add(Orders order) throws SQLException {
        Connection connection = OrderServlet.ds.getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO orders VALUES(?,?,?,?,?,?)");
        pstm.setObject(1, order.getoId());
        pstm.setObject(2, order.getDate());
        pstm.setObject(3, order.getCustomerId());
        pstm.setObject(4, order.getDiscount());
        pstm.setObject(5, order.getTotal());
        pstm.setObject(6, order.getSubTotal());

        boolean b = pstm.executeUpdate() > 0;
        connection.close();
        return b;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public boolean update(Orders orders) {
        return false;
    }

    @Override
    public JsonArrayBuilder getAll() throws SQLException {
        return null;
    }

    @Override
    public JsonObjectBuilder generateID() throws SQLException {
        Connection conn = OrderServlet.ds.getConnection();
        ResultSet rst = conn.prepareStatement("SELECT oid FROM orders ORDER BY oid DESC LIMIT 1").executeQuery();
        if (rst.next()) {
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId += 1;
            if (tempId < 10) {
                objectBuilder.add("oId", "O00-00" + tempId);
            } else if (tempId < 100) {
                objectBuilder.add("oId", "O00-0" + tempId);
            } else if (tempId < 1000) {
                objectBuilder.add("oId", "O00-" + tempId);
            }
        } else {
            objectBuilder.add("oId", "O00-000");
        }
        conn.close();
        return objectBuilder;
    }

    @Override
    public JsonArrayBuilder search(String id) throws SQLException {
        return null;
    }
}
