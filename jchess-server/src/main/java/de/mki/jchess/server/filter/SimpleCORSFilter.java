package de.mki.jchess.server.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter adding CORS-headers to a response.
 */
@Component
public class SimpleCORSFilter implements Filter {

    /**
     * Adding the CORS-headers to a response.
     * @param req The {@link ServletRequest}
     * @param res The {@link ServletResponse}
     * @param chain The {@link FilterChain}
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        chain.doFilter(req, res);
    }

    /**
     * Initializes the {@link Filter}
     * @param filterConfig The {@link FilterConfig}
     */
    public void init(FilterConfig filterConfig) {}

    /**
     * Destroys the {@link Filter} on shutdown.
     */
    public void destroy() {}
}