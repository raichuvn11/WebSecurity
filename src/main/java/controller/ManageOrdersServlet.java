package controller;

import ENumeration.EOrderStatus;
import business.Customer;
import business.Feedback;
import business.ImageFeedback;
import business.Order;
import com.google.gson.Gson;
import data.CustomerDB;
import data.OrderDB;
import data.FeedbackDB;
import data.PaymentDB;
<<<<<<< HEAD
import org.json.JSONArray;
import org.json.JSONObject;
=======
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
>>>>>>> master

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "ManageOrdersServlet", value = "/manageOrdersServlet")
public class ManageOrdersServlet extends HttpServlet {
<<<<<<< HEAD
=======
    private final ObjectMapper objectMapper = new ObjectMapper();

>>>>>>> master
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7); // Trừ đi 7 ngày
        Date date7DaysAgo = calendar.getTime();
        request.setAttribute("date7DaysAgo", date7DaysAgo);

        String action = request.getParameter("action");
        if ("loadOrders".equals(action))
        {
            Customer customer = (Customer) request.getSession().getAttribute("customer");

            if (customer != null) {
                ArrayList<Order> listOrder = OrderDB.loadOrders(customer);
                request.setAttribute("listOrder", listOrder);
                request.getRequestDispatcher("/KhachHang/orders.jsp").forward(request, response);
            } else {
                response.sendRedirect("/KhachHang/login.jsp");
            }
        }

        if ("filterOrders".equals(action)) {
            Customer customer = (Customer) request.getSession().getAttribute("customer");
            String status = request.getParameter("status");
            ArrayList<Order> listOrder;

            if (status != null && !status.isEmpty()) {
                EOrderStatus orderStatus = EOrderStatus.valueOf(status);
                listOrder = OrderDB.filterOrders(customer, orderStatus);
            } else {
                listOrder = OrderDB.loadOrders(customer);
            }

            // Đặt danh sách đơn hàng đã lọc vào request để truyền sang JSP
            request.setAttribute("listOrder", listOrder);
            request.getRequestDispatcher("/KhachHang/orders.jsp").forward(request, response);
        }

        if ("viewFeedback".equals(action)) {
            Long orderId = Long.valueOf(request.getParameter("orderId"));
            Feedback feedback = FeedbackDB.getFeedbackByOrderId(orderId);

            // Set up the response as JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

<<<<<<< HEAD
            // Create a simple JSON object for feedback
            JSONObject jsonResponse = new JSONObject();

            // Assuming Feedback has appropriate methods to get data
            jsonResponse.put("description", feedback.getDescription());
            jsonResponse.put("rate", feedback.getRate());

            // For images, assuming you have base64 encoded images
            JSONArray imagesArray = new JSONArray();
            for (ImageFeedback image : feedback.getImageFeedbacks()) {
                imagesArray.put("data:image/jpeg;base64," + image.getBase64Image());
            }
            jsonResponse.put("images", imagesArray);

            // Write the JSON response
            response.getWriter().write(jsonResponse.toString());
=======
            // Create JSON response using Jackson
            ObjectNode jsonResponse = objectMapper.createObjectNode();
            jsonResponse.put("description", feedback.getDescription());
            jsonResponse.put("rate", feedback.getRate());

            // For images
            ArrayNode imagesArray = jsonResponse.putArray("images");
            for (ImageFeedback image : feedback.getImageFeedbacks()) {
                imagesArray.add("data:image/jpeg;base64," + image.getBase64Image());
            }

            // Write the JSON response
            response.getWriter().write(objectMapper.writeValueAsString(jsonResponse));
>>>>>>> master
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("cancelOrder".equals(action)) {
            Long orderId = Long.valueOf(request.getParameter("orderId"));

            // Gọi phương thức trong lớp DB để thay đổi trạng thái đơn hàng
            boolean success = OrderDB.updateOrderStatus(orderId, EOrderStatus.CANCELED);

            if (success) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            return;
        }

        if ("refundOrder".equals(action)) {
            Long orderId = Long.valueOf(request.getParameter("orderId"));

            // Gọi phương thức trong lớp DB để thay đổi trạng thái đơn hàng
            boolean success = OrderDB.updateOrderStatus(orderId, EOrderStatus.REFUNDED);

            if (success) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            return;
        }

        if ("acceptOrder".equals(action)) {
            Long orderId = Long.valueOf(request.getParameter("orderId"));

            // Gọi phương thức trong lớp DB để thay đổi trạng thái đơn hàng
            boolean success = OrderDB.updateOrderStatus(orderId, EOrderStatus.ACCEPTED);

            if (success) {
                PaymentDB.updatePaymentDate(orderId);
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            return;
        }

        // Đọc JSON từ request
        StringBuilder jsonBuilder = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        }

<<<<<<< HEAD
        // Parse JSON thành đối tượng JSONObject
        String json = jsonBuilder.toString();
        JSONObject feedbackData = new JSONObject(json);
        // Lấy dữ liệu từ JSONObject
        action = feedbackData.getString("action");
        if ("feedbackOrder".equals(action)) {
            long customerId = feedbackData.getLong("customerId");
            long orderId = feedbackData.getLong("orderId");
            int rate = feedbackData.getInt("rate");
            String description = feedbackData.getString("description");
            JSONArray imageFeedbacks = feedbackData.getJSONArray("imageFeedbacks");

            // Chuyển đổi JSONArray thành danh sách ImageFeedback
            List<ImageFeedback> imageFeedbackList = convertJSONArrayToImageFeedbackList(imageFeedbacks);
=======
        // Parse JSON using Jackson
        String json = jsonBuilder.toString();
        ObjectNode feedbackData = (ObjectNode) objectMapper.readTree(json);
        
        action = feedbackData.get("action").asText();
        if ("feedbackOrder".equals(action)) {
            long customerId = feedbackData.get("customerId").asLong();
            long orderId = feedbackData.get("orderId").asLong();
            int rate = feedbackData.get("rate").asInt();
            String description = feedbackData.get("description").asText();
            ArrayNode imageFeedbacks = (ArrayNode) feedbackData.get("imageFeedbacks");

            // Chuyển đổi ArrayNode thành danh sách ImageFeedback
            List<ImageFeedback> imageFeedbackList = convertArrayNodeToImageFeedbackList(imageFeedbacks);
>>>>>>> master

            Feedback feedback = new Feedback(
                    CustomerDB.getCustomer(customerId),
                    OrderDB.getOrder(orderId),
                    rate,
                    description,
                    imageFeedbackList
            );

<<<<<<< HEAD
            for (int i = 0; i < imageFeedbacks.length(); i++) {
                String base64Image = imageFeedbacks.getString(i);
=======
            for (int i = 0; i < imageFeedbacks.size(); i++) {
                String base64Image = imageFeedbacks.get(i).asText();
>>>>>>> master
                System.out.println("Image " + (i + 1) + ": " + base64Image.substring(0, 50) + "..."); // Print first 50 chars
            }

            boolean success = FeedbackDB.insertFeedback(feedback);
            if (success) {
                OrderDB.updateOrderStatus(orderId, EOrderStatus.FEEDBACKED);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"status\":\"success\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"status\":\"error\", \"message\":\"Failed to update order status.\"}");
            }
        }
    }

<<<<<<< HEAD
    private List<ImageFeedback> convertJSONArrayToImageFeedbackList(JSONArray imageFeedbacks) {
        List<ImageFeedback> imageFeedbackList = new ArrayList<>();

        if (imageFeedbacks == null || imageFeedbacks.length() == 0) {
=======
    private List<ImageFeedback> convertArrayNodeToImageFeedbackList(ArrayNode imageFeedbacks) {
        List<ImageFeedback> imageFeedbackList = new ArrayList<>();

        if (imageFeedbacks == null || imageFeedbacks.size() == 0) {
>>>>>>> master
            System.out.println("Image feedback array is empty or null.");
            return imageFeedbackList;
        }

<<<<<<< HEAD
        for (int i = 0; i < imageFeedbacks.length(); i++) {
            try {
                String base64Image = imageFeedbacks.getString(i);
=======
        for (int i = 0; i < imageFeedbacks.size(); i++) {
            try {
                String base64Image = imageFeedbacks.get(i).asText();
>>>>>>> master

                // Loại bỏ phần tiền tố "data:image/jpeg;base64," nếu có
                if (base64Image.startsWith("data:image/jpeg;base64,")) {
                    base64Image = base64Image.substring("data:image/jpeg;base64,".length());
                }

                // Kiểm tra xem Base64 đã được loại bỏ đúng hay chưa
                System.out.println("Base64 String for Image " + (i + 1) + ": " + base64Image.substring(0, 50) + "...");

                // Giải mã Base64 sang byte[]
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                imageFeedbackList.add(new ImageFeedback(imageBytes));
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid Base64 string at index " + i);
            } catch (Exception e) {
                System.err.println("Unexpected error at index " + i + ": " + e.getMessage());
            }
        }
        return imageFeedbackList;
    }
}
