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
import java.util.List;


public class UserManagementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        User currentUser = (User) session.getAttribute(Constants.SESSION_USER);
        if (currentUser == null || !Constants.ROLE_ADMIN.equals(currentUser.getRole())) {
            request.setAttribute("errorMessage", "Access denied. Admin only.");
            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
            return;
        }

        String action = request.getParameter("action");

        if ("create".equals(action)) {
            createUser(request, response);
        } else if ("update".equals(action)) {
            updateUser(request, response);
        } else if ("delete".equals(action)) {
            deleteUser(request, response);
        } else {
            listUsers(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equals(action)) {
            listUsers(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/user-management.jsp");
        }
    }

    private void createUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String role = request.getParameter("role");

        if (userService.createUser(username, password, fullName, email, role)) {
            request.setAttribute("successMessage", "User created successfully");
        } else {
            request.setAttribute("errorMessage", "Failed to create user. User may already exist.");
        }

        listUsers(request, response);
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = request.getParameter("userId");
        User user = userService.getUserByUsername(request.getParameter("username"));

        if (user != null) {
            user.setFullName(request.getParameter("fullName"));
            user.setEmail(request.getParameter("email"));
            user.setRole(request.getParameter("role"));

            if (userService.updateUser(user)) {
                request.setAttribute("successMessage", "User updated successfully");
            } else {
                request.setAttribute("errorMessage", "Failed to update user");
            }
        }

        listUsers(request, response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = request.getParameter("userId");

        if (userService.deleteUser(userId)) {
            request.setAttribute("successMessage", "User deleted successfully");
        } else {
            request.setAttribute("errorMessage", "Failed to delete user");
        }

        listUsers(request, response);
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> users = userService.getAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/user-management.jsp").forward(request, response);
    }
}

