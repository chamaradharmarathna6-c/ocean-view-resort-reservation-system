package com.oceanview.dao;

import com.oceanview.model.Bill;
import com.oceanview.util.DatabaseConnectionPool;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class BillDAO {
    private DatabaseConnectionPool connectionPool = DatabaseConnectionPool.getInstance();


    public boolean createBill(Bill bill) {
        String sql = "INSERT INTO Bills (billId, reservationNumber, guestName, checkInDate, checkOutDate, " +
                     "numberOfNights, roomRate, totalCost, paymentStatus, billGeneratedDate, paymentDate) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, bill.getBillId());
            pstmt.setString(2, bill.getReservationNumber());
            pstmt.setString(3, bill.getGuestName());
            pstmt.setDate(4, Date.valueOf(bill.getCheckInDate()));
            pstmt.setDate(5, Date.valueOf(bill.getCheckOutDate()));
            pstmt.setLong(6, bill.getNumberOfNights());
            pstmt.setDouble(7, bill.getRoomRate());
            pstmt.setDouble(8, bill.getTotalCost());
            pstmt.setString(9, bill.getPaymentStatus());
            pstmt.setDate(10, Date.valueOf(bill.getBillGeneratedDate()));
            pstmt.setDate(11, bill.getPaymentDate() != null ? Date.valueOf(bill.getPaymentDate()) : null);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error creating bill: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }


    public Bill getBillById(String billId) {
        String sql = "SELECT * FROM Bills WHERE billId = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, billId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapBill(rs);
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Error getting bill: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }


    public Bill getBillByReservationNumber(String reservationNumber) {
        String sql = "SELECT * FROM Bills WHERE reservationNumber = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, reservationNumber);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapBill(rs);
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Error getting bill by reservation: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }


    public List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM Bills ORDER BY billGeneratedDate DESC";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bills.add(mapBill(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all bills: " + e.getMessage());
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return bills;
    }


    public List<Bill> getBillsByPaymentStatus(String paymentStatus) {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM Bills WHERE paymentStatus = ? ORDER BY billGeneratedDate DESC";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, paymentStatus);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bills.add(mapBill(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting bills by status: " + e.getMessage());
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return bills;
    }


    public List<Bill> getBillsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM Bills WHERE billGeneratedDate >= ? AND billGeneratedDate <= ? ORDER BY billGeneratedDate DESC";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(startDate));
            pstmt.setDate(2, Date.valueOf(endDate));

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bills.add(mapBill(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting bills by date range: " + e.getMessage());
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return bills;
    }


    public boolean updateBill(Bill bill) {
        String sql = "UPDATE Bills SET guestName = ?, paymentStatus = ?, paymentDate = ? WHERE billId = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, bill.getGuestName());
            pstmt.setString(2, bill.getPaymentStatus());
            pstmt.setDate(3, bill.getPaymentDate() != null ? Date.valueOf(bill.getPaymentDate()) : null);
            pstmt.setString(4, bill.getBillId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating bill: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }


    public boolean deleteBill(String billId) {
        String sql = "DELETE FROM Bills WHERE billId = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, billId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting bill: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }


    public double getTotalRevenue(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT SUM(totalCost) as totalRevenue FROM Bills WHERE billGeneratedDate >= ? AND billGeneratedDate <= ? AND paymentStatus = 'PAID'";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(startDate));
            pstmt.setDate(2, Date.valueOf(endDate));

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                double revenue = rs.getDouble("totalRevenue");
                return revenue > 0 ? revenue : 0.0;
            }
            return 0.0;
        } catch (SQLException e) {
            System.err.println("Error getting total revenue: " + e.getMessage());
            e.printStackTrace();
            return 0.0;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }


    public double getTotalPendingRevenue() {
        String sql = "SELECT SUM(totalCost) as totalRevenue FROM Bills WHERE paymentStatus IN ('PENDING', 'PARTIALLY_PAID')";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                double revenue = rs.getDouble("totalRevenue");
                return revenue > 0 ? revenue : 0.0;
            }
            return 0.0;
        } catch (SQLException e) {
            System.err.println("Error getting pending revenue: " + e.getMessage());
            e.printStackTrace();
            return 0.0;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }


    private Bill mapBill(ResultSet rs) throws SQLException {
        Bill bill = new Bill();
        bill.setBillId(rs.getString("billId"));
        bill.setReservationNumber(rs.getString("reservationNumber"));
        bill.setGuestName(rs.getString("guestName"));
        bill.setCheckInDate(rs.getDate("checkInDate").toLocalDate());
        bill.setCheckOutDate(rs.getDate("checkOutDate").toLocalDate());
        bill.setNumberOfNights(rs.getLong("numberOfNights"));
        bill.setRoomRate(rs.getDouble("roomRate"));
        bill.setTotalCost(rs.getDouble("totalCost"));
        bill.setPaymentStatus(rs.getString("paymentStatus"));
        bill.setBillGeneratedDate(rs.getDate("billGeneratedDate").toLocalDate());

        Date paymentDate = rs.getDate("paymentDate");
        if (paymentDate != null) {
            bill.setPaymentDate(paymentDate.toLocalDate());
        }

        return bill;
    }
}

