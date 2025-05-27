package controller;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.IOException;
import java.util.logging.Logger;

import business.Customer;
import business.Staff;
import business.Owner;

import data.CustomerDB;
import data.StaffDB;
import data.OwnerDB;
import ultil.LoggerUtil;
import utils.MaHoa;

@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {
    private  static final Logger logger = LoggerUtil.getLogger();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/KhachHang/login.jsp";
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        String role = request.getParameter("role");
        String message = "";
        HttpSession session = request.getSession();

        // Lấy IP của client
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        if (email == null || email.equals("") || pass == null || pass.equals("")) {
            message = "Vui lòng nhập đủ thông tin";
            logger.warning("Thiếu thông tin đăng nhập từ người dùng. IP: " + ipAddress);
        } else {
            if (role.equals("customer")) {
                String passW = MaHoa.toSHA1(pass);
                Customer customer = CustomerDB.getCustomerByEmailPass(email, passW);
                if (customer == null || customer.getStatus().equals("InActive")) {
                    message = "Sai tài khoản hoặc mật khẩu";
                    logger.warning("Đăng nhập KH thất bại: " + email + ", IP: " + ipAddress);
                } else {
                    session.setAttribute("customer", customer);

                    String displayName = customer.getName();
                    String displayEmail = (customer.getEmail() != null && !customer.getEmail().isEmpty()) ? customer.getEmail() : customer.getGoogleLogin();
                    session.setAttribute("displayName", displayName);
                    session.setAttribute("displayEmail", displayEmail);

                    logger.info("Khách hàng đăng nhập thành công: " + displayEmail + ", IP: " + ipAddress);

                    if (!isProfileCompleteCus(customer)) {
                        url = "/KhachHang/saveProfile.jsp";
                    } else {
                        url = "../indexServlet";
                    }
                }
            } else if (role.equals("staff")) {
                Staff staff = StaffDB.getStaffByEmailPass(email, pass);
                if (staff == null || staff.getStatus().equals("InActive")) {
                    message = "Sai tài khoản hoặc mật khẩu";
                    logger.warning("Đăng nhập Nhân viên thất bại: " + email + ", IP: " + ipAddress);
                } else {
                    session.setAttribute("staff", staff);

                    String displayName = staff.getName();
                    String displayEmail = staff.getEmail();
                    session.setAttribute("displayName", displayName);
                    session.setAttribute("displayEmail", displayEmail);

                    logger.info("Nhân viên đăng nhập thành công: " + staff.getEmail() + ", IP: " + ipAddress);

                    if (!isProfileCompleteSta(staff)) {
                        url = "/KhachHang/saveProfile.jsp";
                    } else {
                        url = "/Staff/dashboard.jsp";
                    }
                }
            } else if (role.equals("owner")) {
                Owner owner = OwnerDB.getOwnerByEmailPass(email, pass);
                if (owner == null) {
                    message = "Sai tài khoản hoặc mật khẩu";
                    logger.warning("Đăng nhập Chủ sở hữu thất bại: " + email + ", IP: " + ipAddress);
                } else {
                    session.setAttribute("owner", owner);
                    logger.info("Chủ sở hữu đăng nhập thành công: " + owner.getEmail() + ", IP: " + ipAddress);
                    url = "/listStaff";
                }
            } else {
                message = "Vui lòng chọn vai trò của bạn";
                logger.warning("Người dùng không chọn vai trò khi đăng nhập. IP: " + ipAddress);
            }
        }

        session.setAttribute("message", message);
        response.sendRedirect(request.getContextPath() + url);
    }


    /**
     */
    private boolean isProfileCompleteCus(Customer customer) {
        return customer.getPhone() != null &&
                customer.getAddress() != null;
    }

    /**
     * Kiểm tra xem hồ sơ của Staff có đầy đủ thông tin chưa.
     */
    private boolean isProfileCompleteSta(Staff staff) {
        return staff.getPhone() != null && !staff.getPhone().isEmpty() &&
                staff.getAddress() != null && staff.getAddress().isComplete();
    }
}
