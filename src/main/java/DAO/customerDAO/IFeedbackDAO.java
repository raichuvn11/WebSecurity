package DAO.customerDAO;


import business.Feedback;

public interface IFeedbackDAO {
    Feedback getFeedback(Long orderID);

}
