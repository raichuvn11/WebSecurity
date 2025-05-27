package controller;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.IOException;
<<<<<<< HEAD
<<<<<<< HEAD
import java.util.logging.Logger;
=======
import java.util.HashMap;
import java.util.Map;
>>>>>>> master
=======
import java.util.logging.Logger;
>>>>>>> master

import business.Customer;
import business.Staff;
import business.Owner;

import data.CustomerDB;
import data.StaffDB;
import data.OwnerDB;
<<<<<<< HEAD
<<<<<<< HEAD
import ultil.LoggerUtil;
=======
>>>>>>> master
=======
import ultil.LoggerUtil;
>>>>>>> master
import utils.MaHoa;

@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {
<<<<<<< HEAD
<<<<<<< HEAD
    private  static final Logger logger = LoggerUtil.getLogger();
=======
>>>>>>> master
=======
    private  static final Logger logger = LoggerUtil.getLogger();
>>>>>>> master

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
<<<<<<< HEAD
<<<<<<< HEAD

=======
    private static final Map<String, Integer> loginAttempts = new HashMap<>();
    private static final int MAX_ATTEMPTS = 5;
>>>>>>> master
=======

>>>>>>> master
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/KhachHang/login.jsp";
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        String role = request.getParameter("role");
        String message = "";
        HttpSession session = request.getSession();

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> master
        // Lấy IP của client
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
<<<<<<< HEAD
=======
        // Kiểm tra nếu bị quá số lần sai
        if (loginAttempts.containsKey(email) && loginAttempts.get(email) >= MAX_ATTEMPTS) {
            message = "Bạn đã đăng nhập sai quá nhiều lần. Vui lòng thử lại sau.";
            session.setAttribute("message", message);
            response.sendRedirect(request.getContextPath() + url);
            return;
>>>>>>> master
=======
>>>>>>> master
        }

        if (email == null || email.equals("") || pass == null || pass.equals("")) {
            message = "Vui lòng nhập đủ thông tin";
<<<<<<< HEAD
<<<<<<< HEAD
            logger.warning("Thiếu thông tin đăng nhập từ người dùng. IP: " + ipAddress);
=======
>>>>>>> master
=======
            logger.warning("Thiếu thông tin đăng nhập từ người dùng. IP: " + ipAddress);
>>>>>>> master
        } else {
            if (role.equals("customer")) {
                String passW = MaHoa.toSHA1(pass);
                Customer customer = CustomerDB.getCustomerByEmailPass(email, passW);
                if (customer == null || customer.getStatus().equals("InActive")) {
                    message = "Sai tài khoản hoặc mật khẩu";
<<<<<<< HEAD
<<<<<<< HEAD
                    logger.warning("Đăng nhập KH thất bại: " + email + ", IP: " + ipAddress);
                } else {
=======
                    // Tăng số lần sai
                    loginAttempts.put(email, loginAttempts.getOrDefault(email, 0) + 1);
                } else {
                    // Đăng nhập thành công: xóa số lần sai
                    loginAttempts.remove(email);
>>>>>>> master
=======
                    logger.warning("Đăng nhập KH thất bại: " + email + ", IP: " + ipAddress);
                } else {
>>>>>>> master
                    session.setAttribute("customer", customer);

                    String displayName = customer.getName();
                    String displayEmail = (customer.getEmail() != null && !customer.getEmail().isEmpty()) ? customer.getEmail() : customer.getGoogleLogin();
                    session.setAttribute("displayName", displayName);
                    session.setAttribute("displayEmail", displayEmail);

<<<<<<< HEAD
<<<<<<< HEAD
                    logger.info("Khách hàng đăng nhập thành công: " + displayEmail + ", IP: " + ipAddress);

=======
>>>>>>> master
=======
                    logger.info("Khách hàng đăng nhập thành công: " + displayEmail + ", IP: " + ipAddress);

>>>>>>> master
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
<<<<<<< HEAD
<<<<<<< HEAD
                    logger.warning("Đăng nhập Nhân viên thất bại: " + email + ", IP: " + ipAddress);
                } else {
=======
                    loginAttempts.put(email, loginAttempts.getOrDefault(email, 0) + 1);
                } else {
                    loginAttempts.remove(email);
>>>>>>> master
=======
                    logger.warning("Đăng nhập Nhân viên thất bại: " + email + ", IP: " + ipAddress);
                } else {
>>>>>>> master
                    session.setAttribute("staff", staff);

                    String displayName = staff.getName();
                    String displayEmail = staff.getEmail();
                    session.setAttribute("displayName", displayName);
                    session.setAttribute("displayEmail", displayEmail);

<<<<<<< HEAD
<<<<<<< HEAD
                    logger.info("Nhân viên đăng nhập thành công: " + staff.getEmail() + ", IP: " + ipAddress);

=======
>>>>>>> master
=======
                    logger.info("Nhân viên đăng nhập thành công: " + staff.getEmail() + ", IP: " + ipAddress);

>>>>>>> master
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
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> master
                    logger.warning("Đăng nhập Chủ sở hữu thất bại: " + email + ", IP: " + ipAddress);
                } else {
                    session.setAttribute("owner", owner);
                    logger.info("Chủ sở hữu đăng nhập thành công: " + owner.getEmail() + ", IP: " + ipAddress);
<<<<<<< HEAD
=======
                    loginAttempts.put(email, loginAttempts.getOrDefault(email, 0) + 1);
                } else {
                    loginAttempts.remove(email);
                    session.setAttribute("owner", owner);
>>>>>>> master
=======
>>>>>>> master
                    url = "/listStaff";
                }
            } else {
                message = "Vui lòng chọn vai trò của bạn";
<<<<<<< HEAD
<<<<<<< HEAD
                logger.warning("Người dùng không chọn vai trò khi đăng nhập. IP: " + ipAddress);
=======
>>>>>>> master
=======
                logger.warning("Người dùng không chọn vai trò khi đăng nhập. IP: " + ipAddress);
>>>>>>> master
            }
        }

        session.setAttribute("message", message);
        response.sendRedirect(request.getContextPath() + url);
    }
<<<<<<< HEAD
<<<<<<< HEAD


=======
>>>>>>> master
=======


>>>>>>> master
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
