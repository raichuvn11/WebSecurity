package Filter;

import ultil.LoginAttempt;
import ultil.LoggerUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@WebFilter(urlPatterns = "/login")
public class LoginRateLimitFilter implements Filter {
    private static final Logger logger = LoggerUtil.getLogger();
    private static final int MAX_ATTEMPTS = 5;
    private static final long LOCK_TIME = 15 * 60 * 1000;
    private static final int MAX_IP_ATTEMPTS = 10;
    private static final long IP_LOCK_TIME = 30 * 60 * 1000;
    private static final Map<String, LoginAttempt> loginAttempts = new ConcurrentHashMap<>();
    private static final Map<String, LoginAttempt> ipAttempts = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if ("POST".equalsIgnoreCase(request.getMethod()) && "/login".equals(request.getServletPath())) {
            String email = request.getParameter("email");
            String ipAddress = getClientIP(request);
            long currentTime = System.currentTimeMillis();

            LoginAttempt ipAttempt = ipAttempts.getOrDefault(ipAddress, new LoginAttempt());

            if (ipAttempt.getAttempts() >= MAX_IP_ATTEMPTS && 
                (currentTime - ipAttempt.getLastAttemptTime() >= IP_LOCK_TIME)) {
                ipAttempt.reset();
                logger.info("Reset số lần thử cho IP: " + ipAddress);
            }

            if (ipAttempt.getAttempts() >= MAX_IP_ATTEMPTS && 
                (currentTime - ipAttempt.getLastAttemptTime() < IP_LOCK_TIME)) {
                logger.warning("IP blocked due to too many attempts: " + ipAddress);
                HttpSession session = request.getSession();
                session.setAttribute("message", "Quá nhiều yêu cầu từ IP của bạn. Vui lòng thử lại sau 30 phút.");
                response.sendRedirect(request.getContextPath() + "/KhachHang/login.jsp");
                return;
            }

            if (email != null) {
                LoginAttempt attempt = loginAttempts.getOrDefault(email, new LoginAttempt());

                if (attempt.getAttempts() >= MAX_ATTEMPTS && 
                    (currentTime - attempt.getLastAttemptTime() >= LOCK_TIME)) {
                    attempt.reset();
                    logger.info("Reset số lần thử cho email: " + email);
                }

                // Kiểm tra nếu tài khoản đã bị khóa
                if (attempt.getAttempts() >= MAX_ATTEMPTS && 
                    (currentTime - attempt.getLastAttemptTime() < LOCK_TIME)) {
                    logger.warning("Account locked due to too many attempts: " + email + " from IP: " + ipAddress);
                    HttpSession session = request.getSession();
                    session.setAttribute("message", "Bạn đã đăng nhập sai quá nhiều lần. Vui lòng thử lại sau 15 phút.");
                    response.sendRedirect(request.getContextPath() + "/KhachHang/login.jsp");
                    return;
                }

                if (attempt.getAttempts() > 0) {
                    try {
                        Thread.sleep(attempt.getAttempts() * 1000); // Tăng 1 giây cho mỗi lần sai
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                attempt.incrementAttempts();
                ipAttempt.incrementAttempts();

                attempt.setLastAttemptTime(currentTime);
                ipAttempt.setLastAttemptTime(currentTime);

                loginAttempts.put(email, attempt);
                ipAttempts.put(ipAddress, ipAttempt);

                logger.info("Tăng số lần thử - Email: " + email + " (" + attempt.getAttempts() + "), IP: " + ipAddress + " (" + ipAttempt.getAttempts() + ")");
            }
        }

        chain.doFilter(req, res);
    }

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Không cần xử lý gì thêm
    }

    @Override
    public void destroy() {
        loginAttempts.clear();
        ipAttempts.clear();
    }
}
