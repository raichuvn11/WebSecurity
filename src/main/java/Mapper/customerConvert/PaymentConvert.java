package Mapper.customerConvert;

import DTO.customerDTO.responseDTO.PaymentResponseDTO;
import business.Payment;
import org.modelmapper.ModelMapper;

public class PaymentConvert {
    private static ModelMapper modelMapper = new ModelMapper();

    public static PaymentResponseDTO convertToPeyment (Payment payment) {
        PaymentResponseDTO dto = modelMapper.map(payment, PaymentResponseDTO.class);
        return dto;
    }
}
