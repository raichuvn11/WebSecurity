package services.customerService.Impl;

import DAO.customerDAO.IOrderDAO;
import DAO.customerDAO.impl.OrderDAOImpl;
import DTO.customerDTO.requestDTO.OrderRequestDTO;
import DTO.customerDTO.responseDTO.OrderResponseDTO;
import business.Order;
import Mapper.customerConvert.OrderConvert;
import services.customerService.IOrderService;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements IOrderService {
    private IOrderDAO orderDAO = new OrderDAOImpl();
    private OrderConvert orderConvert = new OrderConvert();
    @Override
    public List<OrderResponseDTO> getOrder(OrderRequestDTO orderRequestDTO) {
       List<Order> orders = orderDAO.getOrder(orderRequestDTO);
       List<OrderResponseDTO> orderResponseDTOs = new ArrayList<>();
       for (Order order : orders) {
           OrderResponseDTO orderResponseDTO=new OrderResponseDTO();
           orderResponseDTO= orderConvert.convertToDTO(order);
           orderResponseDTOs.add(orderResponseDTO);
       }
        return orderResponseDTOs;
    }

    @Override
    public Long totalPriceOfOrder(Long orderId) {
        return 0l;
    }
}
