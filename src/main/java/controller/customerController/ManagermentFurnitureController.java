package controller.customerController;



import DTO.customerDTO.requestDTO.FurnitureRequestDTO;
import DTO.customerDTO.requestDTO.OrderRequestDTO;
import DTO.customerDTO.responseDTO.*;
import com.google.gson.Gson;
import services.customerService.*;
import services.customerService.Impl.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/admin-customer-furniture/*"})
public class ManagermentFurnitureController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IOrderService orderService=new OrderServiceImpl();
    private IFeedbackService feedbackService=new FeedbackServiceImpl();
    private IFurnitureOfOrderService productOfOrderService=new FurnitureOfOrderServiceImpl();
    private IManagermentCustomerService managermentCustomerService=new ManagermentCustomerServiceImpl();
    private IPaymentService paymentService=new PaymentServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String categoryName=req.getParameter("categoryName");
        String customerIdStr = req.getParameter("customerId");
        Long customerId = Long.parseLong(customerIdStr);
        String priceStartStr = req.getParameter("priceStart");
        String priceEndStr = req.getParameter("priceEnd");
        FurnitureRequestDTO furnitureRequestDTO=new FurnitureRequestDTO();
        furnitureRequestDTO.setCustomerId(customerId);
        furnitureRequestDTO.setCategoryName(categoryName);
        Long priceStart= null;
        Long priceEnd= null;
        if (priceStartStr != null && !priceStartStr.trim().isEmpty()) {
            try {
                priceStart = Long.parseLong(priceStartStr);
                furnitureRequestDTO.setPriceStart(priceStart);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (priceStartStr != null && !priceStartStr.trim().isEmpty()) {
            try {
                priceEnd= Long.parseLong(priceEndStr);
                furnitureRequestDTO.setPriceEnd(priceEnd);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        List<FurnitureOfOrderResponseDTO> furnitureOfOrderResponseDTOList=productOfOrderService.getFurnituresByCustomerId(furnitureRequestDTO);
        CustomerResponseDTO responseDTO = managermentCustomerService.getCustomerById(customerId);
        req.setAttribute("furniture", furnitureOfOrderResponseDTOList);
        req.setAttribute("furnitureRequestDTO", furnitureRequestDTO);
        req.setAttribute("customer", responseDTO);
        String url = "/Admin/listFurnitureOfCustomer.jsp";
        getServletContext().getRequestDispatcher(url).forward(req, resp);
    }
}