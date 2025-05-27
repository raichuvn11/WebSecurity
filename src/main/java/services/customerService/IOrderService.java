package services.customerService;

import DTO.customerDTO.requestDTO.OrderRequestDTO;
import DTO.customerDTO.responseDTO.OrderResponseDTO;

import java.util.List;

public interface IOrderService {
    List<OrderResponseDTO> getOrder(OrderRequestDTO orderRequestDTO);
    Long totalPriceOfOrder(Long orderId);
}
