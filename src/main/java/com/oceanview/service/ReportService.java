package com.oceanview.service;

import com.oceanview.dao.BillDAO;
import com.oceanview.dao.ReservationDAO;
import com.oceanview.model.Bill;
import com.oceanview.model.Reservation;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReportService {
    private ReservationDAO reservationDAO = new ReservationDAO();
    private BillDAO billDAO = new BillDAO();


    public Map<String, Object> generateRevenueReport(LocalDate startDate, LocalDate endDate) {
        Map<String, Object> report = new HashMap<>();

        double totalRevenue = billDAO.getTotalRevenue(startDate, endDate);
        double pendingRevenue = billDAO.getTotalPendingRevenue();
        List<Bill> bills = billDAO.getBillsByDateRange(startDate, endDate);

        report.put("startDate", startDate);
        report.put("endDate", endDate);
        report.put("totalRevenue", totalRevenue);
        report.put("pendingRevenue", pendingRevenue);
        report.put("billCount", bills.size());
        report.put("bills", bills);

        return report;
    }


    public Map<String, Object> generateOccupancyReport(LocalDate reportDate) {
        Map<String, Object> report = new HashMap<>();

        List<Reservation> allReservations = reservationDAO.getAllReservations();


        Map<String, Integer> occupancyByType = new HashMap<>();
        occupancyByType.put("SINGLE", 0);
        occupancyByType.put("DOUBLE", 0);
        occupancyByType.put("SUITE", 0);
        occupancyByType.put("DELUXE", 0);

        for (Reservation res : allReservations) {
            if (!reportDate.isBefore(res.getCheckInDate()) && reportDate.isBefore(res.getCheckOutDate())) {
                String roomType = res.getRoomType();
                occupancyByType.put(roomType, occupancyByType.getOrDefault(roomType, 0) + 1);
            }
        }

        report.put("reportDate", reportDate);
        report.put("occupancyByType", occupancyByType);
        report.put("totalOccupied", occupancyByType.values().stream().mapToInt(Integer::intValue).sum());

        return report;
    }


    public List<Map<String, Object>> generateOccupancyReportDateRange(LocalDate startDate, LocalDate endDate) {
        List<Map<String, Object>> reports = new ArrayList<>();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            reports.add(generateOccupancyReport(date));
        }

        return reports;
    }


    public Map<String, Object> generateGuestReport(LocalDate startDate, LocalDate endDate) {
        Map<String, Object> report = new HashMap<>();

        List<Reservation> reservations = reservationDAO.getReservationsByDateRange(startDate, endDate);

        report.put("startDate", startDate);
        report.put("endDate", endDate);
        report.put("totalGuests", reservations.size());
        report.put("reservations", reservations);

        return report;
    }


    public Map<String, Integer> getReservationStats() {
        Map<String, Integer> stats = new HashMap<>();

        List<Reservation> allReservations = reservationDAO.getAllReservations();

        int pending = 0;
        int confirmed = 0;
        int checkedIn = 0;
        int checkedOut = 0;
        int cancelled = 0;

        for (Reservation res : allReservations) {
            switch (res.getStatus()) {
                case "PENDING":
                    pending++;
                    break;
                case "CONFIRMED":
                    confirmed++;
                    break;
                case "CHECKED_IN":
                    checkedIn++;
                    break;
                case "CHECKED_OUT":
                    checkedOut++;
                    break;
                case "CANCELLED":
                    cancelled++;
                    break;
            }
        }

        stats.put("PENDING", pending);
        stats.put("CONFIRMED", confirmed);
        stats.put("CHECKED_IN", checkedIn);
        stats.put("CHECKED_OUT", checkedOut);
        stats.put("CANCELLED", cancelled);
        stats.put("TOTAL", allReservations.size());

        return stats;
    }
}

