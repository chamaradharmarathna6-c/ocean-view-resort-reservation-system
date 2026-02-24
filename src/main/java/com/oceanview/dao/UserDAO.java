package com.oceanview.dao;

import com.oceanview.model.User;
import com.oceanview.util.DatabaseConnectionPool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDAO {
    private DatabaseConnectionPool connectionPool = DatabaseConnectionPool.getInstance();


    public boolean createUser(User user) {
        String sql = "INSERT INTO Users (userId, username, password, fullName, email, role, active, createdDate, createdBy) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";


        Connection connection = connectionPool.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getFullName());
            pstmt.setString(5, user.getEmail());
            pstmt.setString(6, user.getRole());
            pstmt.setBoolean(7, user.isActive());
            pstmt.setLong(8, user.getCreatedDate());
            pstmt.setString(9, user.getCreatedBy());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }


    public User authenticateUser(String username, String password) {
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ? AND active = 1";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getString("userId"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("fullName"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setActive(rs.getBoolean("active"));
                user.setCreatedDate(rs.getLong("createdDate"));
                user.setCreatedBy(rs.getString("createdBy"));
                return user;
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }


    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE username = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getString("userId"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("fullName"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setActive(rs.getBoolean("active"));
                user.setCreatedDate(rs.getLong("createdDate"));
                user.setCreatedBy(rs.getString("createdBy"));
                return user;
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Error getting user: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }


    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users ORDER BY createdDate DESC";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getString("userId"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("fullName"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setActive(rs.getBoolean("active"));
                user.setCreatedDate(rs.getLong("createdDate"));
                user.setCreatedBy(rs.getString("createdBy"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all users: " + e.getMessage());
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return users;
    }


    public boolean updateUser(User user) {
        String sql = "UPDATE Users SET fullName = ?, email = ?, role = ?, active = ? WHERE userId = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getFullName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getRole());
            pstmt.setBoolean(4, user.isActive());
            pstmt.setString(5, user.getUserId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    public boolean deleteUser(String userId) {
        String sql = "DELETE FROM Users WHERE userId = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    public boolean usernameExists(String username) {
        String sql = "SELECT COUNT(*) as count FROM Users WHERE username = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error checking username existence: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }
}

