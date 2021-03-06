package hello;

import org.jboss.logging.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserCORSFilter implements Filter {

    private Logger logger = Logger.getLogger(UserCORSFilter.class.getName());
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {

        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        String originHeader = request.getHeader("Origin");

        response.setHeader("Access-Control-Allow-Origin", originHeader);
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,origin,accept,content-type,access-control-request-method,access-control-request-headers,authorization,client-id");
        response.setHeader("Content-Security-Policy", "default-src 'self'; script-src 'unsafe-inline'; style-src 'self' 'unsafe-inline'; img-src 'self'; connect-src *");
        response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
        if (!"OPTIONS".equals(request.getMethod())) {
            try {
                chain.doFilter(req, res);
            } catch (IOException | ServletException e) {
                logger.debug(e);
            }
        } else {
        }
    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

}