package bo.custom.impl;

import bo.custom.CustomerBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dao.custom.impl.CustomerDAOImpl;
import dto.CustomerDTO;
import entity.Customer;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.SQLException;

public class CustomerBOImpl implements CustomerBO {
CustomerDAOImpl customerDAO = (CustomerDAOImpl) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public JsonArrayBuilder getAllCustomer() throws SQLException {
        return customerDAO.getAll();
    }

    @Override
    public JsonObjectBuilder generateCustomerID() throws SQLException {
        return customerDAO.generateID();
    }

    @Override
    public JsonArrayBuilder searchCustomer(String id) throws SQLException {
        return customerDAO.search(id);
    }

    @Override
    public JsonArrayBuilder loadCusID() throws SQLException {
        return customerDAO.loadCusID();
    }

    @Override
    public JsonArrayBuilder selectCusData(String id) throws SQLException {
        return customerDAO.selectCusData(id);
    }

    @Override
    public boolean addCustomer(CustomerDTO customerDTO) throws SQLException {
        Customer customer = new Customer(customerDTO.getId(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getSalary());
        return customerDAO.add(customer);
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException {
        return customerDAO.delete(id);
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException {
        return customerDAO.update(new Customer(customerDTO.getId(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getSalary()));
    }
}
