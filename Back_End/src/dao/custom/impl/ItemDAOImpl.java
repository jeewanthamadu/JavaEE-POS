package dao.custom.impl;

import dao.custom.ItemDAO;
import entity.Item;
import servlet.ItemServlet;
import servlet.OrderServlet;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemDAOImpl implements ItemDAO {
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
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

    @Override
    public JsonArrayBuilder getAll() throws SQLException {
        Connection connection = ItemServlet.ds.getConnection();
        ResultSet resultSet = connection.prepareStatement("select * from item").executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            int qty = resultSet.getInt(3);
            double price = resultSet.getDouble(4);



            objectBuilder.add("itemId", id);
            objectBuilder.add("itemName", name);
            objectBuilder.add("itemQty", qty);
            objectBuilder.add("itemPrice", price);

            arrayBuilder.add(objectBuilder.build());
        }

        connection.close();
        return arrayBuilder;
    }

    @Override
    public JsonObjectBuilder generateID() throws SQLException {
        Connection connection = ItemServlet.ds.getConnection();
        ResultSet rst = connection.prepareStatement("SELECT code FROM item ORDER BY code DESC LIMIT 1").executeQuery();
        if (rst.next()) {
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId+=1;
            if (tempId < 10) {
                objectBuilder.add("code", "I00-00" + tempId);
            } else if (tempId < 100) {
                objectBuilder.add("code", "I00-0" + tempId);
            } else if (tempId < 1000) {
                objectBuilder.add("code", "I00-" + tempId);
            }
        }else{
            objectBuilder.add("code", "I00-000");
        }

connection.close();
        return objectBuilder;
    }

    @Override
    public JsonArrayBuilder search(String id) throws SQLException {
        Connection conn = ItemServlet.ds.getConnection();
        PreparedStatement pstm = conn.prepareStatement("SELECT * FROM item WHERE CONCAT(code,description) LIKE ?");
        pstm.setObject(1, "%" + id + "%");
        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String itemID = resultSet.getString(1);
            String itemName = resultSet.getString(2);
            int itemQtyOnHand = resultSet.getInt(3);
            double itemUnitPrice = resultSet.getDouble(4);

            objectBuilder.add("itemId", itemID);
            objectBuilder.add("itemName", itemName);
            objectBuilder.add("itemQty", itemQtyOnHand);
            objectBuilder.add("itemPrice", itemUnitPrice);

            arrayBuilder.add(objectBuilder.build());
    }
        conn.close();
        return arrayBuilder;
    }

    @Override
    public JsonArrayBuilder loadItemID() throws SQLException {
        Connection conn = OrderServlet.ds.getConnection();
        ResultSet rst  = conn.prepareStatement("SELECT code FROM item").executeQuery();
        while (rst.next()){
            String code = rst.getString(1);
            objectBuilder.add("code",code);
            arrayBuilder.add(objectBuilder.build());
        }
        conn.close();
        return arrayBuilder;
    }

    @Override
    public JsonArrayBuilder selectCusData(String id) throws SQLException {
        Connection conn = OrderServlet.ds.getConnection();
        PreparedStatement pstm = conn.prepareStatement("SELECT * FROM item WHERE code=?");
        pstm.setObject(1,id);
        ResultSet rst = pstm.executeQuery();
        if (rst.next()){
            String itemName = rst.getString(2);
            String itemQtyOnHand = rst.getString(3);
            String itemPrice = rst.getString(4);
            objectBuilder.add("itemName",itemName);
            objectBuilder.add("itemQtyOnHand",itemQtyOnHand);
            objectBuilder.add("itemPrice",itemPrice);

            arrayBuilder.add(objectBuilder.build());
        }

        conn.close();
        return arrayBuilder;
    }
}
