<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.oceanview.model.User" %>
<%@ page import="com.oceanview.model.Reservation" %>
<%@ page import="com.oceanview.model.Bill" %>
<%@ page import="com.oceanview.util.Constants" %>

<%
    User user = (User) session.getAttribute(Constants.SESSION_USER);
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }

    Reservation reservation = (Reservation) request.getAttribute("reservation");
    Bill bill = (Bill) request.getAttribute("bill");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Reservation - Ocean View Resort</title>
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
        }

        .container {
            max-width: 900px;
            margin: 30px auto;
            padding: 0 20px;
        }

        .back-link {
            display: inline-block;
            margin-bottom: 20px;
            color: #008080;
            text-decoration: none;
            font-size: 14px;
        }

        .back-link:hover {
            text-decoration: underline;
        }

        .card {
            background: white;
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
        }

        .card h2 {
            color: #008080;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid #008080;
        }

        .info-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 20px;
        }

        .info-item {
            display: flex;
            flex-direction: column;
        }

        .info-label {
            font-weight: bold;
            color: #36454F;
            font-size: 12px;
            text-transform: uppercase;
            margin-bottom: 5px;
        }

        .info-value {
            color: #36454F;
            font-size: 16px;
        }

        .btn-group {
            display: flex;
            gap: 10px;
            margin-top: 20px;
        }

        .btn {
            padding: 12px 25px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            text-decoration: none;
            transition: transform 0.2s;
        }

        .btn-primary {
            background: linear-gradient(135deg, #008080 0%, #36454F 100%);
            color: white;
        }

        .btn-primary:hover {
            transform: scale(1.05);
        }

        .message {
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }

        .success {
            background-color: #e6f9f7;
            color: #006666;
            border-left: 4px solid #008080;
        }

        .error {
            background-color: #ffe6e0;
            color: #cc4422;
            border-left: 4px solid #FF7F50;
        }

        .paid-badge {
            background-color: #9DC183;
            color: white;
            padding: 6px 12px;
            border-radius: 3px;
            font-size: 12px;
            font-weight: bold;
        }

        .pending-badge {
            background-color: #FF7F50;
            color: white;
            padding: 6px 12px;
            border-radius: 3px;
            font-size: 12px;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="navbar">
        <h1>Ocean View Resort</h1>
        <div>
            <a href="<%= request.getContextPath() %>/dashboard.jsp">Dashboard</a>
            <a href="<%= request.getContextPath() %>/reservations.jsp">Reservations</a>
            <a href="<%= request.getContextPath() %>/logout">Logout</a>
        </div>
    </div>

    <div class="container">
        <a href="<%= request.getContextPath() %>/reservations.jsp" class="back-link">← Back to Reservations</a>

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

        <% if (reservation != null) { %>
            <div class="card">
                <h2>Reservation Details</h2>
                <div class="info-grid">
                    <div class="info-item">
                        <span class="info-label">Reservation Number</span>
                        <span class="info-value"><strong><%= reservation.getReservationNumber() %></strong></span>
                    </div>
                    <div class="info-item">
                        <span class="info-label">Guest Name</span>
                        <span class="info-value"><%= reservation.getGuestName() %></span>
                    </div>
                    <div class="info-item">
                        <span class="info-label">Address</span>
                        <span class="info-value"><%= reservation.getAddress() %></span>
                    </div>
                    <div class="info-item">
                        <span class="info-label">Contact Number</span>
                        <span class="info-value"><%= reservation.getContactNumber() %></span>
                    </div>
                    <div class="info-item">
                        <span class="info-label">Room Type</span>
                        <span class="info-value"><%= reservation.getRoomType() %></span>
                    </div>
                    <div class="info-item">
                        <span class="info-label">Check-In Date</span>
                        <span class="info-value"><%= reservation.getCheckInDate() %></span>
                    </div>
                    <div class="info-item">
                        <span class="info-label">Check-Out Date</span>
                        <span class="info-value"><%= reservation.getCheckOutDate() %></span>
                    </div>
                    <div class="info-item">
                        <span class="info-label">Number of Nights</span>
                        <span class="info-value"><%= reservation.getNumberOfNights() %></span>
                    </div>
                    <div class="info-item">
                        <span class="info-label">Status</span>
                        <span class="info-value"><%= reservation.getStatus() %></span>
                    </div>
                </div>

                <div class="btn-group">
                    <form action="<%= request.getContextPath() %>/reservation" method="POST" style="display:inline;">
                        <input type="hidden" name="action" value="calculateBill">
                        <input type="hidden" name="reservationNumber" value="<%= reservation.getReservationNumber() %>">
                        <button type="submit" class="btn btn-primary">Calculate Bill</button>
                    </form>
                </div>
            </div>

            <% if (bill != null) { %>
                <div class="card">
                    <h2>Bill Details</h2>
                    <div class="info-grid">
                        <div class="info-item">
                            <span class="info-label">Bill ID</span>
                            <span class="info-value"><strong><%= bill.getBillId() %></strong></span>
                        </div>
                        <div class="info-item">
                            <span class="info-label">Guest Name</span>
                            <span class="info-value"><%= bill.getGuestName() %></span>
                        </div>
                        <div class="info-item">
                            <span class="info-label">Number of Nights</span>
                            <span class="info-value"><%= bill.getNumberOfNights() %></span>
                        </div>
                        <div class="info-item">
                            <span class="info-label">Room Rate (per night)</span>
                            <span class="info-value">Rs. <%= String.format("%.2f", bill.getRoomRate()) %></span>
                        </div>
                        <div class="info-item">
                            <span class="info-label">Total Cost (LKR)</span>
                            <span class="info-value" style="font-size: 20px; color: #008080;"><strong>Rs. <%= String.format("%.2f", bill.getTotalCost()) %></strong></span>
                        </div>
                        <div class="info-item">
                            <span class="info-label">Payment Status</span>
                            <span class="info-value">
                                <% if ("PAID".equals(bill.getPaymentStatus())) { %>
                                    <span class="paid-badge">✓ PAID</span>
                                <% } else { %>
                                    <span class="pending-badge">⏳ <%= bill.getPaymentStatus() %></span>
                                <% } %>
                            </span>
                        </div>
                    </div>

                    <div class="btn-group">
                        <a href="<%= request.getContextPath() %>/bill?action=view&billId=<%= bill.getBillId() %>" class="btn btn-primary">View Full Bill & Manage Payment</a>
                    </div>
                </div>
            <% } %>
        <% } else { %>
            <div class="card" style="text-align: center; padding: 50px;">
                <p style="color: #000080; font-size: 16px;">Reservation not found.</p>
            </div>
        <% } %>
    </div>
</body>
</html>


