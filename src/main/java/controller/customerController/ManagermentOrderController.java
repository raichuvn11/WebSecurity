package controller.customerController;



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

@WebServlet(urlPatterns = {"/admin-customer-order/*"})
public class ManagermentOrderController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IOrderService orderService=new OrderServiceImpl();
    private IFeedbackService feedbackService=new FeedbackServiceImpl();
    private IFurnitureOfOrderService productOfOrderService=new FurnitureOfOrderServiceImpl();
    private IManagermentCustomerService managermentCustomerService=new ManagermentCustomerServiceImpl();
    private IPaymentService paymentService=new PaymentServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String customerIdStr = req.getParameter("customerId");
        Long customerId = Long.parseLong(customerIdStr);
        String orderStatusStr = req.getParameter("status");
        String orderIdStr = req.getParameter("id");
        String orderDateParam = req.getParameter("orderDate");

        OrderRequestDTO searchOrder = new OrderRequestDTO();
        searchOrder.setCustomerId(customerId);
        searchOrder.setStatus(orderStatusStr);

        Long orderId = null;

        if (orderIdStr != null && !orderIdStr.trim().isEmpty()) {
            try {
                orderId = Long.parseLong(orderIdStr);
                searchOrder.setId(orderId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (orderDateParam != null && !orderDateParam.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                searchOrder.setOrderDate(dateFormat.parse(orderDateParam));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        List<OrderResponseDTO> orders = orderService.getOrder(searchOrder);
        CustomerResponseDTO responseDTO = managermentCustomerService.getCustomerById(customerId);
        req.setAttribute("searchOrder", searchOrder);
        req.setAttribute("orders", orders);
        req.setAttribute("customer", responseDTO);
        String url = "/Admin/listOrder.jsp";
        getServletContext().getRequestDispatcher(url).forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        FeedbackResponseDTO responseDTO = new FeedbackResponseDTO();
        if (pathInfo != null && pathInfo.length() > 1) {
            try {
                Long orderId = Long.parseLong(pathInfo.substring(1));
                responseDTO = feedbackService.getFeedback(orderId);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(new Gson().toJson(responseDTO));
            } catch (NumberFormatException e) {
                // Nếu không phải số, gửi lỗi về phía client
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Invalid ID format");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Missing or invalid path info");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        DetailOrderAndPaymentDTO responseDTO = new DetailOrderAndPaymentDTO();
        List<FurnitureOfOrderResponseDTO> furnitureOfOrderResponseDTOS=new ArrayList<>();
        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();

        if (pathInfo != null && pathInfo.length() > 1) {
            try {
                Long orderId = Long.parseLong(pathInfo.substring(1));
                furnitureOfOrderResponseDTOS = productOfOrderService.getProductOfOrder(orderId);
                paymentResponseDTO = paymentService.getPayment(orderId);
                responseDTO.setFurnitureOfOrderResponseDTO(furnitureOfOrderResponseDTOS);
                responseDTO.setPaymentResponseDTO(paymentResponseDTO);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(new Gson().toJson(responseDTO));
            } catch (NumberFormatException e) {
                // Nếu không phải số, gửi lỗi về phía client
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Invalid ID format");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Missing or invalid path info");
        }
    }
}