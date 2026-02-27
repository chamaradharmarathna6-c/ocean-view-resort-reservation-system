package com.oceanview.servlet;

import com.oceanview.model.User;
import com.oceanview.model.Bill;
import com.oceanview.service.BillService;
import com.oceanview.util.Constants;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class BillServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BillService billService = new BillService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equals(action)) {
            listBills(request, response);
        } else if ("view".equals(action)) {
            viewBill(request, response);
        } else if ("pending".equals(action)) {
            listPendingBills(request, response);
        } else if ("search".equals(action)) {
            searchBills(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/bills.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("markPaid".equals(action)) {
            markBillAsPaid(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/bills.jsp");
        }
    }

    private void listBills(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dateRange = request.getParameter("dateRange");
        List<Bill> bills;

        if ("custom".equals(dateRange)) {
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");

            try {
                LocalDate startDate = LocalDate.parse(startDateStr);
                LocalDate endDate = LocalDate.parse(endDateStr);

                bills = billService.getAllBills();
                bills.removeIf(b -> b.getBillGeneratedDate().isBefore(startDate) || b.getBillGeneratedDate().isAfter(endDate));
            } catch (Exception e) {
                bills = billService.getAllBills();
            }
        } else {

            bills = billService.getAllBills();
        }

        request.setAttribute("bills", bills);
        request.getRequestDispatcher("/bills.jsp").forward(request, response);
    }

    private void listPendingBills(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Bill> bills = billService.getPendingBills();
        request.setAttribute("bills", bills);
        request.setAttribute("showPendingOnly", true);
        request.getRequestDispatcher("/bills.jsp").forward(request, response);
    }

    private void viewBill(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String billId = request.getParameter("billId");
        Bill bill = billService.getBillDetails(billId);

        request.setAttribute("bill", bill);
        request.getRequestDispatcher("/view-bill.jsp").forward(request, response);
    }

    private void markBillAsPaid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String billId = request.getParameter("billId");

        if (billService.markBillAsPaid(billId)) {
            request.setAttribute("successMessage", "Bill marked as paid successfully");

            Bill updatedBill = billService.getBillDetails(billId);
            request.setAttribute("bill", updatedBill);
        } else {
            request.setAttribute("errorMessage", "Failed to mark bill as paid");

            Bill bill = billService.getBillDetails(billId);
            request.setAttribute("bill", bill);
        }

        request.getRequestDispatcher("/view-bill.jsp").forward(request, response);
    }

    private void searchBills(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String guestName = request.getParameter("guestName");
        List<Bill> bills = billService.searchBillsByGuestName(guestName);

        request.setAttribute("bills", bills);
        request.setAttribute("searchQuery", guestName);
        request.getRequestDispatcher("/bills.jsp").forward(request, response);
    }
}

