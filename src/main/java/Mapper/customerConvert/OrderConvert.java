package Mapper.customerConvert;

import DTO.customerDTO.responseDTO.OrderResponseDTO;
import business.Order;
import org.modelmapper.ModelMapper;

public class OrderConvert {
    private static ModelMapper modelMapper = new ModelMapper();

    // Phương thức chuyển đổi từ Order sang OrderResponseDTO
    public static OrderResponseDTO convertToDTO(Order order) {
        return modelMapper.map(order, OrderResponseDTO.class);
    }
}
