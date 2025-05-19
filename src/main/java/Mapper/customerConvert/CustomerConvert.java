package Mapper.customerConvert;


import DTO.customerDTO.responseDTO.CustomerByIdResponseDTO;
import DTO.customerDTO.responseDTO.CustomerResponseDTO;
import business.Address;
import business.Customer;
import org.modelmapper.ModelMapper;

import java.util.Base64;

public class CustomerConvert {

    private static ModelMapper modelMapper = new ModelMapper();

    // Phương thức chuyển đổi từ Customer Entity sang CustomerResponseDTO
    public static CustomerResponseDTO convertToDTO(Customer customer) {
        CustomerResponseDTO dto = modelMapper.map(customer, CustomerResponseDTO.class);
        if (customer.getAddress() != null) {
            Address address = customer.getAddress();
            String fullAddress = address.getStreet() + ", " + address.getCity() + ", " + address.getProvince() + ", " + address.getCountry();
            dto.setAddress(fullAddress);
        }
        // Chuyển đổi mảng byte của feedbackImage thành chuỗi Base64
        if (customer.getAvatar() != null) {
            String base64Image = Base64.getEncoder().encodeToString(customer.getAvatar());
            dto.setAvatar(base64Image);
        }
        return dto;
    }
    public static CustomerByIdResponseDTO convertToDTOCustomer (Customer customer) {
        CustomerByIdResponseDTO dto = modelMapper.map(customer, CustomerByIdResponseDTO.class);
        // Chuyển đổi mảng byte của feedbackImage thành chuỗi Base64
        if (customer.getAvatar() != null) {
            String base64Image = Base64.getEncoder().encodeToString(customer.getAvatar());
            dto.setAvatar(base64Image);
        }
        return dto;
    }
}

