package DAO.customerDAO;

import DTO.customerDTO.requestDTO.CustomerRequestDTO;
import business.Customer;

import java.util.List;

public interface IManagermentCustomerDAO {
    List<Customer> getAllCustomer(CustomerRequestDTO reqDTO);
    Customer findById(Long customerId);
    void updateCustomerStatus(List<Long> customerIds, String status);

}