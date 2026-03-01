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
    <title>Reports - Ocean View Resort</title>
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

        .header h2 {
            color: #008080;
            font-size: 28px;
            margin-bottom: 30px;
        }

        .report-tabs {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 15px;
            margin-bottom: 30px;
        }

        .tab-btn {
            background: white;
            border: 2px solid #C2B280;
            padding: 15px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: all 0.3s;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        .tab-btn:hover {
            border-color: #008080;
            color: #008080;
            transform: translateY(-2px);
        }

        .tab-btn.active {
            background: linear-gradient(135deg, #008080 0%, #36454F 100%);
            color: white;
            border-color: #008080;
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

        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin: 20px 0;
        }

        .stat-card {
            background: linear-gradient(135deg, #008080 0%, #36454F 100%);
            color: white;
            padding: 20px;
            border-radius: 5px;
            text-align: center;
        }

        .stat-value {
            font-size: 28px;
            font-weight: bold;
            margin: 10px 0;
        }

        .stat-label {
            font-size: 12px;
            opacity: 0.9;
        }

        .table-section {
            margin-top: 30px;
            overflow-x: auto;
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

        .hidden {
            display: none;
        }

        .visible {
            display: block;
        }
    </style>

    <script>
        function toggleOccupancyDateFields() {
            const reportType = document.getElementById("reportType").value;
            const dailyDateField = document.getElementById("dailyDateField");
            const rangeStartDateField = document.getElementById("rangeStartDateField");
            const rangeEndDateField = document.getElementById("rangeEndDateField");
            const reportDateInput = document.getElementById("reportDate");
            const startDateInput = document.getElementById("startDate");
            const endDateInput = document.getElementById("endDate");

            if (reportType === "daily") {
                dailyDateField.classList.remove("hidden");
                rangeStartDateField.classList.add("hidden");
                rangeEndDateField.classList.add("hidden");
                reportDateInput.setAttribute("required", "required");
                startDateInput.removeAttribute("required");
                endDateInput.removeAttribute("required");
            } else if (reportType === "range") {
                dailyDateField.classList.add("hidden");
                rangeStartDateField.classList.remove("hidden");
                rangeEndDateField.classList.remove("hidden");
                reportDateInput.removeAttribute("required");
                startDateInput.setAttribute("required", "required");
                endDateInput.setAttribute("required", "required");
            }
        }
    </script>
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
        <h2>Reports & Analytics</h2>
    </div>

    <!-- Display error message -->
    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
    %>
    <div class="error-message"><%= errorMessage %></div>
    <% } %>

    <!-- Revenue Report -->
    <div class="card">
        <h3>Revenue Report</h3>
        <form action="<%= request.getContextPath() %>/report" method="POST">
            <input type="hidden" name="action" value="revenue">
            <div class="form-grid">
                <div class="form-group">
                    <label>Start Date</label>
                    <input type="date" name="startDate" required>
                </div>
                <div class="form-group">
                    <label>End Date</label>
                    <input type="date" name="endDate" required>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn">Generate Report</button>
                </div>
            </div>
        </form>

        <%
            java.util.Map<String, Object> report = (java.util.Map<String, Object>) request.getAttribute("report");
            String reportType = (String) request.getAttribute("reportType");
            if ("revenue".equals(reportType) && report != null) {
        %>
        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-label">Total Revenue</div>
                <div class="stat-value">Rs. <%= String.format("%.2f", report.get("totalRevenue")) %></div>
            </div>
            <div class="stat-card">
                <div class="stat-label">Pending Revenue</div>
                <div class="stat-value">Rs. <%= String.format("%.2f", report.get("pendingRevenue")) %></div>
            </div>
            <div class="stat-card">
                <div class="stat-label">Bills Processed</div>
                <div class="stat-value"><%= report.get("billCount") %></div>
            </div>
        </div>
        <% } %>
    </div>

    <!-- Room Occupancy Report -->
    <div class="card">
        <h3>Room Occupancy Report</h3>
        <form action="<%= request.getContextPath() %>/report" method="POST">
            <input type="hidden" name="action" value="occupancy">
            <div class="form-grid">
                <div class="form-group">
                    <label>Report Type</label>
                    <select name="reportType" id="reportType" required onchange="toggleOccupancyDateFields()">
                        <option value="daily">Daily Report</option>
                        <option value="range">Date Range</option>
                    </select>
                </div>
                <div class="form-group" id="dailyDateField">
                    <label>Report Date</label>
                    <input type="date" name="reportDate" id="reportDate" required>
                </div>
                <div class="form-group hidden" id="rangeStartDateField">
                    <label>Start Date</label>
                    <input type="date" name="startDate" id="startDate">
                </div>
                <div class="form-group hidden" id="rangeEndDateField">
                    <label>End Date</label>
                    <input type="date" name="endDate" id="endDate">
                </div>
                <div class="form-group">
                    <button type="submit" class="btn">Generate Report</button>
                </div>
            </div>
        </form>

        <%
            if ("occupancy".equals(reportType)) {
                java.util.Map<String, Object> dailyReport = (java.util.Map<String, Object>) request.getAttribute("report");
                java.util.List<java.util.Map<String, Object>> occupancyReports = (java.util.List<java.util.Map<String, Object>>) request.getAttribute("occupancyReports");

                if (dailyReport != null) {
                    java.util.Map<String, Integer> occupancy = (java.util.Map<String, Integer>) dailyReport.get("occupancyByType");
        %>
        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-label">Single Rooms</div>
                <div class="stat-value"><%= occupancy.get("SINGLE") %></div>
            </div>
            <div class="stat-card">
                <div class="stat-label">Double Rooms</div>
                <div class="stat-value"><%= occupancy.get("DOUBLE") %></div>
            </div>
            <div class="stat-card">
                <div class="stat-label">Suites</div>
                <div class="stat-value"><%= occupancy.get("SUITE") %></div>
            </div>
            <div class="stat-card">
                <div class="stat-label">Deluxe Rooms</div>
                <div class="stat-value"><%= occupancy.get("DELUXE") %></div>
            </div>
        </div>
        <%
        } else if (occupancyReports != null && !occupancyReports.isEmpty()) {
        %>
        <div class="table-section">
            <table>
                <thead>
                <tr>
                    <th>Single Rooms</th>
                    <th>Double Rooms</th>
                    <th>Suites</th>
                    <th>Deluxe Rooms</th>
                </tr>
                </thead>
                <tbody>
                <%
                    for (java.util.Map<String, Object> occupancyDay : occupancyReports) {
                        java.util.Map<String, Integer> dayOccupancy = (java.util.Map<String, Integer>) occupancyDay.get("occupancyByType");
                %>
                <tr>
                    <td><%= dayOccupancy.get("SINGLE") %></td>
                    <td><%= dayOccupancy.get("DOUBLE") %></td>
                    <td><%= dayOccupancy.get("SUITE") %></td>
                    <td><%= dayOccupancy.get("DELUXE") %></td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
        <%
                }
            }
        %>
    </div>
</div>
</body>
</html>
