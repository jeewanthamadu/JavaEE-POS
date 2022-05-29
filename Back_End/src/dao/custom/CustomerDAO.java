package dao.custom;

import dao.CrudDAO;
import entity.Customer;

import javax.json.JsonArrayBuilder;
import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer,String> {
    JsonArrayBuilder loadCusID () throws SQLException;
    JsonArrayBuilder selectCusData(String id) throws SQLException;
}
