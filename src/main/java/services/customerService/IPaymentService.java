package services.customerService;

import DTO.customerDTO.responseDTO.PaymentResponseDTO;

public interface IPaymentService {
    PaymentResponseDTO getPayment(Long orderID);
}
