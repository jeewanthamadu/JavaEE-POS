package dao;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.SQLException;

public interface CrudDAO<S,Id> extends SuperDAO{
    boolean add (S s) throws SQLException;
    boolean delete(String id) throws SQLException;
    boolean update(S s) throws SQLException;
    JsonArrayBuilder getAll() throws SQLException;
    JsonObjectBuilder generateID() throws SQLException;
    JsonArrayBuilder search(String id) throws SQLException;


}
