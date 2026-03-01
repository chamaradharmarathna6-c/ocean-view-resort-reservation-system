<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.oceanview.model.User" %>
<%@ page import="com.oceanview.util.Constants" %>
<%@ page import="java.util.List" %>

<%
    User currentUser = (User) session.getAttribute(Constants.SESSION_USER);
    if (currentUser == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }

    if (!Constants.ROLE_ADMIN.equals(currentUser.getRole())) {
        response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
        return;
    }

    List<User> users = (List<User>) request.getAttribute("users");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Management - Ocean View Resort</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Arial', sans-serif;
            background-color: #f5f5f5;
        }

        .navbar {
            background: linear-gradient(135deg, #008080 0%, #36454F 100%);
            color: white;
            padding: 20px 40px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
        }

        .navbar a {
            color: white;
            text-decoration: none;
            background-color: rgba(255, 255, 255, 0.2);
            padding: 8px 15px;
            border-radius: 5px;
            margin-left: 10px;
            transition: background-color 0.3s;
        }

        .container {
            max-width: 1200px;
            margin: 30px auto;
            padding: 0 20px;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }

        .header h2 {
            color: #008080;
            font-size: 28px;
        }

        .form-section {
            background: white;
            border-radius: 10px;
            padding: 25px;
            margin-bottom: 30px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        .form-section h3 {
            color: #008080;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid #008080;
        }

        .form-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-bottom: 20px;
        }

        .form-group {
            display: flex;
            flex-direction: column;
        }

        .form-group label {
            font-weight: bold;
            margin-bottom: 5px;
            color: #36454F;
            font-size: 14px;
        }

        .form-group input, .form-group select {
            padding: 10px;
            border: 2px solid #C2B280;
            border-radius: 5px;
            font-size: 14px;
        }

        .form-group input:focus, .form-group select:focus {
            outline: none;
            border-color: #008080;
            background-color: #f0f8ff;
        }

        .btn {
            background: linear-gradient(135deg, #008080 0%, #36454F 100%);
            color: white;
            padding: 12px 25px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            transition: transform 0.2s;
        }

        .btn:hover {
            transform: scale(1.05);
        }

        .message {
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }

        .success {
            background-color: #e6ffe6;
            color: #006600;
            border-left: 4px solid #006600;
        }

        .error {
            background-color: #ffe6e6;
            color: #FF7F50;
            border-left: 4px solid #FF7F50;
        }

        .table-section {
            background: white;
            border-radius: 10px;
            padding: 25px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            overflow-x: auto;
        }

        .table-section h3 {
            color: #008080;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid #008080;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }

        table thead {
            background-color: #008080;
            color: white;
        }

        table th, table td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #C2B280;
        }

        table tbody tr:hover {
            background-color: #f9f9f9;
        }

        .action-btns {
            display: flex;
            gap: 10px;
        }

        .action-btns button {
            padding: 6px 12px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            font-size: 12px;
        }

        .delete-btn {
            background-color: #FF7F50;
            color: white;
        }

        .delete-btn:hover {
            opacity: 0.8;
        }

        .badge {
            display: inline-block;
            padding: 4px 8px;
            border-radius: 3px;
            font-size: 12px;
            font-weight: bold;
        }

        .badge-admin {
            background-color: #FFFF00;
            color: #36454F;
        }

        .badge-staff {
            background-color: #008080;
            color: white;
        }
    </style>
</head>
<body>
    <div class="navbar">
        <h1>Ocean View Resort</h1>
        <div>
            <a href="<%= request.getContextPath() %>/dashboard.jsp">Dashboard</a>
            <a href="<%= request.getContextPath() %>/logout">Logout</a>
        </div>
    </div>

    <div class="container">
        <div class="header">
            <h2>User Management</h2>
        </div>

        <%
            String successMessage = (String) request.getAttribute("successMessage");
            if (successMessage != null) {
        %>
            <div class="message success"><%= successMessage %></div>
        <% } %>

        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null) {
        %>
            <div class="message error"><%= errorMessage %></div>
        <% } %>

        <div class="form-section">
            <h3>Add New User</h3>
            <form action="<%= request.getContextPath() %>/user-management" method="POST">
                <input type="hidden" name="action" value="create">
                <div class="form-grid">
                    <div class="form-group">
                        <label>Username</label>
                        <input type="text" name="username" required>
                    </div>
                    <div class="form-group">
                        <label>Password</label>
                        <input type="password" name="password" required>
                    </div>
                    <div class="form-group">
                        <label>Full Name</label>
                        <input type="text" name="fullName" required>
                    </div>
                    <div class="form-group">
                        <label>Email</label>
                        <input type="email" name="email" required>
                    </div>
                    <div class="form-group">
                        <label>Role</label>
                        <select name="role" required>
                            <option value=""> Select the Role </option>
                            <option value="ADMIN">Admin</option>
                            <option value="STAFF">Staff</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn" style="margin-top: auto;">Create User</button>
                    </div>
                </div>
            </form>
        </div>

        <div class="table-section">
            <h3>All Users</h3>
            <% if (users != null && !users.isEmpty()) { %>
                <table>
                    <thead>
                        <tr>
                            <th>Username</th>
                            <th>Full Name</th>
                            <th>Email</th>
                            <th>Role</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (User u : users) { %>
                            <tr>
                                <td><strong><%= u.getUsername() %></strong></td>
                                <td><%= u.getFullName() %></td>
                                <td><%= u.getEmail() %></td>
                                <td>
                                    <% if (Constants.ROLE_ADMIN.equals(u.getRole())) { %>
                                        <span class="badge badge-admin">ADMIN</span>
                                    <% } else { %>
                                        <span class="badge badge-staff">STAFF</span>
                                    <% } %>
                                </td>
                                <td><%= u.isActive() ? "Active" : "Inactive" %></td>
                                <td>
                                    <% if (!u.getUserId().equals(currentUser.getUserId())) { %>
                                        <div class="action-btns">
                                            <form action="<%= request.getContextPath() %>/user-management" method="POST" style="display:inline;">
                                                <input type="hidden" name="action" value="delete">
                                                <input type="hidden" name="userId" value="<%= u.getUserId() %>">
                                                <button type="submit" class="delete-btn" onclick="return confirm('Are you sure?')">Delete</button>
                                            </form>
                                        </div>
                                    <% } else { %>
                                        <span style="color: #36454F; font-size: 12px;">Current User</span>
                                    <% } %>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } else { %>
                <p style="text-align: center; color: #36454F; padding: 20px;">No users found.</p>
            <% } %>
        </div>
    </div>
</body>
</html>


