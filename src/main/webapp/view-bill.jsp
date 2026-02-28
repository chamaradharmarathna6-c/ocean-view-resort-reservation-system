<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.oceanview.model.User" %>
<%@ page import="com.oceanview.model.Bill" %>
<%@ page import="com.oceanview.util.Constants" %>

<%
    User user = (User) session.getAttribute(Constants.SESSION_USER);
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }

    Bill bill = (Bill) request.getAttribute("bill");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Bill - Ocean View Resort</title>
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
            max-width: 800px;
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

        .bill-container {
            background: white;
            border-radius: 10px;
            padding: 40px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        .bill-header {
            text-align: center;
            border-bottom: 2px solid #008080;
            padding-bottom: 20px;
            margin-bottom: 30px;
        }

        .bill-header h1 {
            color: #008080;
            margin-bottom: 10px;
        }

        .hotel-name {
            font-size: 24px;
            font-weight: bold;
            color: #36454F;
        }

        .bill-section {
            margin-bottom: 30px;
        }

        .section-title {
            font-weight: bold;
            color: #36454F;
            margin-bottom: 15px;
            padding-bottom: 10px;
            border-bottom: 1px solid #C2B280;
            font-size: 14px;
        }

        .info-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
            padding: 8px 0;
        }

        .info-label {
            font-weight: bold;
            color: #36454F;
        }

        .info-value {
            color: #36454F;
        }

        .total-section {
            background-color: #f0f8ff;
            padding: 20px;
            border-radius: 5px;
            margin-bottom: 30px;
        }

        .total-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
            font-size: 16px;
        }

        .total-row.grand-total {
            font-size: 20px;
        .total-section {
            color: #008080;
            border-top: 2px solid #008080;
            padding-top: 20px;
            margin-top: 20px;
        }

        .payment-status {
            text-align: center;
            padding: 15px;
            background-color: #e6ffe6;
            border-radius: 5px;
            color: #006600;
            font-weight: bold;
            margin-bottom: 20px;
        }

        .payment-status.pending {
            background-color: #fff3cd;
            color: #856404;
        }

        .btn-group {
            display: flex;
            gap: 10px;
            justify-content: center;
            margin-top: 30px;
            color:#000080 ;
        }

        .btn {
            padding: 12px 25px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            transition: transform 0.2s;
        }

        .btn-primary {
            background: linear-gradient(135deg, #008080 0%, #36454F 100%);
            color: white;
        }

        .btn-primary:hover {
            transform: scale(1.05);
        }

        .btn-secondary {
            background-color: #C2B280;
            color: #36454F;
        }

        .btn-secondary:hover {
            background-color: #bbb;
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
    </style>
</head>
<body>
    <div class="navbar">
        <h1>Ocean View Resort</h1>
        <div>
            <a href="<%= request.getContextPath() %>/dashboard.jsp">Dashboard</a>
            <a href="<%= request.getContextPath() %>/bills.jsp">Bills</a>
            <a href="<%= request.getContextPath() %>/logout">Logout</a>
        </div>
    </div>

    <div class="container">
        <a href="<%= request.getContextPath() %>/bills.jsp" class="back-link">← Back to Bills</a>

        <%
            String successMessage = (String) request.getAttribute("successMessage");
            if (successMessage != null) {
        %>
            <div class="message success"><%= successMessage %></div>
        <% } %>

        <% if (bill != null) { %>
            <div class="bill-container">
                <div class="bill-header">
                    <div class="hotel-name">OCEAN VIEW RESORT</div>
                    <h1>BILL</h1>
                    <p style="color: #36454F; font-size: 14px;">Galle, Sri Lanka</p>
                </div>

                <div class="bill-section">
                    <div class="section-title">BILL INFORMATION</div>
                    <div class="info-row">
                        <span class="info-label">Bill ID:</span>
                        <span class="info-value"><strong><%= bill.getBillId() %></strong></span>
                    </div>
                    <div class="info-row">
                        <span class="info-label">Date Generated:</span>
                        <span class="info-value"><%= bill.getBillGeneratedDate() %></span>
                    </div>
                </div>

                <div class="bill-section">
                    <div class="section-title">GUEST INFORMATION</div>
                    <div class="info-row">
                        <span class="info-label">Guest Name:</span>
                        <span class="info-value"><%= bill.getGuestName() %></span>
                    </div>
                    <div class="info-row">
                        <span class="info-label">Reservation Number:</span>
                        <span class="info-value"><%= bill.getReservationNumber() %></span>
                    </div>
                </div>

                <div class="bill-section">
                    <div class="section-title">STAY DETAILS</div>
                    <div class="info-row">
                        <span class="info-label">Check-In Date:</span>
                        <span class="info-value"><%= bill.getCheckInDate() %></span>
                    </div>
                    <div class="info-row">
                        <span class="info-label">Check-Out Date:</span>
                        <span class="info-value"><%= bill.getCheckOutDate() %></span>
                    </div>
                    <div class="info-row">
                        <span class="info-label">Number of Nights:</span>
                        <span class="info-value"><%= bill.getNumberOfNights() %></span>
                    </div>
                </div>

                <div class="total-section">
                    <div class="total-row">
                        <span>Room Rate (per night):</span>
                        <span>Rs. <%= String.format("%.2f", bill.getRoomRate()) %></span>
                    </div>
                    <div class="total-row">
                        <span>Number of Nights:</span>
                        <span><%= bill.getNumberOfNights() %></span>
                    </div>
                    <div class="total-row grand-total">
                        <span>TOTAL AMOUNT (LKR):</span>
                        <span>Rs. <%= String.format("%.2f", bill.getTotalCost()) %></span>
                    </div>

                </div>

                <% if ("PENDING".equals(bill.getPaymentStatus())) { %>
                    <div class="payment-status pending">
                         Payment Status: PENDING
                    </div>
                    <div class="btn-group">
                        <form action="<%= request.getContextPath() %>/bill" method="POST">
                            <input type="hidden" name="action" value="markPaid">
                            <input type="hidden" name="billId" value="<%= bill.getBillId() %>">
                            <button type="submit" class="btn btn-primary">Mark as Paid</button>
                        </form>
                    </div>
                <% } else { %>
                    <div class="payment-status">
                        ✓ Payment Status: PAID
                    </div>
                <% } %>

                <div class="btn-group" style="margin-top: 20px;">
                    <button class="btn btn-secondary" onclick="window.print()">Print Bill</button>
                </div>
            </div>
        <% } else { %>
            <div class="bill-container" style="text-align: center; padding: 50px;">
                <p style="color: #36454F; font-size: 16px;">Bill not found.</p>
            </div>
        <% } %>
    </div>
</body>
</html>


