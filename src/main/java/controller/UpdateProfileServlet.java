package controller;

import DAO.CustomerDao;
import DAO.customerDAO.ProfileDAO.StaffDao2;

import business.Customer;
import business.Staff;
import business.Address;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@WebServlet(name = "UpdateProfileServlet", value = "/updateProfile")
@MultipartConfig
public class UpdateProfileServlet extends HttpServlet {

    private final CustomerDao customerDao = new CustomerDao();
    private final StaffDao2 staffDao = new StaffDao2();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Lấy thông tin người dùng từ session
        Customer customer = (Customer) session.getAttribute("customer");
        Staff staff = (Staff) session.getAttribute("staff");

        if (customer == null && staff == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("User not logged in.");
            return;
        }

        try {
            // Lấy dữ liệu từ form
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String birthDateStr = request.getParameter("birthDate");
            String street = request.getParameter("street");
            String city = request.getParameter("city");
            String province = request.getParameter("province");
            String country = request.getParameter("country");
            Address address = new Address(street, city, province, country);

            Part part = request.getPart("profileImage");
            byte[] profileImage = null;
            if (part != null && part.getSize() > 0) {
                profileImage = toByteArray(part.getInputStream());
            }



            if (customer != null) {
                updateCustomer(customer, name, phone, birthDateStr, address, profileImage, request);
                session.setAttribute("person", customer);
            } else if (staff != null) {
                updateStaff(staff, name, phone, birthDateStr, address, profileImage, request);
                session.setAttribute("person", staff);
            }

            response.setStatus(HttpServletResponse.SC_OK);
            response.sendRedirect("/profile.jsp?success=true");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.sendRedirect("/profile.jsp?error=true");
        }
    }

    private void updateCustomer(Customer customer, String name, String phone, String birthDateStr, Address address, byte[] avatarBytes, HttpServletRequest request) throws IOException {
        if (name != null && !name.isEmpty()) customer.setName(name);
        if (phone != null && !phone.isEmpty()) customer.setPhone(phone);
        customer.setAddress(address);

        if (avatarBytes != null && avatarBytes.length > 0) {
            customer.setAvatar(avatarBytes);
        } else if (customer.getAvatar() == null) {
            customer.setAvatar(getDefaultAvatar(request, "/assets/img/default-customer.jpg"));
        }

        if (birthDateStr != null && !birthDateStr.isEmpty()) {
            try {
                customer.setBirthDate(java.sql.Date.valueOf(birthDateStr));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid birth date format: " + e.getMessage());
            }
        }

        customerDao.updateCustomer(customer);
    }

    private void updateStaff(Staff staff, String name, String phone, String birthDateStr, Address address, byte[] avatarBytes, HttpServletRequest request) throws IOException {
        if (name != null && !name.isEmpty()) staff.setName(name);
        if (phone != null && !phone.isEmpty()) staff.setPhone(phone);
        staff.setAddress(address);

        if (avatarBytes != null && avatarBytes.length > 0) {
            staff.setAvatar(avatarBytes);
        } else if (staff.getAvatar() == null) {
            staff.setAvatar(getDefaultAvatar(request, "/assets/img/default-staff.jpg"));
        }

        if (birthDateStr != null && !birthDateStr.isEmpty()) {
            try {
                staff.setBirthDate(java.sql.Date.valueOf(birthDateStr));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid birth date format: " + e.getMessage());
            }
        }

        staffDao.updateStaff(staff);
    }

    private byte[] getDefaultAvatar(HttpServletRequest request, String path) throws IOException {
        try (InputStream is = request.getServletContext().getResourceAsStream(path)) {
            if (is != null) {
                return is.readAllBytes();
            }
        }
        return null; // Trả về null nếu không tìm thấy ảnh
    }

    private byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(data)) != -1) {
            buffer.write(data, 0, bytesRead);
        }
        return buffer.toByteArray();
    }
}
