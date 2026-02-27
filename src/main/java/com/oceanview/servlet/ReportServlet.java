package com.oceanview.servlet;

import com.oceanview.model.User;
import com.oceanview.service.ReportService;
import com.oceanview.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ReportService reportService = new ReportService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("revenue".equals(action)) {
            generateRevenueReport(request, response);
        } else if ("occupancy".equals(action)) {
            generateOccupancyReport(request, response);
        } else if ("stats".equals(action)) {
            generateStatistics(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/reports.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("revenue".equals(action)) {
            generateRevenueReport(request, response);
        } else if ("occupancy".equals(action)) {
            generateOccupancyReport(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/reports.jsp");
        }
    }

    private void generateRevenueReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        try {
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);

            // Date validation
            if (startDate.isAfter(endDate)) {
                request.setAttribute("errorMessage", "Start date cannot be after End date.");
            } else {
                Map<String, Object> report = reportService.generateRevenueReport(startDate, endDate);
                request.setAttribute("report", report);
                request.setAttribute("reportType", "revenue");
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Invalid date format.");
        }

        request.getRequestDispatcher("/reports.jsp").forward(request, response);
    }

    private void generateOccupancyReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String reportDateStr = request.getParameter("reportDate");
        String reportType = request.getParameter("reportType");

        try {
            if ("daily".equals(reportType)) {
                LocalDate reportDate = LocalDate.parse(reportDateStr);
                Map<String, Object> report = reportService.generateOccupancyReport(reportDate);
                request.setAttribute("report", report);
            } else if ("range".equals(reportType)) {
                String startDateStr = request.getParameter("startDate");
                String endDateStr = request.getParameter("endDate");
                LocalDate startDate = LocalDate.parse(startDateStr);
                LocalDate endDate = LocalDate.parse(endDateStr);

                // Date validation
                if (startDate.isAfter(endDate)) {
                    request.setAttribute("errorMessage", "Start date cannot be after End date.");
                } else {
                    List<Map<String, Object>> reports = reportService.generateOccupancyReportDateRange(startDate, endDate);
                    request.setAttribute("occupancyReports", reports);
                }
            }
            request.setAttribute("reportType", "occupancy");
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Invalid date format.");
        }

        request.getRequestDispatcher("/reports.jsp").forward(request, response);
    }

    private void generateStatistics(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Integer> stats = reportService.getReservationStats();
        request.setAttribute("stats", stats);
        request.setAttribute("reportType", "statistics");

        request.getRequestDispatcher("/reports.jsp").forward(request, response);
    }
}
