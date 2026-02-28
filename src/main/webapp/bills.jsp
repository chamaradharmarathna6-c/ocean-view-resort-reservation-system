<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.oceanview.model.User" %>
<%@ page import="com.oceanview.model.Bill" %>
<%@ page import="com.oceanview.service.BillService" %>
<%@ page import="com.oceanview.util.Constants" %>
<%@ page import="java.util.List" %>

<%
    User user = (User) session.getAttribute(Constants.SESSION_USER);
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }

    BillService billService = new BillService();
    List<Bill> bills = (List<Bill>) request.getAttribute("bills");
    if (bills == null) {
        bills = billService.getAllBills();
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bills - Ocean View Resort</title>
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

        .navbar a:hover {
            background-color: rgba(255, 255, 255, 0.4);
        }

        .container {
            max-width: 1200px;
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

        .filter-section {
            background: white;
            border-radius: 10px;
            padding: 25px;
            margin-bottom: 30px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        .filter-section h3 {
            color: #008080;
            margin-bottom: 15px;
            font-size: 16px;
        }

        .search-form {
            display: flex;
            gap: 10px;
            align-items: center;
        }

        .search-form input {
            flex: 1;
            padding: 12px;
            border: 2px solid #C2B280;
            border-radius: 5px;
            font-size: 14px;
        }

        .search-form input:focus {
            outline: none;
            border-color: #008080;
            background-color: #f0f8ff;
        }

        .search-form button {
            padding: 12px 25px;
            background: linear-gradient(135deg, #008080 0%, #36454F 100%);
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            transition: transform 0.2s;
        }

        .search-form button:hover {
            transform: scale(1.05);
        }

        .clear-search {
            padding: 12px 15px;
            background-color: #C2B280;
            color: #36454F;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            text-decoration: none;
            transition: background-color 0.3s;
            display: inline-block;
        }

        .clear-search:hover {
            background-color: #bbb;
        }

        .form-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 15px;
            align-items: flex-end;
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
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            transition: transform 0.2s;
        }

        .btn:hover {
            transform: scale(1.05);
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

        .action-btns a, .action-btns button {
            padding: 6px 12px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            font-size: 12px;
            text-decoration: none;
        }

        .delete-btn {
            background-color: #FF7F50;
            color: white;
        }

        .paid-badge {
            background-color: #006600;
            color: white;
            padding: 4px 8px;
            border-radius: 3px;
            font-size: 12px;
        }

        .pending-badge {
            background-color: #ff9800;
            color: white;
            padding: 4px 8px;
            border-radius: 3px;
            font-size: 12px;
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
            <h2>Bills & Payments</h2>
        </div>

        <div class="filter-section">
            <h3>Search Bill</h3>
            <form method="GET" action="<%= request.getContextPath() %>/bill" class="search-form">
                <input type="hidden" name="action" value="search">
                <input type="text" name="guestName" placeholder="Enter guest name to search..." required>
                <button type="submit">Search</button>
                <a href="<%= request.getContextPath() %>/bills.jsp" class="clear-search">Clear</a>
            </form>
        </div>

        <div class="table-section">
            <%
                String searchQuery = (String) request.getAttribute("searchQuery");
                if (searchQuery != null && !searchQuery.isEmpty()) {
            %>
                <h3>Search Results for "<%= searchQuery %>"</h3>
            <% } else { %>
                <h3>All Bills</h3>
            <% } %>
            <% if (bills != null && !bills.isEmpty()) { %>
                <table>
                    <thead>
                        <tr>
                            <th>Bill ID</th>
                            <th>Guest Name</th>
                            <th>Reservation #</th>
                            <th>Total Cost (LKR)</th>
                            <th>Payment Status</th>
                            <th>Generated Date</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Bill bill : bills) { %>
                            <tr>
                                <td><strong><%= bill.getBillId() %></strong></td>
                                <td><%= bill.getGuestName() %></td>
                                <td><%= bill.getReservationNumber() %></td>
                                <td style="font-weight: bold; color: #008080;">Rs. <%= String.format("%.2f", bill.getTotalCost()) %></td>
                                <td>
                                    <% if ("PAID".equals(bill.getPaymentStatus())) { %>
                                        <span class="paid-badge">✓ PAID</span>
                                    <% } else { %>
                                        <span class="pending-badge">⏳ <%= bill.getPaymentStatus() %></span>
                                    <% } %>
                                </td>
                                <td><%= bill.getBillGeneratedDate() %></td>
                                <td>
                                    <div class="action-btns">
                                        <a href="<%= request.getContextPath() %>/bill?action=view&billId=<%= bill.getBillId() %>" class="view-btn">View</a>
                                    </div>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } else { %>
                <p style="text-align: center; color: #36454F; padding: 20px;">No bills found.</p>
            <% } %>
        </div>
    </div>
</body>
</html>


