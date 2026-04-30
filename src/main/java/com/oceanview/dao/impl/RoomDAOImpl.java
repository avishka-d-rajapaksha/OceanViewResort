package com.oceanview.dao.impl;

import com.oceanview.dao.RoomDAO;
import com.oceanview.db.DBConnection;
import com.oceanview.model.Room;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {

    @Override
    public boolean addRoom(Room room) {
        String sql = "INSERT INTO rooms (room_id, room_type, rate_per_night, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, room.getRoomNumber());
            pst.setString(2, room.getType());
            pst.setDouble(3, room.getPrice());
            pst.setString(4, room.getStatus());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    @Override
    public List<Room> getAllRooms() {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM rooms";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Room(
                    rs.getString("room_id"),
                    rs.getString("room_type"),
                    rs.getDouble("rate_per_night"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public Room getRoomByNumber(String roomNumber) {
        String sql = "SELECT * FROM rooms WHERE room_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, roomNumber);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Room(rs.getString("room_id"), rs.getString("room_type"), rs.getDouble("rate_per_night"), rs.getString("status"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public boolean updateRoomStatus(String roomNumber, String status) {
        String sql = "UPDATE rooms SET status = ? WHERE room_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, status);
            pst.setString(2, roomNumber);
            
            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✓ Room status updated: " + roomNumber + " -> " + status);
                return true;
            }
            return false;
        } catch (SQLException e) { 
            System.err.println("Error updating room status: " + e.getMessage());
            e.printStackTrace(); 
            return false; 
        }
    }
    
    /**
     * BUG FIX: Add method for complete room update (type, price, status)
     */
    @Override
    public boolean updateRoom(String roomNumber, String type, double price, String status) {
        String sql = "UPDATE rooms SET room_type = ?, rate_per_night = ?, status = ? WHERE room_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, type);
            pst.setDouble(2, price);
            pst.setString(3, status);
            pst.setString(4, roomNumber);
            
            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✓ Room updated: " + roomNumber);
                return true;
            }
            System.out.println("✗ Room not found: " + roomNumber);
            return false;
        } catch (SQLException e) { 
            System.err.println("✗ Error updating room: " + e.getMessage());
            e.printStackTrace(); 
            return false; 
        }
    }
    
    /**
     * BUG FIX: Add method to delete room
     */
    @Override
    public boolean deleteRoom(String roomNumber) {
        String sql = "DELETE FROM rooms WHERE room_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, roomNumber);
            int affectedRows = pst.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("✓ Room deleted: " + roomNumber);
                return true;
            }
            System.out.println("✗ Room not found for deletion: " + roomNumber);
            return false;
        } catch (SQLException e) { 
            System.err.println("✗ Error deleting room: " + e.getMessage());
            e.printStackTrace(); 
            return false; 
        }
    }
}