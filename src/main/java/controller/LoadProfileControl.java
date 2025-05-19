package controller;

import DAO.PersonDao;
import DAO.CustomerDao;
import DAO.customerDAO.ProfileDAO.StaffDao2;
import business.Person;
import business.Customer;
import business.Staff;
import business.Owner;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "LoadProfileControl", value = "/loadProfile")
public class LoadProfileControl extends HttpServlet {

    private final CustomerDao customerDao = new CustomerDao();
    private final StaffDao2 staffDao = new StaffDao2();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String url = "/profile.jsp";
        Customer cus = null;
        Staff sta = null;
        String userType = null;
        String email = null;
        boolean showChangePasswordButton = true;

        // Formatters
        SimpleDateFormat inputFormatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        SimpleDateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy");

        if (session.getAttribute("customer") != null) {
            Customer customer = (Customer) session.getAttribute("customer");
            cus = customerDao.getCustomerById(customer.getPersonID());
            if (cus == null || cus.getAvatar() == null) {
                // Sử dụng ảnh mặc định nếu không có avatar
                customer.setAvatar(getDefaultAvatar("/assets/img/default-customer.jpg"));
                cus = customer;
            }
            userType = "Customer";
            email = cus.getEmail() != null ? cus.getEmail() : cus.getGoogleLogin();
            if (cus.getEmail() == null) {
                showChangePasswordButton = false;
            }
            // Set URL hoặc dữ liệu Base64 để hiển thị avatar
            request.setAttribute("avatarUrl", getAvatarUrl(cus.getAvatar()));
            request.setAttribute("person", cus);
        } else if (session.getAttribute("staff") != null) {
            Staff staff = (Staff) session.getAttribute("staff");
            sta = staffDao.getStaffById(staff.getPersonID());
            if (sta == null || sta.getAvatar() == null) {
                // Sử dụng ảnh mặc định nếu không có avatar
                staff.setAvatar(getDefaultAvatar("/assets/img/default-staff.jpg"));
                sta = staff;
            }
            userType = "Staff";
            email = sta.getEmail(); // Assume Staff does not use Google Login
            // Set URL hoặc dữ liệu Base64 để hiển thị avatar
            request.setAttribute("avatarUrl", getAvatarUrl(sta.getAvatar()));
            request.setAttribute("person", sta);
        }
        String formattedBirthDate = "";
        try {
            if (cus != null && cus.getBirthDate() != null) {
                Date parsedDate = inputFormatter.parse(cus.getBirthDate().toString());
                formattedBirthDate = outputFormatter.format(parsedDate);
                request.setAttribute("birthDate", formattedBirthDate);
                request.setAttribute("person", cus);
            } else if (sta != null && sta.getBirthDate() != null) {
                Date parsedDate = inputFormatter.parse(sta.getBirthDate().toString());
                formattedBirthDate = outputFormatter.format(parsedDate);
                request.setAttribute("birthDate", formattedBirthDate);
                request.setAttribute("person", sta);
            }
        } catch (ParseException e) {
            e.printStackTrace(); // Log the error for debugging purposes
            request.setAttribute("birthDate", "Invalid Date");
        }

        if (cus == null && sta == null) {
            session.setAttribute("message", "Vui lòng đăng nhập để truy cập thông tin hồ sơ.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.setAttribute("userType", userType);
        request.setAttribute("email", email);
        request.setAttribute("showChangePasswordButton", showChangePasswordButton);

        System.out.println("Forwarding to: " + url);
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
    private String getAvatarUrl(byte[] avatar) {
        if (avatar == null || avatar.length == 0) {
            return null; // Không có ảnh
        }
        return "data:image/jpeg;base64," + java.util.Base64.getEncoder().encodeToString(avatar);
    }
    private byte[] getDefaultAvatar(String path) throws IOException {
        try (InputStream is = getServletContext().getResourceAsStream(path)) {
            if (is != null) {
                return is.readAllBytes();
            }
        }
        return null; // Trả về null nếu không tìm thấy ảnh
    }

}
