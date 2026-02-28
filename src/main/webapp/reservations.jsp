<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.oceanview.model.User" %>
<%@ page import="com.oceanview.model.Reservation" %>
<%@ page import="com.oceanview.service.ReservationService" %>
<%@ page import="com.oceanview.util.Constants" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>

<%
    User user = (User) session.getAttribute(Constants.SESSION_USER);
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }

    ReservationService reservationService = new ReservationService();
    List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
    if (reservations == null) {
        reservations = reservationService.getReservationsWithPendingPayment();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reservations - Ocean View Resort</title>

    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }

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
        }

        .navbar a {
            color: white;
            text-decoration: none;
            background-color: rgba(255,255,255,0.2);
            padding: 8px 15px;
            border-radius: 5px;
            margin-left: 10px;
        }

        .container {
            max-width: 1200px;
            margin: 30px auto;
            padding: 0 20px;
        }

        .header h2 { color: #008080; }

        .btn {
            background: linear-gradient(135deg, #008080 0%, #36454F 100%);
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .form-section, .table-section {
            background: white;
            border-radius: 10px;
            padding: 25px;
            margin-bottom: 30px;
        }

        .form-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-bottom: 20px;
        }

        .form-group { display: flex; flex-direction: column; }

        .form-group input, .form-group select {
            padding: 10px;
            border: 2px solid #C2B280;
            border-radius: 5px;
        }

        .form-group input:focus, .form-group select:focus {
            outline: none;
            border-color: #008080;
        }

        .message {
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }

        .success {
            background-color: #e6ffe6;
            color: #006600;
        }

        .error {
            background-color: #ffe6e6;
            color: #FF0000;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        table thead {
            background-color: #008080;
            color: white;
        }

        table th, table td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
        }

        .action-btns a, .action-btns button {
            padding: 5px 10px;
            border-radius: 4px;
            border: none;
            cursor: pointer;
            font-size: 12px;
        }

        .view-btn { background-color: #008080; color: white; }
        .delete-btn { background-color: #FF7F50; color: white; }
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
        <h2>Reservations</h2>
    </div>

    <% String successMessage = (String) request.getAttribute("successMessage");
        if (successMessage != null) { %>
    <div class="message success"><%= successMessage %></div>
    <% } %>

    <% String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) { %>
    <div class="message error"><%= errorMessage %></div>
    <% } %>


    <div class="form-section">
        <h3>Add New Reservation</h3>
        <form action="<%= request.getContextPath() %>/reservation" method="POST">
            <input type="hidden" name="action" value="add">

            <div class="form-grid">

                <div class="form-group">
                    <label>Guest Name</label>
                    <input type="text" name="guestName" required>
                </div>

                <div class="form-group">
                    <label>Address</label>
                    <input type="text" name="address" required>
                </div>

                <div class="form-group">
                    <label>Contact Number</label>
                    <input type="tel"
                           name="contactNumber"
                           required
                           pattern="07\d{8}"
                           maxlength="10"
                           title="Enter a valid Sri Lankan mobile number (07XXXXXXXX)">
                </div>

                <div class="form-group">
                    <label>Room Type</label>
                    <select name="roomType" required>
                        <option value="">Select Room Type</option>
                        <option value="SINGLE">Single (Rs. 5,000/night)</option>
                        <option value="DOUBLE">Double (Rs. 8,000/night)</option>
                        <option value="SUITE">Suite (Rs. 12,000/night)</option>
                        <option value="DELUXE">Deluxe (Rs. 15,000/night)</option>
                    </select>
                </div>

                <div class="form-group">
                    <label>Check-In Date</label>
                    <input type="date" name="checkInDate" required min="<%= LocalDate.now() %>">
                </div>

                <div class="form-group">
                    <label>Check-Out Date</label>
                    <input type="date" name="checkOutDate" required min="<%= LocalDate.now() %>">
                </div>

            </div>

            <button type="submit" class="btn">Create Reservation</button>
        </form>
    </div>


    <div class="table-section">
        <h3>All Reservations</h3>

        <% if (reservations != null && !reservations.isEmpty()) { %>
        <table>
            <thead>
            <tr>
                <th>Reservation #</th>
                <th>Guest Name</th>
                <th>Room Type</th>
                <th>Check-In</th>
                <th>Check-Out</th>
                <th>Status</th>
                <th>Contact</th>
                <th>Actions</th>
            </tr>
            </thead>

            <tbody>
            <% for (Reservation res : reservations) { %>
            <tr>
                <td><strong><%= res.getReservationNumber() %></strong></td>
                <td><%= res.getGuestName() %></td>
                <td><%= res.getRoomType() %></td>
                <td><%= res.getCheckInDate() %></td>
                <td><%= res.getCheckOutDate() %></td>
                <td><%= res.getStatus() %></td>
                <td><%= res.getContactNumber() %></td>
                <td class="action-btns">
                    <a href="<%= request.getContextPath() %>/reservation?action=view&reservationNumber=<%= res.getReservationNumber() %>" class="view-btn">View</a>

                    <form action="<%= request.getContextPath() %>/reservation" method="POST" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="reservationNumber" value="<%= res.getReservationNumber() %>">
                        <button type="submit" class="delete-btn"
                                onclick="return confirm('Are you sure?')">Delete</button>
                    </form>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <% } else { %>
        <p style="text-align:center; padding:20px;">No reservations found.</p>
        <% } %>
    </div>

</div>

</body>
</html>
