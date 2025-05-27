package controller.staffManagementController;

import DAO.StaffDAO;
import data.ImageUtil;
import business.Address;
import business.Shift;
import business.Staff;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
<<<<<<< HEAD
import java.io.IOException;
=======
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
>>>>>>> master
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@WebServlet("/editStaff")
@MultipartConfig
public class EditStaffServlet extends HttpServlet {
<<<<<<< HEAD
=======
    private static final String UPLOAD_DIR = "E:/HK2_24-25/Bao_mat_web/DACuoiKy/Project/WebSecurity/src/main/webapp/upload/";
>>>>>>> master

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        String message = "";
        Staff staff = StaffDAO.getStaffById(Long.parseLong(request.getParameter("emp-id")));
        List<Shift> listShift = StaffDAO.getShiftInMonth(staff, LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        System.out.println(listShift.size());
        request.setAttribute("listShift", listShift);
        if (action == null) {
            request.setAttribute("staff", staff);
            getServletContext().getRequestDispatcher("/Admin/editStaff.jsp").forward(request, response);
        }
        else if(action.equals("edit")) {
            Boolean isSuccess = false;
            staff.setName(request.getParameter("emp-name"));
            staff.setEmail(request.getParameter("email"));
            staff.setPhone(request.getParameter("phone"));
            staff.setBirthDate(java.sql.Date.valueOf(request.getParameter("birth-date")));
            staff.setWorkDate(java.sql.Date.valueOf(request.getParameter("work-date")));
            staff.setSalary(Double.parseDouble(request.getParameter("salary")));

            String addressCountry = request.getParameter("address-country");
            String addressCity = request.getParameter("address-city");
            String addressStreet = request.getParameter("address-street");
            String addressProvince = request.getParameter("address-province");
            Address address = staff.getAddress();
            address.setCountry(addressCountry);
            address.setCity(addressCity);
            address.setStreet(addressStreet);
            address.setProvince(addressProvince);
            staff.setAddress(address);
<<<<<<< HEAD
=======

            Part filePart = request.getPart("avatar");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            File uploadFile = new File(UPLOAD_DIR + fileName);

            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, uploadFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
>>>>>>> master
            byte[] avatarBytes = ImageUtil.renderImage(request.getPart("avatar"));
            if(avatarBytes != null){
                staff.setAvatar(avatarBytes);
            }
            try {
                StaffDAO.update(staff);
                HttpSession session = request.getSession();
                List<Staff> listStaff = (List<Staff>) session.getAttribute("listStaff");
                for (int i = 0; i < listStaff.size(); i++) {
                    if(Objects.equals(listStaff.get(i).getPersonID(), staff.getPersonID())) {
                        listStaff.remove(i);
                        listStaff.add(i, staff);
                        break;
                    }
                }
                session.setAttribute("listStaff", listStaff);
                request.setAttribute("staff", staff);
                message = "Cập nhật thông tin thành công!";
                isSuccess = true;

            }
            catch (Exception e) {
                e.printStackTrace();
                message = "Cập nhật thông tin không thành công!";
            }
            request.setAttribute("message", message);
            request.setAttribute("isSuccess", isSuccess);
            getServletContext().getRequestDispatcher("/Admin/editStaff.jsp").forward(request, response);
        }
    }
}
