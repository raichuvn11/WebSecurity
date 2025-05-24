package DAO.customerDAO;

import DTO.customerDTO.requestDTO.OrderRequestDTO;
import business.Order;

import java.util.List;

public interface IOrderDAO {
    List<Order> getOrder(OrderRequestDTO orderRequestDTO);
}
