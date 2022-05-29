package dao.custom.impl;

import dao.custom.CustomerDAO;
import entity.Customer;
import servlet.CustomerServlet;
import servlet.OrderServlet;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAOImpl implements CustomerDAO {

    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
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

    @Override
    public JsonArrayBuilder getAll() throws SQLException {

        Connection conn = CustomerServlet.ds.getConnection();
        ResultSet resultSet = conn.prepareStatement("select * from customer").executeQuery();
        while (resultSet.next()){
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            double salary = resultSet.getDouble(4);

            objectBuilder.add("id", id);
            objectBuilder.add("name", name);
            objectBuilder.add("address", address);
            objectBuilder.add("salary", salary);


            arrayBuilder.add(objectBuilder.build());
        }

conn.close();
        return arrayBuilder;
    }

    @Override
    public JsonObjectBuilder generateID() throws SQLException {
        Connection connection = CustomerServlet.ds.getConnection();
        ResultSet rst = connection.prepareStatement("SELECT id FROM customer ORDER BY id DESC LIMIT 1").executeQuery();
        if (rst.next()) {
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId+=1;
            if (tempId < 10) {
                objectBuilder.add("id", "C00-00" + tempId);
            } else if (tempId < 100) {
                objectBuilder.add("id", "C00-0" + tempId);
            } else if (tempId < 1000) {
                objectBuilder.add("id", "C00-" + tempId);
            }
        }else{
            objectBuilder.add("id", "C00-000");
        }

        connection.close();
        return objectBuilder;
    }

    @Override
    public JsonArrayBuilder search(String id) throws SQLException {
        Connection conn = CustomerServlet.ds.getConnection();
        PreparedStatement pstm = conn.prepareStatement("SELECT * FROM customer WHERE CONCAT(id,name) LIKE ?");
        pstm.setObject(1, "%" + id + "%");
        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String cusIDS = resultSet.getString(1);
            String cusNameS = resultSet.getString(2);
            String cusAddressS = resultSet.getString(3);
            double cusSalaryS = resultSet.getDouble(4);

            objectBuilder.add("id", cusIDS);
            objectBuilder.add("name", cusNameS);
            objectBuilder.add("address", cusAddressS);
            objectBuilder.add("salary", cusSalaryS);

            arrayBuilder.add(objectBuilder.build());

           }
        conn.close();
        return arrayBuilder;
    }

    @Override
    public JsonArrayBuilder loadCusID() throws SQLException {
        Connection conn = OrderServlet.ds.getConnection();
        ResultSet rst  = conn.prepareStatement("SELECT id FROM customer").executeQuery();
        while (rst.next()){
            String id = rst.getString(1);
            objectBuilder.add("id",id);
            arrayBuilder.add(objectBuilder.build());
        }
        conn.close();
        return arrayBuilder;
    }

    @Override
    public JsonArrayBuilder selectCusData(String id) throws SQLException {
        Connection connection = OrderServlet.ds.getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM customer WHERE id=?");
        pstm.setObject(1,id);
        ResultSet rst = pstm.executeQuery();
        if (rst.next()){
            String cusName = rst.getString(2);
            String cusAddress = rst.getString(3);
            String cusSalary = rst.getString(4);
            objectBuilder.add("cusName",cusName);
            objectBuilder.add("cusAddress",cusAddress);
            objectBuilder.add("cusSalary",cusSalary);

            arrayBuilder.add(objectBuilder.build());
        }
        connection.close();
        return arrayBuilder;
    }
}
