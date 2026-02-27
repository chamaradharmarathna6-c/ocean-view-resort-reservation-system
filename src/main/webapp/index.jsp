<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ocean View Resort - Reservation System</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Arial', sans-serif;
            background: linear-gradient(135deg, #008080 0%, #36454F 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .login-container {
            background: white;
            border-radius: 10px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.3);
            width: 100%;
            max-width: 400px;
            padding: 40px;
        }
        .header { text-align: center; margin-bottom: 30px; }
        .header h1 { color: #008080; font-size: 28px; margin-bottom: 10px; }
        .header p { color: #36454F; font-size: 14px; }
        .form-group { margin-bottom: 20px; }
        .form-group label {
            display: block; margin-bottom: 8px;
            color: #36454F; font-weight: bold; font-size: 14px;
        }
        .form-group input {
            width: 100%; padding: 12px;
            border: 2px solid #C2B280;
            border-radius: 5px; font-size: 14px;
            transition: border-color 0.3s;
        }
        .form-group input:focus {
            outline: none; border-color: #008080; background-color: #f0f8ff;
        }
        .btn-login {
            width: 100%; padding: 12px;
            background: linear-gradient(135deg, #008080 0%, #36454F 100%);
            color: white; border: none; border-radius: 5px;
            font-size: 16px; font-weight: bold;
            cursor: pointer; transition: transform 0.2s;
        }
        .btn-login:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 20px rgba(30, 144, 255, 0.4);
        }
        .error-message {
            background-color: #ffe6e0; color: #cc4422;
            padding: 12px; border-radius: 5px; margin-bottom: 20px;
            border-left: 4px solid #FF7F50;
        }
        .help-link { text-align: center; margin-top: 20px; }
        .help-link a { color: #008080; text-decoration: none; font-size: 14px; }
        .help-link a:hover { text-decoration: underline; }
    </style>
</head>
<body>
<div class="login-container">
    <div class="header">
        <h1>Ocean View Resort</h1>
        <p>Reservation Management System</p>
    </div>


    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
    %>
    <div class="error-message">
        <strong>Login Failed!</strong><br>
        <%= errorMessage %>
    </div>
    <%
        }
    %>


    <form action="<%= request.getContextPath() %>/login" method="POST">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" placeholder="Enter your username" required>
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" placeholder="Enter your password" required>
        </div>

        <button type="submit" class="btn-login">LOGIN</button>
    </form>

    <div class="help-link">
        <a href="<%= request.getContextPath() %>/help.jsp">Need help? Click here</a>
    </div>
</div>


<%
    String successMessage = (String) request.getAttribute("successMessage");
    if (successMessage != null) {
%>
<script>
    alert("<%= successMessage %>");
    window.location.href = "<%= request.getContextPath() %>/dashboard.jsp";
</script>
<%
    }
%>
<%
    String logoutSuccess = (String) session.getAttribute("logoutSuccess");
    if (logoutSuccess != null) {
%>
<script>
    alert("<%= logoutSuccess %>");
</script>
<%

        session.removeAttribute("logoutSuccess");
    }
%>
</body>
</html>
