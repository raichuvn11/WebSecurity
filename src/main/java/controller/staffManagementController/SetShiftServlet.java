package controller.staffManagementController;

import DAO.StaffDAO;
import data.CalendarUtil;
import business.Shift;
import business.Staff;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@WebServlet("/setShiftStaff")
public class SetShiftServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        LocalDate currentDate = LocalDate.now();
        int month = currentDate.getMonthValue();  // Tháng hiện tại
        int year = currentDate.getYear();         // Năm hiện tại

        if (request.getParameter("month") != null && request.getParameter("year") != null) {
            month = Integer.parseInt(request.getParameter("month"));
            year = Integer.parseInt(request.getParameter("year"));
        }

        String action = Objects.requireNonNullElse(request.getParameter("action"), "");

        if ("pre".equals(action)) {
            month -= 1;
            if (month == 0) {
                month = 12;
                year -= 1;
            }
        } else if ("next".equals(action)) {
            month += 1;
            if (month == 13) {
                month = 1;
                year += 1;
            }
        } else if ("submit".equals(action)) {
            String strStaffId = request.getParameter("listStaff");
            String warningMessage ="";
            if(strStaffId != null) {
                Long staffId = Long.parseLong(strStaffId);
                Staff staff = StaffDAO.getStaffById(staffId);
                String[] amShifts = request.getParameterValues("shift-selection-am");
                String[] pmShifts = request.getParameterValues("shift-selection-pm");
                List<Shift> listShift = staff.getListShift();
                int shiftCount = 0;
                if (amShifts != null) {
                    for (String s : amShifts) {
                        Shift shift = new Shift("am_", "8h00", "11h30", java.sql.Date.valueOf(s));
                        listShift.add(shift);
                        shiftCount++;
                    }
                }
                if (pmShifts != null) {
                    for (String s : pmShifts) {
                        Shift shift = new Shift("pm_", "13h00", "17h00", java.sql.Date.valueOf(s));
                        listShift.add(shift);
                        shiftCount++;
                    }
                }

                //check listShift của mỗi nhân viên
                if (shiftCount > 15) {
                    warningMessage = "Đã chọn quá 15 ca cho nhân viên này!";
                }
                else if (shiftCount < 15) {
                    warningMessage = "Chưa chọn đủ 15 ca cho nhân viên này!";
                }
                else{
                    staff.setListShift(listShift);
                    StaffDAO.update(staff);
                }
            }
            else{
                warningMessage = "Vui lòng chọn 1 nhân viên!";
            }
            request.setAttribute("warningMessage", warningMessage);

        }else if(action.equals("listStaff")){
            LocalDate date = LocalDate.parse(request.getParameter("date"));
            List<Staff> amStaffs = StaffDAO.getStaffInShift(date, "am_");
            List<Staff> pmStaffs = StaffDAO.getStaffInShift(date, "pm_");
            request.setAttribute("amStaffs", amStaffs);
            request.setAttribute("pmStaffs", pmStaffs);
        }

        Map<LocalDate, Integer> staffCount_amShift = StaffDAO.getStaffPerShiftInMonth("am_", month, year);
        Map<LocalDate, Integer> staffCount_pmShift = StaffDAO.getStaffPerShiftInMonth("pm_", month, year);

        String calendarTable = CalendarUtil.getHtmlCalendar(staffCount_amShift, staffCount_pmShift, month, year);
        List<Staff> listStaff = StaffDAO.getAllStaffs("Active");
        request.setAttribute("listStaff", listStaff);
        request.setAttribute("calendarTable", calendarTable);
        request.setAttribute("currentMonth", month);
        request.setAttribute("currentYear", year);
        request.getRequestDispatcher("/Admin/setShift.jsp").forward(request, response);
    }
}
