package com.oceanview.service;

import com.oceanview.dao.UserDAO;
import com.oceanview.model.User;
import com.oceanview.util.Constants;
import java.util.UUID;


public class UserService {
    private UserDAO userDAO = new UserDAO();


    public User authenticate(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            System.err.println("Username and password cannot be empty");
            return null;
        }

        User user = userDAO.authenticateUser(username, password);
        if (user != null && user.isActive()) {
            return user;
        }
        return null;
    }


    public boolean createUser(String username, String password, String fullName, String email, String role) {

        if (userDAO.usernameExists(username)) {
            System.err.println(Constants.MSG_USER_ALREADY_EXISTS);
            return false;
        }


        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            System.err.println("Username and password cannot be empty");
            return false;
        }

        String userId = "USR-" + UUID.randomUUID().toString().substring(0, 8);
        User user = new User(userId, username, password, fullName, email, role, true);
        user.setCreatedBy("ADMIN");

        return userDAO.createUser(user);
    }


    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }


    public java.util.List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }


    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }


    public boolean deleteUser(String userId) {
        return userDAO.deleteUser(userId);
    }


    public boolean isAdmin(User user) {
        return user != null && user.getRole().equals(Constants.ROLE_ADMIN);
    }


    public boolean isStaff(User user) {
        return user != null && user.getRole().equals(Constants.ROLE_STAFF);
    }
}

