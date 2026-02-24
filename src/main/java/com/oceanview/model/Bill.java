package com.oceanview.model;

import java.io.Serializable;
import java.time.LocalDate;


public class Bill implements Serializable {
    private static final long serialVersionUID = 1L;

    private String billId;
    private String reservationNumber;
    private String guestName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private long numberOfNights;
    private double roomRate;
    private double totalCost;
    private String paymentStatus;
    private LocalDate billGeneratedDate;
    private LocalDate paymentDate;

    public Bill() {
    }

    public Bill(String billId, String reservationNumber, String guestName,
                LocalDate checkInDate, LocalDate checkOutDate, long numberOfNights,
                double roomRate) {
        this.billId = billId;
        this.reservationNumber = reservationNumber;
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfNights = numberOfNights;
        this.roomRate = roomRate;
        this.totalCost = numberOfNights * roomRate;
        this.paymentStatus = "PENDING";
        this.billGeneratedDate = LocalDate.now();
    }


    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public long getNumberOfNights() {
        return numberOfNights;
    }

    public void setNumberOfNights(long numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public double getRoomRate() {
        return roomRate;
    }

    public void setRoomRate(double roomRate) {
        this.roomRate = roomRate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDate getBillGeneratedDate() {
        return billGeneratedDate;
    }

    public void setBillGeneratedDate(LocalDate billGeneratedDate) {
        this.billGeneratedDate = billGeneratedDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "billId='" + billId + '\'' +
                ", reservationNumber='" + reservationNumber + '\'' +
                ", guestName='" + guestName + '\'' +
                ", numberOfNights=" + numberOfNights +
                ", totalCost=" + totalCost +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}

