<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.oceanview.model.User" %>
<%@ page import="com.oceanview.util.Constants" %>
<%
    User user = (User) session.getAttribute(Constants.SESSION_USER);
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Ocean View Resort</title>
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

        .navbar h1 {
            font-size: 24px;
        }

        .navbar .user-info {
            display: flex;
            align-items: center;
            gap: 20px;
        }

        .navbar .user-info span {
            font-size: 14px;
        }

        .navbar a {
            color: white;
            text-decoration: none;
            background-color: rgba(255, 255, 255, 0.2);
            padding: 8px 15px;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        .navbar a:hover {
            background-color: rgba(255, 255, 255, 0.4);
        }

        .container {
            max-width: 1200px;
            margin: 30px auto;
            padding: 0 20px;
        }

        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }

        .card {
            background: white;
            border-radius: 10px;
            padding: 25px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            text-align: center;
            cursor: pointer;
            transition: transform 0.3s, box-shadow 0.3s;
        }

        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.15);
        }

        .card-icon {
            font-size: 40px;
            margin-bottom: 15px;
        }

        .card h2 {
            color: #008080;
            margin-bottom: 10px;
            font-size: 20px;
        }

        .card p {
            color: #36454F;
            font-size: 14px;
            margin-bottom: 15px;
        }

        .card a {
            display: inline-block;
            background: linear-gradient(135deg, #008080 0%, #36454F 100%);
            color: white;
            padding: 10px 20px;
            border-radius: 5px;
            text-decoration: none;
            transition: transform 0.2s;
        }

        .card a:hover {
            transform: scale(1.05);
        }

        .welcome-section {
            background: white;
            border-radius: 10px;
            padding: 30px;
            margin-bottom: 30px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        .welcome-section h2 {
            color: #008080;
            margin-bottom: 10px;
        }

        .welcome-section p {
            color: #36454F;
            line-height: 1.6;
        }

        .admin-only {
            border: 2px solid #FFFF00;
            background-color: #fffef0;
        }

        .admin-only .card-icon {
            color: #FFFF00;
        }
    </style>
</head>
<body>
    <div class="navbar">
        <h1>Ocean View Resort</h1>
        <div class="user-info">
            <span>Welcome, <strong><%= user.getFullName() %></strong></span>
            <a href="<%= request.getContextPath() %>/logout">Logout</a>
        </div>
    </div>

    <div class="container">
        <div class="welcome-section">
            <h2>Welcome to Ocean View Resort Reservation System</h2>
            <p>
                This system helps you manage hotel reservations efficiently. You can add new reservations,
                view reservation details, calculate bills, and generate reports.
            </p>
        </div>

        <div class="dashboard-grid">
            <div class="card">
                <div class="card-icon">📆</div>
                <h2>Reservations</h2>
                <p>Manage guest reservations</p>
                <a href="<%= request.getContextPath() %>/reservations.jsp">View & Manage</a>
            </div>

            <div class="card">
                <div class="card-icon">🧾</div>
                <h2>Bills</h2>
                <p>View and manage guest bills</p>
                <a href="<%= request.getContextPath() %>/bills.jsp">View Bills</a>
            </div>

            <div class="card">
                <div class="card-icon">📊</div>
                <h2>Reports</h2>
                <p>View revenue and occupancy reports</p>
                <a href="<%= request.getContextPath() %>/reports.jsp">View Reports</a>
            </div>

            <% if (Constants.ROLE_ADMIN.equals(user.getRole())) { %>
            <div class="card admin-only">
                <div class="card-icon">👤</div>
                <h2>User Management</h2>
                <p>Manage system users (Admin)</p>
                <a href="<%= request.getContextPath() %>/user-management.jsp">Manage Users</a>
            </div>
            <% } %>

            <div class="card">
                <div class="card-icon">❓</div>
                <h2>Help & Support</h2>
                <p>Get help using the system</p>
                <a href="<%= request.getContextPath() %>/help.jsp">Get Help</a>
            </div>
        </div>
    </div>

</body>
</html>


