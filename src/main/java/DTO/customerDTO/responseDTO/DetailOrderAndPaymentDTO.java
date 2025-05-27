package DTO.customerDTO.responseDTO;

import java.util.List;

public class DetailOrderAndPaymentDTO {
    private List<FurnitureOfOrderResponseDTO> furnitureOfOrderResponseDTO;
    private PaymentResponseDTO paymentResponseDTO;

    public List<FurnitureOfOrderResponseDTO> getFurnitureOfOrderResponseDTO() {
        return furnitureOfOrderResponseDTO;
    }

    public void setFurnitureOfOrderResponseDTO(List<FurnitureOfOrderResponseDTO> furnitureOfOrderResponseDTO) {
        this.furnitureOfOrderResponseDTO = furnitureOfOrderResponseDTO;
    }

    public PaymentResponseDTO getPaymentResponseDTO() {
        return paymentResponseDTO;
    }

    public void setPaymentResponseDTO(PaymentResponseDTO paymentResponseDTO) {
        this.paymentResponseDTO = paymentResponseDTO;
    }
}
