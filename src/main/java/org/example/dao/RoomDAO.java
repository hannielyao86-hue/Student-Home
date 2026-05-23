package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.example.config.DatabaseConnection;
import org.example.model.Room;

public class RoomDAO {

    private final Connection cnx = DatabaseConnection.getInstance().getConnection();

    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms ORDER BY numero_room";

        try (PreparedStatement ps = cnx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                rooms.add(new Room(
                        rs.getInt("id_room"),
                        rs.getString("numero_room"),
                        rs.getString("type_room"),
                        rs.getInt("capaciter_room"),
                        rs.getString("statut_room"),
                        rs.getDouble("loyer")
                ));
            }

        } catch (Exception e) {
            System.err.println("RoomDAO getAllRooms error: " + e.getMessage());
        }

        if (rooms.isEmpty()) {
            seedDefaultRooms();
            return getAllRooms();
        }

        return rooms;
    }

    public boolean createRoom(Room room) {
        String sql = "INSERT INTO rooms " +
                "(id_room, numero_room, type_room, capaciter_room, statut_room, loyer, id_residence, id_incident) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            int idRoom = room.getIdRoom() > 0 ? room.getIdRoom() : getNextRoomId();
            ps.setInt(1, idRoom);
            ps.setString(2, room.getNumeroRoom());
            ps.setString(3, room.getTypeRoom());
            ps.setInt(4, room.getCapaciterRoom());
            ps.setString(5, room.getStatutRoom());
            ps.setDouble(6, room.getLoyer());
            ps.setInt(7, 1);
            ps.setString(8, "NONE");
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.err.println("RoomDAO createRoom error: " + e.getMessage());
        }
        return false;
    }

    public boolean updateRoom(Room room) {
        String sql = "UPDATE rooms SET loyer = ?, statut_room = ? WHERE id_room = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setDouble(1, room.getLoyer());
            ps.setString(2, room.getStatutRoom());
            ps.setInt(3, room.getIdRoom());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.err.println("RoomDAO updateRoom error: " + e.getMessage());
        }
        return false;
    }

    private int getNextRoomId() {
        String sql = "SELECT COALESCE(MAX(id_room), 0) + 1 AS next_id FROM rooms";
        try (PreparedStatement ps = cnx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("next_id");
            }
        } catch (Exception e) {
            System.err.println("RoomDAO getNextRoomId error: " + e.getMessage());
        }
        return 1;
    }

    private void seedDefaultRooms() {
        String sql = "INSERT INTO rooms " +
                "(id_room, numero_room, type_room, capaciter_room, statut_room, loyer, id_residence, id_incident) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        List<Room> defaultRooms = List.of(
                new Room(1, "A101", "SIMPLE", 1, "OCCUPEE", 3000),
                new Room(2, "A102", "SIMPLE", 1, "LIBRE", 2500),
                new Room(3, "A103", "DOUBLE", 2, "RESERVE", 2900),
                new Room(4, "A104", "SIMPLE", 1, "LIBRE", 2400),
                new Room(5, "A105", "DOUBLE", 2, "OCCUPEE", 3200),
                new Room(6, "B101", "SIMPLE", 1, "LIBRE", 2600),
                new Room(7, "B102", "SIMPLE", 1, "OCCUPEE", 2800),
                new Room(8, "B103", "DOUBLE", 2, "RESERVE", 2900),
                new Room(9, "B104", "SIMPLE", 1, "LIBRE", 2500),
                new Room(10, "B105", "DOUBLE", 2, "OCCUPEE", 3100),
                new Room(11, "C101", "DOUBLE", 2, "RESERVE", 2700),
                new Room(12, "C102", "SIMPLE", 1, "LIBRE", 2400),
                new Room(13, "C103", "DOUBLE", 2, "OCCUPEE", 3300),
                new Room(14, "C104", "SIMPLE", 1, "LIBRE", 2500),
                new Room(15, "C105", "DOUBLE", 2, "RESERVE", 2950),
                new Room(16, "D101", "DOUBLE", 2, "OCCUPEE", 3400),
                new Room(17, "D102", "SIMPLE", 1, "LIBRE", 2600),
                new Room(18, "D103", "DOUBLE", 2, "RESERVE", 2850),
                new Room(19, "D104", "DOUBLE", 2, "OCCUPEE", 3050),
                new Room(20, "D105", "SIMPLE", 1, "LIBRE", 2500)
        );

        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            for (Room room : defaultRooms) {
                ps.setInt(1, room.getIdRoom());
                ps.setString(2, room.getNumeroRoom());
                ps.setString(3, room.getTypeRoom());
                ps.setInt(4, room.getCapaciterRoom());
                ps.setString(5, room.getStatutRoom());
                ps.setDouble(6, room.getLoyer());
                ps.setInt(7, 1);
                ps.setString(8, "NONE");
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (Exception e) {
            System.err.println("RoomDAO seedDefaultRooms error: " + e.getMessage());
        }
    }
}
