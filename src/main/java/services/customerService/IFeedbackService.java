package services.customerService;

import DTO.customerDTO.responseDTO.FeedbackResponseDTO;

public interface IFeedbackService {
    FeedbackResponseDTO getFeedback(Long orderID);
}
