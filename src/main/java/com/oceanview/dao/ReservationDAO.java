package com.oceanview.dao;

import com.oceanview.model.Reservation;
import com.oceanview.util.DatabaseConnectionPool;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ReservationDAO {
    private DatabaseConnectionPool connectionPool = DatabaseConnectionPool.getInstance();


    public boolean createReservation(Reservation reservation) {
        String sql = "INSERT INTO Reservations (reservationNumber, guestName, address, contactNumber, " +
                     "roomType, checkInDate, checkOutDate, status, roomId, createdDate, lastModified) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, reservation.getReservationNumber());
            pstmt.setString(2, reservation.getGuestName());
            pstmt.setString(3, reservation.getAddress());
            pstmt.setString(4, reservation.getContactNumber());
            pstmt.setString(5, reservation.getRoomType());
            pstmt.setDate(6, Date.valueOf(reservation.getCheckInDate()));
            pstmt.setDate(7, Date.valueOf(reservation.getCheckOutDate()));
            pstmt.setString(8, reservation.getStatus());
            pstmt.setString(9, reservation.getRoomId());
            pstmt.setLong(10, reservation.getCreatedDate());
            pstmt.setLong(11, reservation.getLastModified());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error creating reservation: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }


    public Reservation getReservationByNumber(String reservationNumber) {
        String sql = "SELECT * FROM Reservations WHERE reservationNumber = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, reservationNumber);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapReservation(rs);
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Error getting reservation: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }


    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM Reservations ORDER BY createdDate DESC";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                reservations.add(mapReservation(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all reservations: " + e.getMessage());
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return reservations;
    }


    public List<Reservation> getReservationsByGuestName(String guestName) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM Reservations WHERE guestName LIKE ? ORDER BY createdDate DESC";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + guestName + "%");

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                reservations.add(mapReservation(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting reservations by guest name: " + e.getMessage());
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return reservations;
    }


    public List<Reservation> getReservationsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM Reservations WHERE checkInDate >= ? AND checkOutDate <= ? ORDER BY checkInDate";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(startDate));
            pstmt.setDate(2, Date.valueOf(endDate));

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                reservations.add(mapReservation(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting reservations by date range: " + e.getMessage());
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return reservations;
    }


    public List<Reservation> getReservationsByStatus(String status) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM Reservations WHERE status = ? ORDER BY createdDate DESC";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, status);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                reservations.add(mapReservation(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting reservations by status: " + e.getMessage());
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return reservations;
    }


    public boolean updateReservation(Reservation reservation) {
        String sql = "UPDATE Reservations SET guestName = ?, address = ?, contactNumber = ?, " +
                     "roomType = ?, checkInDate = ?, checkOutDate = ?, status = ?, lastModified = ? WHERE reservationNumber = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, reservation.getGuestName());
            pstmt.setString(2, reservation.getAddress());
            pstmt.setString(3, reservation.getContactNumber());
            pstmt.setString(4, reservation.getRoomType());
            pstmt.setDate(5, Date.valueOf(reservation.getCheckInDate()));
            pstmt.setDate(6, Date.valueOf(reservation.getCheckOutDate()));
            pstmt.setString(7, reservation.getStatus());
            pstmt.setLong(8, System.currentTimeMillis());
            pstmt.setString(9, reservation.getReservationNumber());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating reservation: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }


    public boolean deleteReservation(String reservationNumber) {
        String sql = "DELETE FROM Reservations WHERE reservationNumber = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, reservationNumber);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting reservation: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }


    public boolean reservationExists(String reservationNumber) {
        String sql = "SELECT COUNT(*) as count FROM Reservations WHERE reservationNumber = ?";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, reservationNumber);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error checking reservation existence: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }


    public int getReservationCountByRoomType(String roomType) {
        String sql = "SELECT COUNT(*) as count FROM Reservations WHERE roomType = ? AND status IN ('PENDING', 'CONFIRMED', 'CHECKED_IN')";

        Connection connection = connectionPool.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, roomType);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
            return 0;
        } catch (SQLException e) {
            System.err.println("Error getting reservation count: " + e.getMessage());
            e.printStackTrace();
            return 0;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }


    private Reservation mapReservation(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setReservationNumber(rs.getString("reservationNumber"));
        reservation.setGuestName(rs.getString("guestName"));
        reservation.setAddress(rs.getString("address"));
        reservation.setContactNumber(rs.getString("contactNumber"));
        reservation.setRoomType(rs.getString("roomType"));
        reservation.setCheckInDate(rs.getDate("checkInDate").toLocalDate());
        reservation.setCheckOutDate(rs.getDate("checkOutDate").toLocalDate());
        reservation.setStatus(rs.getString("status"));
        reservation.setRoomId(rs.getString("roomId"));
        reservation.setCreatedDate(rs.getLong("createdDate"));
        reservation.setLastModified(rs.getLong("lastModified"));
        return reservation;
    }
}

