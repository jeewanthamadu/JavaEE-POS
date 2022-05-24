package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;

import java.sql.SQLException;

public interface CustomerBO extends SuperBO {
    boolean addCustomer(CustomerDTO customerDTO) throws SQLException;
    boolean deleteCustomer(String id);
    boolean updateCustomer(CustomerDTO customerDTO);

}
