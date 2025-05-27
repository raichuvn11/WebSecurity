package services.customerService.Impl;


import DAO.customerDAO.IPaymentDAO;
import DAO.customerDAO.impl.PaymentDAOImpl;
import DTO.customerDTO.responseDTO.PaymentResponseDTO;
import business.Payment;
import Mapper.customerConvert.PaymentConvert;
import services.customerService.IFurnitureOfOrderService;
import services.customerService.IPaymentService;

public class PaymentServiceImpl implements IPaymentService {
    private IPaymentDAO paymentDAO = new PaymentDAOImpl();
    private PaymentConvert paymentConvert = new PaymentConvert();
    private IFurnitureOfOrderService furnitureOfOrderService = new FurnitureOfOrderServiceImpl();
    @Override
    public PaymentResponseDTO getPayment(Long orderID) {
        Payment payment = paymentDAO.getPayment(orderID);
        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
        paymentResponseDTO=paymentConvert.convertToPeyment(payment);
        Long totalPriceFurniture = furnitureOfOrderService.totalPriceOfOrder(orderID);
        paymentResponseDTO.setTotalPrice(totalPriceFurniture);
        return paymentResponseDTO;
    }

}
