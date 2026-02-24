package com.oceanview.dao;

import com.oceanview.model.Room;
import com.oceanview.util.DatabaseConnectionPool;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    private DatabaseConnectionPool connectionPool =
            DatabaseConnectionPool.getInstance();


    public boolean createRoom(Room room) {

        String sql = "INSERT INTO Rooms " +
                "(roomId, roomType, roomNumber, roomRate, status, description, capacity) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection connection = connectionPool.getConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, room.getRoomId());
            pstmt.setString(2, room.getRoomType());
            pstmt.setString(3, room.getRoomNumber());
            pstmt.setDouble(4, room.getRoomRate());
            pstmt.setString(5, room.getStatus());
            pstmt.setString(6, room.getDescription());
            pstmt.setInt(7, room.getCapacity());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }


    public int getRoomCountByType(String roomType) {

        String sql = "SELECT COUNT(*) as count FROM Rooms WHERE roomType = ?";

        Connection connection = connectionPool.getConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, roomType);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("count");
            }

            return 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }


    public int getBookedRoomCountByTypeAndDateRange(String roomType,
                                                    LocalDate checkInDate,
                                                    LocalDate checkOutDate) {

        String sql = "SELECT COUNT(*) as count FROM Reservations " +
                "WHERE roomType = ? " +
                "AND status IN ('PENDING','CONFIRMED','CHECKED_IN') " +
                "AND checkInDate < ? " +
                "AND checkOutDate > ?";

        Connection connection = connectionPool.getConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, roomType);


            pstmt.setDate(2, Date.valueOf(checkOutDate));
            pstmt.setDate(3, Date.valueOf(checkInDate));

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("count");
            }

            return 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }


    public List<Room> getAllRooms() {

        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM Rooms ORDER BY roomNumber";

        Connection connection = connectionPool.getConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                rooms.add(mapRoom(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return rooms;
    }


    private Room mapRoom(ResultSet rs) throws SQLException {

        Room room = new Room();

        room.setRoomId(rs.getString("roomId"));
        room.setRoomType(rs.getString("roomType"));
        room.setRoomNumber(rs.getString("roomNumber"));
        room.setRoomRate(rs.getDouble("roomRate"));
        room.setStatus(rs.getString("status"));
        room.setDescription(rs.getString("description"));
        room.setCapacity(rs.getInt("capacity"));

        return room;
    }
}
