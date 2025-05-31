package Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

@WebFilter("/*")
public class CSPFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (response instanceof HttpServletResponse && request instanceof HttpServletRequest) {
            HttpServletResponse httpResp = (HttpServletResponse) response;
            HttpServletRequest httpReq = (HttpServletRequest) request;

            // Tạo nonce ngẫu nhiên
            byte[] nonceBytes = new byte[16];
            new SecureRandom().nextBytes(nonceBytes);
            String nonce = Base64.getEncoder().encodeToString(nonceBytes);

            // Lưu nonce vào request attribute để dùng trong JSP hoặc HTML
            httpReq.setAttribute("cspNonce", nonce);

            // CSP header với nonce, bổ sung form-action để tránh lỗi
            String csp = "default-src 'self'; " +
                    "script-src 'self' data: 'nonce-" + nonce + "' https://unpkg.com https://cdnjs.cloudflare.com https://cdn.jsdelivr.net https://stackpath.bootstrapcdn.com http://maxcdn.bootstrapcdn.com http://code.jquery.com  ; " +
                    "style-src 'self' data: 'nonce-" + nonce + "' https://cdnjs.cloudflare.com https://unpkg.com https://fonts.googleapis.com https://cdn.jsdelivr.net https://stackpath.bootstrapcdn.com http://maxcdn.bootstrapcdn.com ;" +
                    "img-src 'self' data: https://via.placeholder.com https://cellphones.com.vn; " +
                    "font-src 'self' data: https://cdnjs.cloudflare.com https://fonts.googleapis.com https://fonts.gstatic.com https://maxcdn.bootstrapcdn.com; " +
                    "object-src 'none'; " +
                    "frame-ancestors 'none'; " +
                    "form-action 'self'; " +
                    "base-uri 'self';";

            httpResp.setHeader("Content-Security-Policy", csp);
        }

        chain.doFilter(request, response);
    }
}