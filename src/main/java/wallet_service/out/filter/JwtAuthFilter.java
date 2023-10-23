package wallet_service.out.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import wallet_service.out.util.JwtProvider;

import java.io.IOException;

public class JwtAuthFilter implements Filter {

    private JwtProvider jwtProvider;

    public JwtAuthFilter() {
        this.jwtProvider = new JwtProvider();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String jwtToken = req.getHeader("YourSecretKey");
        if (jwtToken == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String username;
        try {
            username = jwtProvider.validateToken(jwtToken);
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        req.setAttribute("username", username);

        filterChain.doFilter(req, resp);
    }
}