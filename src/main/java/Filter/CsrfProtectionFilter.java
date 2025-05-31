package Filter;

import jakarta.servlet.annotation.WebFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CsrfProtectionFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // Chỉ kiểm tra với POST request
        if ("POST".equalsIgnoreCase(req.getMethod())) {
            String tokenSession = (String) req.getSession().getAttribute("csrfToken");
            String tokenForm = req.getParameter("csrfToken");

            if (tokenSession == null || tokenForm == null || !tokenSession.equals(tokenForm)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF Token invalid or missing.");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) {}
    public void destroy() {}
}