package dao.custom.impl;

import dao.custom.OrderDAO;
import entity.Orders;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.SQLException;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean add(Orders orders) {
        return false;
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
        return null;
    }

    @Override
    public JsonArrayBuilder search(String id) throws SQLException {
        return null;
    }
}
