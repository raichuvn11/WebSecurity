package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*") // Lọc tất cả URL
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();


        // Kiểm tra quyền truy cập
        if (uri.contains("/listStaff") || uri.contains("/addStaff") || uri.contains("/editStaff") || uri.contains("/deleteStaff")
        || uri.contains("/setShiftStaff") || uri.contains("/searchStaff") || uri.contains("/statistics") || uri.contains("/CouponController")
        || uri.contains("/category-controller") || uri.contains("/product-controller")) {
            if (session == null || session.getAttribute("owner") == null) {
                res.sendRedirect(contextPath + "/KhachHang/login.jsp");
                return;
            }
        } else if (uri.contains("/Staff")) {
            if (uri.contains("insertChat") || uri.contains("chatbox") || uri.contains("getChat") || uri.contains("loadStaffChatList") || uri.contains("loadStaffChatList")) {
                if (session == null || (session.getAttribute("customer") == null && session.getAttribute("staff") == null)){
                    res.sendRedirect(contextPath + "/KhachHang/login.jsp");
                    return;
                }
            }
            else if (session == null || session.getAttribute("staff") == null) {
                res.sendRedirect(contextPath + "/KhachHang/login.jsp");
                return;
            }
        }
        // Nếu hợp lệ, tiếp tục xử lý
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {}
    @Override
    public void destroy() {}
}