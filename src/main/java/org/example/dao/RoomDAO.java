package org.example.dao;

import org.example.model.Room;
import org.example.config.DatabaseConnection; // Import important
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    // 1. Voir toutes les chambres (Admin)
    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                rooms.add(new Room(rs.getInt("id_room"), rs.getString("numero_room"), rs.getString("type_room"), rs.getInt("capaciter_room"), rs.getString("statut_room"), rs.getDouble("loyer")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return rooms;
    }

    // 2. Voir uniquement les chambres vraiment LIBRES (Étudiants)
    public List<Room> getAvailableRooms() {
        List<Room> rooms = new ArrayList<>();
        // Désormais, seules les 'LIBRE' apparaissent.
        // Les 'RESERVEE' et 'OCCUPEE' sont automatiquement masquées.
        String sql = "SELECT * FROM rooms WHERE statut_room = 'LIBRE'";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                rooms.add(new Room(rs.getInt("id_room"), rs.getString("numero_room"), rs.getString("type_room"), rs.getInt("capaciter_room"), rs.getString("statut_room"), rs.getDouble("loyer")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return rooms;
    }

    public boolean createRoom(Room room) {
        String sql = "INSERT INTO rooms (numero_room, type_room, capaciter_room, statut_room, loyer, id_residence, id_incident) VALUES (?, ?, ?, ?, ?, 1, 'NONE')";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, room.getNumeroRoom());
            stmt.setString(2, room.getTypeRoom());
            stmt.setInt(3, room.getCapaciterRoom());
            stmt.setString(4, room.getStatutRoom());
            stmt.setDouble(5, room.getLoyer());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean updateRoom(Room room) {
        String sql = "UPDATE rooms SET numero_room = ?, type_room = ?, capaciter_room = ?, statut_room = ?, loyer = ? WHERE id_room = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, room.getNumeroRoom());
            stmt.setString(2, room.getTypeRoom());
            stmt.setInt(3, room.getCapaciterRoom());
            stmt.setString(4, room.getStatutRoom());
            stmt.setDouble(5, room.getLoyer());
            stmt.setInt(6, room.getIdRoom());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}