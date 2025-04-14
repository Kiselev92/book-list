package kiselev.anton.booklist.port.filter;

import jakarta.servlet.*;
import java.io.IOException;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = {"/book/*"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        boolean loggedIn = (session != null && session.getAttribute("user") != null);


        String path = req.getRequestURI();

        if (loggedIn || path.equals("/login")) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect("/login");
        }
    }
}
