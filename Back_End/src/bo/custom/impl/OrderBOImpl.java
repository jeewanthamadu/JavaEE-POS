package bo.custom.impl;

import bo.custom.OrderBO;
import dao.DAOFactory;
import dao.custom.impl.OrderDAOImpl;
import dao.custom.impl.OrderDetailsDAOImpl;
import dto.OrderDTO;

import javax.json.JsonObjectBuilder;
import java.sql.SQLException;


public class OrderBOImpl implements OrderBO {
    OrderDAOImpl orderDAO = (OrderDAOImpl) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailsDAOImpl orderDetailsDAO = (OrderDetailsDAOImpl) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);

    @Override
    public boolean addOrder(OrderDTO orderDTO) {
        return false;
    }

    @Override
    public JsonObjectBuilder generateID() throws SQLException {
        return orderDAO.generateID();
    }

}
