package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

public class CsrfTokenGeneratorFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        String tokenSession = (String) req.getSession().getAttribute("csrfToken");
        if (tokenSession == null) {
            tokenSession = UUID.randomUUID().toString();
            req.getSession().setAttribute("csrfToken", tokenSession);
        }

        // Đặt lại vào request scope để dễ dùng trong JSP
        req.setAttribute("csrfToken", tokenSession);

        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) {}
    public void destroy() {}
}
