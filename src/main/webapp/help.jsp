<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.oceanview.model.User" %>
<%@ page import="com.oceanview.util.Constants" %>

<%
    User user = (User) session.getAttribute(Constants.SESSION_USER);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Help & Support - Ocean View Resort</title>
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
            max-width: 1000px;
            margin: 30px auto;
            padding: 0 20px;
        }

        .header {
            margin-bottom: 30px;
        }

        .header h2 {
            color: #008080;
            font-size: 28px;
            margin-bottom: 10px;
        }

        .header p {
            color: #36454F;
            font-size: 14px;
        }

        .card {
            background: white;
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
        }

        .card h3 {
            color: #008080;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid #008080;
            font-size: 20px;
        }

        .faq-item {
            margin-bottom: 20px;
            padding-bottom: 20px;
            border-bottom: 1px solid #eee;
        }

        .faq-item:last-child {
            border-bottom: none;
        }

        .faq-question {
            font-weight: bold;
            color: #36454F;
            margin-bottom: 10px;
            cursor: pointer;
            font-size: 15px;
        }

        .faq-question::before {
            content: "▶ ";
            margin-right: 5px;
        }

        .faq-answer {
            color: #36454F;
            line-height: 1.6;
            font-size: 14px;
            margin-left: 20px;
        }

        .feature-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin: 20px 0;
        }

        .feature-box {
            background: linear-gradient(135deg, #f0f8ff 0%, #ffffff 100%);
            border-left: 4px solid #008080;
            padding: 20px;
            border-radius: 5px;
        }

        .feature-box h4 {
            color: #008080;
            margin-bottom: 10px;
            font-size: 16px;
        }

        .feature-box p {
            color: #36454F;
            font-size: 14px;
            line-height: 1.5;
        }

        .btn {
            background: linear-gradient(135deg, #008080 0%, #36454F 100%);
            color: white;
            padding: 12px 25px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
            transition: transform 0.2s;
        }

        .btn:hover {
            transform: scale(1.05);
        }

        .step-guide {
            background-color: #f9f9f9;
            border-left: 4px solid #008080;
            padding: 20px;
            border-radius: 5px;
            margin: 15px 0;
        }

        .step-guide ol {
            margin-left: 20px;
            color: #36454F;
            line-height: 1.8;
        }

        .step-guide li {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <div class="navbar">
        <h1>Ocean View Resort</h1>
        <div>
            <% if (user != null) { %>
                <a href="<%= request.getContextPath() %>/dashboard.jsp">Dashboard</a>
                <a href="<%= request.getContextPath() %>/logout">Logout</a>
            <% } else { %>
                <a href="<%= request.getContextPath() %>/index.jsp">Login</a>
            <% } %>
        </div>
    </div>

    <div class="container">
        <div class="header">
            <h2>❓ Help & Support</h2>
            <p>Welcome to Ocean View Resort Reservation System. Here you'll find helpful information about using our system.</p>
        </div>

        <div class="card">
            <h3>📋 System Overview</h3>
            <p style="color: #36454F; margin-bottom: 15px;">
                The Ocean View Resort Reservation System is a comprehensive solution for managing hotel reservations,
                billing, and reporting. It provides a user-friendly interface for both staff and administrators.
            </p>

            <div class="feature-grid">
                <div class="feature-box">
                    <h4>👥 User Roles</h4>
                    <p>The system supports two user roles: Admin (full access) and Staff (limited access to reservations and bills).</p>
                </div>
                <div class="feature-box">
                    <h4>📅 Reservations</h4>
                    <p>Manage guest reservations with details like check-in/check-out dates, room types, and guest information.</p>
                </div>
                <div class="feature-box">
                    <h4>💰 Billing</h4>
                    <p>Automatically calculate bills based on room rates and number of nights. Track payment status easily.</p>
                </div>
                <div class="feature-box">
                    <h4>📊 Reports</h4>
                    <p>Generate revenue reports and room occupancy reports to monitor hotel performance.</p>
                </div>
            </div>
        </div>

        <div class="card">
            <h3>🚀 Getting Started</h3>

            <h4 style="color: #36454F; margin: 20px 0 10px;">Step 1: Login</h4>
            <div class="step-guide">
                <ol>
                    <li>Open the reservation system login page</li>
                    <li>Enter your username and password</li>
                    <li>Click the "LOGIN" button</li>
                    <li>You'll be redirected to the dashboard</li>
                </ol>
            </div>

            <h4 style="color: #36454F; margin: 20px 0 10px;">Step 2: Add a Reservation</h4>
            <div class="step-guide">
                <ol>
                    <li>Navigate to "Reservations" from the dashboard</li>
                    <li>Fill in the guest details (name, address, contact number)</li>
                    <li>Select the room type and dates</li>
                    <li>Click "Create Reservation"</li>
                    <li>The reservation number will be generated automatically</li>
                </ol>
            </div>

            <h4 style="color: #36454F; margin: 20px 0 10px;">Step 3: Calculate and View Bills</h4>
            <div class="step-guide">
                <ol>
                    <li>Go to "Reservations" and click "View" on the reservation</li>
                    <li>Click "Calculate Bill"</li>
                    <li>The bill will be generated based on room rate and number of nights</li>
                    <li>View the bill details and mark as paid when payment is received</li>
                </ol>
            </div>
        </div>

        <div class="card">
            <h3>❓ Frequently Asked Questions</h3>

            <div class="faq-item">
                <div class="faq-question">How do I change my password?</div>
                <div class="faq-answer">
                    Currently, you'll need to contact your system administrator to change your password.
                    The admin can create a new user account for you.
                </div>
            </div>

            <div class="faq-item">
                <div class="faq-question">What are the room types and rates?</div>
                <div class="faq-answer">
                    The hotel offers four room types:
                    <ul style="margin-left: 20px; margin-top: 5px;">
                        <li>Single Room: Rs. 5,000 per night</li>
                        <li>Double Room: Rs. 8,000 per night</li>
                        <li>Suite: Rs. 12,000 per night</li>
                        <li>Deluxe Room: Rs. 15,000 per night</li>
                    </ul>
                </div>
            </div>

            <div class="faq-item">
                <div class="faq-question">Are there any taxes or VAT applied to bills?</div>
                <div class="faq-answer">
                    No, all prices are shown in LKR without any tax or VAT. The bill amount shown is the final amount.
                </div>
            </div>

            <div class="faq-item">
                <div class="faq-question">Can I delete a reservation?</div>
                <div class="faq-answer">
                    Yes, you can delete a reservation by viewing it and clicking the "Delete" button.
                    Associated bills will also be removed.
                </div>
            </div>

            <div class="faq-item">
                <div class="faq-question">How do I generate reports?</div>
                <div class="faq-answer">
                    Go to the "Reports" section from the dashboard. You can generate revenue reports and
                    room occupancy reports by selecting the desired date range.
                </div>
            </div>

            <div class="faq-item">
                <div class="faq-question">Who can manage users?</div>
                <div class="faq-answer">
                    Only administrators can access the User Management section. Regular staff members cannot add or delete users.
                </div>
            </div>
        </div>

        <div class="card">
            <h3>📞 Support</h3>
            <p style="color: #36454F; margin-bottom: 15px;">
                If you encounter any issues or need further assistance, please contact your system administrator.
            </p>
            <p style="color: #36454F;">
                <strong>Hotel Contact:</strong><br>
                Ocean View Resort, Galle, Sri Lanka<br>
                Email: info@oceanviewresort.com<br>
                Phone: +94766186241 | 0112265200
            </p>
        </div>
    </div>
</body>
</html>


