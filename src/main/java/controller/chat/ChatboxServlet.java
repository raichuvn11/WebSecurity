package controller.chat;

import DAO.UserInfoDAO;
import business.Customer;
import business.Staff;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Staff/chatbox")
public class ChatboxServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String incomingIdRequest = request.getParameter("incoming_id");
        String outgoingIdRequest = request.getParameter("outgoing_id");
        String currentRole = request.getParameter("currentRole");

        Long incomingId = Long.parseLong(incomingIdRequest);
        Long outgoingId = Long.parseLong(outgoingIdRequest);

        UserInfoDAO userInfoDAO = new UserInfoDAO();

        Object outgoingUser;

        if ("staff".equals(currentRole)) {
            outgoingUser = userInfoDAO.getCustomerInfoById(outgoingId);
        } else {
            outgoingUser = userInfoDAO.getStaffInfoById(outgoingId);
        }

        request.setAttribute("outgoingUser", outgoingUser);
        request.setAttribute("incoming_id", incomingId);
        request.setAttribute("outgoing_id", outgoingId);
        request.setAttribute("current_role", currentRole);
        request.getRequestDispatcher("chatbox.jsp").forward(request, response);
    }
}
