package com.oceanview.dao;

import com.oceanview.model.Room;
import java.util.List;

public interface RoomDAO {
    boolean addRoom(Room room);
    List<Room> getAllRooms();
    Room getRoomByNumber(String roomNumber);
    boolean updateRoomStatus(String roomNumber, String status);
    
    // NEW: Complete room update (type, price, status)
    boolean updateRoom(String roomNumber, String type, double price, String status);
    
    // NEW: Delete room
    boolean deleteRoom(String roomNumber);
}