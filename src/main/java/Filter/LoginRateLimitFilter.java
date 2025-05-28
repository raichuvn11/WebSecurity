package Filter;

import ultil.LoginAttempt;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebFilter(
        urlPatterns = {"/login"}, // Chỉ áp dụng cho URL login
        servletNames = {"LoginServlet"} // Chỉ áp dụng cho servlet login
)
public class LoginRateLimitFilter implements Filter {
    private static final int MAX_ATTEMPTS = 5;
    private static final long LOCK_TIME = 15 * 60 * 1000; // 15 phút

    // Lưu map email -> login attempts (giống bạn)
    private static final Map<String, LoginAttempt> loginAttempts = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (request.getServletPath().equals("/login") && "POST".equalsIgnoreCase(request.getMethod())) {
            String email = request.getParameter("email");
            if (email != null) {
                LoginAttempt attempt = loginAttempts.getOrDefault(email, new LoginAttempt());
                long currentTime = System.currentTimeMillis();

                if (attempt.getAttempts() >= MAX_ATTEMPTS && (currentTime - attempt.getLastAttemptTime() < LOCK_TIME)) {
                    // Trả lỗi 429 hoặc 403
                    response.setStatus(429); // Too Many Requests
                    response.getWriter().write("Bạn đã đăng nhập sai quá nhiều lần. Vui lòng thử lại sau 15 phút.");
                    return;  // Không chuyển tiếp tới servlet login
                }
            }
        }

        // Chuyển tiếp bình thường nếu không bị giới hạn
        chain.doFilter(req, res);
    }

    // Bạn cần đảm bảo đồng bộ với nơi update loginAttempts khi login fail
}
