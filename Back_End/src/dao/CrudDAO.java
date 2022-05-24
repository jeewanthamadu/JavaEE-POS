package dao;

import java.sql.SQLException;

public interface CrudDAO<S,Id> extends SuperDAO{
    boolean add (S s) throws SQLException;
    boolean delete(String id);
    boolean update(S s);

}
