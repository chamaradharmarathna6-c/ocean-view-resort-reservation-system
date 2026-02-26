package com.oceanview.servlet;

import com.oceanview.model.User;
import com.oceanview.service.UserService;
import com.oceanview.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userService.authenticate(username, password);

        if (user != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute(Constants.SESSION_USER, user);
            session.setAttribute(Constants.SESSION_USERNAME, user.getUsername());
            session.setAttribute(Constants.SESSION_USER_ROLE, user.getRole());
            session.setMaxInactiveInterval(30 * 60);

            // Set success message and forward to index.jsp
            request.setAttribute("successMessage", "Login Successful!");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", Constants.MSG_INVALID_CREDENTIALS);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}
