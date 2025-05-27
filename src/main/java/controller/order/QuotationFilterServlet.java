package controller.order;

import business.Order;
import DAO.OrderDAO;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/Staff/filterQuotations")
public class QuotationFilterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String status = request.getParameter("status");

        try {
            OrderDAO orderDAO = new OrderDAO();
            List<Order> filteredOrders;

            if (status == null || status.isEmpty() || "Chọn trạng thái".equals(status)) {
                filteredOrders = orderDAO.getAllOrders();
            } else {
                filteredOrders = orderDAO.filterOrdersByStatus(status);
            }
            request.setAttribute("filteredOrders", filteredOrders);
            System.out.println(filteredOrders);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi lọc đơn hàng: " + e.getMessage());
        }

        request.getRequestDispatcher("quotationList.jsp").forward(request, response);
    }
}
