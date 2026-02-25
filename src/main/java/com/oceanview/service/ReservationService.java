package com.oceanview.service;

import com.oceanview.dao.ReservationDAO;
import com.oceanview.dao.BillDAO;
import com.oceanview.dao.RoomDAO;
import com.oceanview.model.Reservation;
import com.oceanview.model.Bill;
import com.oceanview.model.Room;
import com.oceanview.util.Constants;

import java.time.LocalDate;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {

    private ReservationDAO reservationDAO = new ReservationDAO();
    private RoomDAO roomDAO = new RoomDAO();
    private BillDAO billDAO = new BillDAO();

    public boolean createReservation(String guestName, String address, String contactNumber,
                                     String roomType, LocalDate checkInDate, LocalDate checkOutDate) {


        if (checkOutDate.isBefore(checkInDate) || checkOutDate.isEqual(checkInDate)) {
            throw new IllegalArgumentException(Constants.MSG_INVALID_DATES);
        }


        if (contactNumber == null || !contactNumber.matches("07\\d{8}")) {
            throw new IllegalArgumentException(
                    "Contact number must be a valid Sri Lankan mobile number (07XXXXXXXX).");
        }


        int totalRooms = roomDAO.getRoomCountByType(roomType);

        if (totalRooms == 0) {
            throw new IllegalArgumentException("No rooms found for selected room type.");
        }


        int bookedRooms = roomDAO.getBookedRoomCountByTypeAndDateRange(
                roomType, checkInDate, checkOutDate);


        if (bookedRooms >= totalRooms) {
            throw new IllegalArgumentException(
                    "All rooms of this type are fully booked during the selected dates."
            );
        }

        try {
            String reservationNumber = generateReservationNumber();

            Reservation reservation = new Reservation(
                    reservationNumber,
                    guestName,
                    address,
                    contactNumber,
                    roomType,
                    checkInDate,
                    checkOutDate
            );

            reservation.setStatus(Constants.RESERVATION_PENDING);

            return reservationDAO.createReservation(reservation);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create reservation.");
        }
    }

    public Reservation getReservationDetails(String reservationNumber) {
        return reservationDAO.getReservationByNumber(reservationNumber);
    }

    public boolean deleteReservation(String reservationNumber) {
        try {
            Bill bill = billDAO.getBillByReservationNumber(reservationNumber);
            if (bill != null) {
                billDAO.deleteBill(bill.getBillId());
            }
            return reservationDAO.deleteReservation(reservationNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateReservationStatus(String reservationNumber, String status) {
        Reservation reservation = reservationDAO.getReservationByNumber(reservationNumber);
        if (reservation != null) {
            reservation.setStatus(status);
            return reservationDAO.updateReservation(reservation);
        }
        return false;
    }

    public List<Reservation> getAllReservations() {
        return reservationDAO.getAllReservations();
    }

    public List<Reservation> getReservationsByDateRange(LocalDate startDate, LocalDate endDate) {
        return reservationDAO.getReservationsByDateRange(startDate, endDate);
    }

    public List<Reservation> searchReservationsByGuestName(String guestName) {
        return reservationDAO.getReservationsByGuestName(guestName);
    }

    public int getOccupancyForRoomType(String roomType, LocalDate date) {
        List<Reservation> reservations =
                reservationDAO.getReservationsByStatus(Constants.RESERVATION_CHECKED_IN);

        int count = 0;

        for (Reservation res : reservations) {
            if (res.getRoomType().equals(roomType)
                    && !date.isBefore(res.getCheckInDate())
                    && date.isBefore(res.getCheckOutDate())) {
                count++;
            }
        }
        return count;
    }

    public String generateReservationNumber() {
        return "RES-" + System.currentTimeMillis() + "-" +
                UUID.randomUUID().toString().substring(0, 8);
    }

    public List<Reservation> getReservationsWithPendingPayment() {

        List<Reservation> allReservations = reservationDAO.getAllReservations();
        List<Reservation> pendingReservations = new ArrayList<>();

        for (Reservation res : allReservations) {
            Bill bill = billDAO.getBillByReservationNumber(res.getReservationNumber());

            if (bill == null || Constants.BILL_PENDING.equals(bill.getPaymentStatus())) {
                pendingReservations.add(res);
            }
        }

        return pendingReservations;
    }
}
