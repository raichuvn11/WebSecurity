package services.customerService.Impl;


import DAO.customerDAO.IFeedbackDAO;
import DAO.customerDAO.impl.FeedbackDAOImpl;
import DTO.customerDTO.responseDTO.FeedbackResponseDTO;
import business.Feedback;
import Mapper.customerConvert.FeedbackConvert;
import services.customerService.IFeedbackService;

public class FeedbackServiceImpl implements IFeedbackService {
    private IFeedbackDAO feedbackDAO = new FeedbackDAOImpl();
    private FeedbackConvert feedbackConvert = new FeedbackConvert();
    @Override
    public FeedbackResponseDTO getFeedback(Long orderID) {
        Feedback review=feedbackDAO.getFeedback(orderID);
        FeedbackResponseDTO feedbackResponseDTO=new FeedbackResponseDTO();
        feedbackResponseDTO=feedbackConvert.convertToDTO(review);
        return feedbackResponseDTO;
    }
}
