package com.oceanview.servlet;

import com.oceanview.model.User;
import com.oceanview.model.Reservation;
import com.oceanview.model.Bill;
import com.oceanview.service.ReservationService;
import com.oceanview.service.BillService;
import com.oceanview.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ReservationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ReservationService reservationService = new ReservationService();
    private BillService billService = new BillService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addReservation(request, response);
        } else if ("delete".equals(action)) {
            deleteReservation(request, response);
        } else if ("calculateBill".equals(action)) {
            calculateBill(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/reservations.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("view".equals(action)) {
            viewReservation(request, response);
        } else if ("list".equals(action)) {
            listReservations(request, response);
        } else if ("search".equals(action)) {
            searchReservations(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/reservations.jsp");
        }
    }

    private void addReservation(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String guestName = request.getParameter(Constants.PARAM_GUEST_NAME);
        String address = request.getParameter(Constants.PARAM_ADDRESS);
        String contactNumber = request.getParameter(Constants.PARAM_CONTACT);
        String roomType = request.getParameter(Constants.PARAM_ROOM_TYPE);
        String checkInStr = request.getParameter(Constants.PARAM_CHECKIN);
        String checkOutStr = request.getParameter(Constants.PARAM_CHECKOUT);

        try {

            LocalDate checkInDate = LocalDate.parse(checkInStr);
            LocalDate checkOutDate = LocalDate.parse(checkOutStr);

            boolean created = reservationService.createReservation(
                    guestName,
                    address,
                    contactNumber,
                    roomType,
                    checkInDate,
                    checkOutDate
            );

            if (created) {
                request.setAttribute("successMessage", Constants.MSG_RESERVATION_SUCCESS);
            } else {
                request.setAttribute("errorMessage", "Failed to create reservation.");
            }

        } catch (IllegalArgumentException e) {


            request.setAttribute("errorMessage", e.getMessage());

        } catch (Exception e) {

            request.setAttribute("errorMessage", "Something went wrong. Please try again.");
            e.printStackTrace();
        }

        request.getRequestDispatcher("/reservations.jsp").forward(request, response);
    }

    private void viewReservation(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String reservationNumber = request.getParameter(Constants.PARAM_RESERVATION_NUMBER);

        Reservation reservation = reservationService.getReservationDetails(reservationNumber);
        Bill bill = billService.getBillByReservationNumber(reservationNumber);

        request.setAttribute("reservation", reservation);
        request.setAttribute("bill", bill);

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        request.getRequestDispatcher("/view-reservation.jsp").forward(request, response);
    }

    private void listReservations(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Reservation> reservations = reservationService.getAllReservations();
        request.setAttribute("reservations", reservations);

        request.getRequestDispatcher("/reservations.jsp").forward(request, response);
    }

    private void deleteReservation(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String reservationNumber = request.getParameter(Constants.PARAM_RESERVATION_NUMBER);

        try {

            boolean deleted = reservationService.deleteReservation(reservationNumber);

            if (deleted) {
                request.setAttribute("successMessage",
                        Constants.MSG_RESERVATION_DELETE_SUCCESS);
            } else {
                request.setAttribute("errorMessage",
                        "Failed to delete reservation.");
            }

        } catch (Exception e) {
            request.setAttribute("errorMessage",
                    "Error deleting reservation.");
            e.printStackTrace();
        }

        listReservations(request, response);
    }

    private void searchReservations(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String guestName = request.getParameter("guestName");

        List<Reservation> reservations =
                reservationService.searchReservationsByGuestName(guestName);

        request.setAttribute("reservations", reservations);
        request.setAttribute("searchQuery", guestName);

        request.getRequestDispatcher("/reservations.jsp").forward(request, response);
    }

    private void calculateBill(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String reservationNumber =
                request.getParameter(Constants.PARAM_RESERVATION_NUMBER);

        try {

            Bill bill = billService.calculateBill(reservationNumber);

            if (bill != null) {

                if (billService.saveBill(bill)) {
                    request.setAttribute("successMessage",
                            Constants.MSG_BILL_CALCULATED);
                } else {
                    request.setAttribute("errorMessage",
                            "Bill already exists for this reservation.");
                }

            } else {
                request.setAttribute("errorMessage",
                        "Failed to calculate bill.");
            }

        } catch (Exception e) {

            request.setAttribute("errorMessage",
                    "Error calculating bill.");
            e.printStackTrace();
        }

        viewReservation(request, response);
    }
}
