package Filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ultil.IntegrityVerifier;
import ultil.LoggerUtil;

import java.io.IOException;
import java.util.logging.Logger;

public class IntegrityCheckFilter implements Filter {
    private static final Logger logger = LoggerUtil.getLogger();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Initializing IntegrityCheckFilter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Kiểm tra chữ ký số trong header
        String signature = httpRequest.getHeader("X-Request-Signature");
        String timestamp = httpRequest.getHeader("X-Request-Timestamp");
        String requestBody = getRequestBody(httpRequest);

        if (signature != null && timestamp != null) {
            try {
                // Tạo chuỗi dữ liệu cần xác thực
                String dataToVerify = timestamp + "|" + requestBody;

                // Xác minh chữ ký
                if (!IntegrityVerifier.verifySignature(dataToVerify, signature)) {
                    logger.warning("Invalid request signature detected");
                    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    httpResponse.getWriter().write("{\"error\": \"Invalid request signature\"}");
                    return;
                }

                // Kiểm tra timestamp để tránh replay attack
                long requestTime = Long.parseLong(timestamp);
                long currentTime = System.currentTimeMillis();
                if (Math.abs(currentTime - requestTime) > 300000) { // 5 phút
                    logger.warning("Request timestamp expired");
                    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    httpResponse.getWriter().write("{\"error\": \"Request timestamp expired\"}");
                    return;
                }
            } catch (Exception e) {
                logger.severe("Error verifying request integrity: " + e.getMessage());
                httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                httpResponse.getWriter().write("{\"error\": \"Internal server error\"}");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info("Destroying IntegrityCheckFilter");
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        // Đọc request body
        StringBuilder buffer = new StringBuilder();
        try (ServletInputStream inputStream = request.getInputStream()) {
            byte[] b = new byte[1024];
            int len;
            while ((len = inputStream.read(b)) != -1) {
                buffer.append(new String(b, 0, len));
            }
        }
        return buffer.toString();
    }
} 