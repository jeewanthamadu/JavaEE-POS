package dao.custom.impl;

import dao.custom.OrderDetailsDAO;
import entity.OrderDetails;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.SQLException;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
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
}
