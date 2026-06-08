package org.example.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.example.config.DatabaseConnection;
import org.example.model.Affectation;
import org.example.model.Student;
import org.example.model.Room;

public class AffectationDAO {

    // 1. Créer une nouvelle demande
    public void createAffectation(Affectation affectation) {
        String insertSql = "INSERT INTO affectations (id_student, room_id, date_entree, date_sortie, status) VALUES (?, ?, ?, ?, 'EN_ATTENTE')";
        String updateRoom = "UPDATE rooms SET statut_room = 'RESERVEE' WHERE id_room = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps1 = conn.prepareStatement(insertSql);
                 PreparedStatement ps2 = conn.prepareStatement(updateRoom)) {
                ps1.setInt(1, affectation.getStudent().getIdStudent());
                ps1.setInt(2, affectation.getRoom().getIdRoom());
                ps1.setString(3, affectation.getDateEntree());
                ps1.setString(4, affectation.getDateSortie());
                ps1.executeUpdate();
                ps2.setInt(1, affectation.getRoom().getIdRoom());
                ps2.executeUpdate();
                conn.commit();
            } catch (Exception e) { conn.rollback(); throw e; }
        } catch (Exception e) { e.printStackTrace(); }
    }

    // 2. Vérifier si l'étudiant a une demande active
    public boolean hasActiveReservation(int studentId) {
        String sql = "SELECT COUNT(*) FROM affectations WHERE id_student = ? AND status != 'REFUSE'";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // 3. Récupérer toutes les demandes par statut
    public List<Affectation> getAllAffectationsByStatus(String status) {
        List<Affectation> list = new ArrayList<>();
        // Assurez-vous que les alias SQL correspondent exactement à rs.getString("...")
        String sql = "SELECT a.id, a.date_entree, a.date_sortie, u.nom_users, u.prenom_user, r.id_room, r.numero_room " +
                "FROM affectations a " +
                "JOIN students s ON a.id_student = s.id_student " +
                "JOIN users u ON s.id_users = u.id_users " +
                "JOIN rooms r ON a.room_id = r.id_room " +
                "WHERE a.status = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("DEBUG : Étudiant trouvé dans DB -> " + rs.getString("nom_users"));
                Student s = new Student();
                // Ces noms DOIVENT correspondre aux alias du SQL (nom_users, prenom_user)
                s.setNom(rs.getString("nom_users"));
                s.setPrenom(rs.getString("prenom_user"));

                Room r = new Room();
                r.setIdRoom(rs.getInt("id_room"));
                r.setNumeroRoom(rs.getString("numero_room"));

                Affectation aff = new Affectation(
                        rs.getInt("id"), s, r,
                        rs.getString("date_entree"),
                        rs.getString("date_sortie")
                );
                list.add(aff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 4. Accepter une réservation
    public void accepterReservation(int affectationId, int roomId) {
        String sqlAffectation = "UPDATE affectations SET status = 'OCCUPEE' WHERE id = ?";
        String sqlRoom = "UPDATE rooms SET statut_room = 'OCCUPEE' WHERE id_room = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps1 = conn.prepareStatement(sqlAffectation);
                 PreparedStatement ps2 = conn.prepareStatement(sqlRoom)) {
                ps1.setInt(1, affectationId); ps1.executeUpdate();
                ps2.setInt(1, roomId); ps2.executeUpdate();
                conn.commit();
            } catch (Exception e) { conn.rollback(); throw e; }
        } catch (Exception e) { e.printStackTrace(); }
    }

    // 5. Utilitaires
    public Integer getLatestAffectationStudentId(int roomId) {
        String sql = "SELECT id_student FROM affectations WHERE room_id = ? ORDER BY id DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id_student");
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public int getAffectationIdByStudent(int studentId) {
        String sql = "SELECT id FROM affectations WHERE id_student = ? AND status = 'EN_ATTENTE' LIMIT 1";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (Exception e) { e.printStackTrace(); }
        return -1;
    }

    public void refuserReservation(int affectationId, int roomId) {
        String sqlAffectation = "UPDATE affectations SET status = 'REFUSE' WHERE id = ?";
        String sqlRoom = "UPDATE rooms SET statut_room = 'LIBRE' WHERE id_room = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps1 = conn.prepareStatement(sqlAffectation);
                 PreparedStatement ps2 = conn.prepareStatement(sqlRoom)) {
                ps1.setInt(1, affectationId);
                ps1.executeUpdate();
                ps2.setInt(1, roomId);
                ps2.executeUpdate();
                conn.commit();
            } catch (Exception e) { conn.rollback(); throw e; }
        } catch (Exception e) { e.printStackTrace(); }
    }
}