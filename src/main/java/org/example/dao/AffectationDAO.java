package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.example.config.DatabaseConnection;
import org.example.model.Affectation;

public class AffectationDAO {

    // 1. Créer une nouvelle demande avec dates d'entrée et de sortie
    public void createAffectation(Affectation affectation) {
        String insertSql = "INSERT INTO affectations (student_id, room_id, date_entree, date_sortie, status) VALUES (?, ?, ?, ?, 'EN_ATTENTE')";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSql)) {
            stmt.setInt(1, affectation.getStudent().getIdStudent());
            stmt.setInt(2, affectation.getRoom().getIdRoom());
            stmt.setString(3, affectation.getDateEntree());
            stmt.setString(4, affectation.getDateSortie());
            stmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    // 2. Vérifier si l'étudiant a déjà une demande active
    public boolean hasActiveReservation(int studentId) {
        String sql = "SELECT COUNT(*) FROM affectations WHERE student_id = ? AND status != 'REFUSE'";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // 3. Accepter la réservation (Admin)
    public void accepterReservation(int affectationId, int roomId) {
        String sqlAffectation = "UPDATE affectations SET status = 'ACCEPTE' WHERE id = ?";
        String sqlRoom = "UPDATE rooms SET statut_room = 'OCCUPEE' WHERE id_room = ?";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstance().getConnection();
            conn.setAutoCommit(false);
            try (PreparedStatement ps1 = conn.prepareStatement(sqlAffectation);
                 PreparedStatement ps2 = conn.prepareStatement(sqlRoom)) {
                ps1.setInt(1, affectationId);
                ps1.executeUpdate();
                ps2.setInt(1, roomId);
                ps2.executeUpdate();
            }
            conn.commit();
        } catch (Exception e) {
            if (conn != null) try { conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            e.printStackTrace();
        }
    }

    // 4. Retrouver l'ID de l'affectation par étudiant
    public int getAffectationIdByStudent(int studentId) {
        String sql = "SELECT id FROM affectations WHERE student_id = ? AND status = 'EN_ATTENTE' LIMIT 1";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (Exception e) { e.printStackTrace(); }
        return -1;
    }

    // 5. Retrouver l'ID de la chambre par étudiant
    public int getRoomIdByStudent(int studentId) {
        String sql = "SELECT room_id FROM affectations WHERE student_id = ? AND status = 'EN_ATTENTE' LIMIT 1";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("room_id");
        } catch (Exception e) { e.printStackTrace(); }
        return -1;
    }

    // 6. Méthode pour retrouver l'ID de l'étudiant via la chambre
    public Integer getLatestAffectationStudentId(int roomId) {
        String sql = "SELECT student_id FROM affectations WHERE room_id = ? ORDER BY id DESC LIMIT 1";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("student_id");
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
}