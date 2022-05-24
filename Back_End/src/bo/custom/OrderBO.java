package bo.custom;

import bo.SuperBO;

import dto.OrderDTO;

public interface OrderBO extends SuperBO {
    boolean addOrder(OrderDTO orderDTO);
}
