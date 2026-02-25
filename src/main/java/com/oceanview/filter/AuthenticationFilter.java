package com.oceanview.filter;

import com.oceanview.model.User;
import com.oceanview.util.Constants;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class AuthenticationFilter implements Filter {
    private static final String[] PUBLIC_URLS = {"/index.jsp", "/login", "/help"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String path = requestURI.substring(contextPath.length());


        boolean isPublicURL = false;
        for (String publicUrl : PUBLIC_URLS) {
            if (path.equals(publicUrl) || path.contains(publicUrl)) {
                isPublicURL = true;
                break;
            }
        }


        if (isPublicURL) {
            chain.doFilter(request, response);
            return;
        }


        if (session == null || session.getAttribute(Constants.SESSION_USER) == null) {
            httpResponse.sendRedirect(contextPath + "/index.jsp");
            return;
        }


        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}

