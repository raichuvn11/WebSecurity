package DAO.customerDAO;

import business.Payment;

public interface IPaymentDAO {
    Payment getPayment(Long orderID);
}
