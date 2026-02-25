package com.oceanview.service;

import com.oceanview.dao.BillDAO;
import com.oceanview.dao.ReservationDAO;
import com.oceanview.dao.RoomDAO;
import com.oceanview.model.Bill;
import com.oceanview.model.Reservation;
import com.oceanview.util.Constants;
import java.time.LocalDate;
import java.util.UUID;


public class BillService {
    private BillDAO billDAO = new BillDAO();
    private ReservationDAO reservationDAO = new ReservationDAO();
    private RoomDAO roomDAO = new RoomDAO();


    public Bill calculateBill(String reservationNumber) {
        Reservation reservation = reservationDAO.getReservationByNumber(reservationNumber);
        if (reservation == null) {
            System.err.println("Reservation not found");
            return null;
        }


        double roomRate = getRoomRateByType(reservation.getRoomType());
        long numberOfNights = reservation.getNumberOfNights();

        if (numberOfNights <= 0) {
            System.err.println("Invalid number of nights");
            return null;
        }

        String billId = "BILL-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8);

        Bill bill = new Bill(billId, reservationNumber, reservation.getGuestName(),
                            reservation.getCheckInDate(), reservation.getCheckOutDate(),
                            numberOfNights, roomRate);

        return bill;
    }


    public boolean saveBill(Bill bill) {

        Bill existingBill = billDAO.getBillByReservationNumber(bill.getReservationNumber());
        if (existingBill != null) {
            System.out.println("Bill already exists for this reservation");
            return false;
        }

        return billDAO.createBill(bill);
    }


    public Bill getBillByReservationNumber(String reservationNumber) {
        return billDAO.getBillByReservationNumber(reservationNumber);
    }


    public Bill getBillDetails(String billId) {
        return billDAO.getBillById(billId);
    }


    public boolean markBillAsPaid(String billId) {
        Bill bill = billDAO.getBillById(billId);
        if (bill != null) {
            bill.setPaymentStatus(Constants.BILL_PAID);
            bill.setPaymentDate(LocalDate.now());
            return billDAO.updateBill(bill);
        }
        return false;
    }


    private double getRoomRateByType(String roomType) {
        switch (roomType) {
            case Constants.ROOM_TYPE_SINGLE:
                return Constants.RATE_SINGLE;
            case Constants.ROOM_TYPE_DOUBLE:
                return Constants.RATE_DOUBLE;
            case Constants.ROOM_TYPE_SUITE:
                return Constants.RATE_SUITE;
            case Constants.ROOM_TYPE_DELUXE:
                return Constants.RATE_DELUXE;
            default:
                return Constants.RATE_SINGLE;
        }
    }


    public java.util.List<Bill> getAllBills() {
        return billDAO.getAllBills();
    }


    public java.util.List<Bill> getPendingBills() {
        return billDAO.getBillsByPaymentStatus(Constants.BILL_PENDING);
    }


    public java.util.List<Bill> getPaidBills() {
        return billDAO.getBillsByPaymentStatus(Constants.BILL_PAID);
    }


    public double getTotalRevenue(LocalDate startDate, LocalDate endDate) {
        return billDAO.getTotalRevenue(startDate, endDate);
    }


    public double getTotalPendingRevenue() {
        return billDAO.getTotalPendingRevenue();
    }


    public java.util.List<Bill> searchBillsByGuestName(String guestName) {
        java.util.List<Bill> allBills = billDAO.getAllBills();
        java.util.List<Bill> searchResults = new java.util.ArrayList<>();

        for (Bill bill : allBills) {
            if (bill.getGuestName().toLowerCase().contains(guestName.toLowerCase())) {
                searchResults.add(bill);
            }
        }

        return searchResults;
    }
}

