package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.example.config.DatabaseConnection;
import org.example.model.Affectation;

public class AffectationDAO {

    // ==========================================
    // METHODE 1 : createAffectation
    // ==========================================
    public void createAffectation(Affectation affectation) {

        String checkRoomSql =
                "SELECT * FROM rooms " +
                        "WHERE id_room = ? " +
                        "AND statut_room = 'LIBRE'";

        String insertSql =
                "INSERT INTO affectations " +
                        "(student_id, room_id, affectation_date) " +
                        "VALUES (?, ?, ?)";

        String updateRoomSql =
                "UPDATE rooms " +
                        "SET statut_room = 'RESERVE' " +
                        "WHERE id_room = ?";

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();

            // ===== Vérifier chambre =====
            PreparedStatement checkStatement = connection.prepareStatement(checkRoomSql);
            checkStatement.setInt(1, affectation.getRoom().getIdRoom());
            ResultSet resultSet = checkStatement.executeQuery();

            if (!resultSet.next()) {
                System.out.println("❌ Chambre inexistante ou déjà occupée");
                return;
            }

            // ===== INSERT affectation =====
            PreparedStatement insertStatement = connection.prepareStatement(insertSql);
            insertStatement.setInt(1, affectation.getStudent().getIdStudent());
            insertStatement.setInt(2, affectation.getRoom().getIdRoom());
            insertStatement.setString(3, affectation.getAffectationDate());
            insertStatement.executeUpdate();

            // ===== UPDATE room =====
            PreparedStatement updateStatement = connection.prepareStatement(updateRoomSql);
            updateStatement.setInt(1, affectation.getRoom().getIdRoom());
            updateStatement.executeUpdate();

            System.out.println("✔ Affectation ajoutée avec succès");
            System.out.println("✔ Chambre mise à jour : RESERVE");

        } catch (Exception e) {
            System.out.println("❌ Erreur lors de l'affectation");
            e.printStackTrace();
        }
    } // <-- Fin de createAffectation

    // ==========================================
    // METHODE 2 : getLatestAffectationDate
    // ==========================================
    public String getLatestAffectationDate(int roomId) {
        String sql = "SELECT affectation_date FROM affectations WHERE room_id = ? ORDER BY id DESC LIMIT 1";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    java.sql.Date d = rs.getDate("affectation_date");
                    return d != null ? d.toString() : null;
                }
            }
        } catch (Exception e) {
            System.err.println("AffectationDAO getLatestAffectationDate error: " + e.getMessage());
        }
        return null;
    } // <-- Fin de getLatestAffectationDate

    // ==========================================
    // METHODE 3 : getLatestAffectationStudentId
    // ==========================================
    public Integer getLatestAffectationStudentId(int roomId) {
        String sql = "SELECT student_id FROM affectations WHERE room_id = ? ORDER BY id DESC LIMIT 1";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("student_id");
                }
            }
        } catch (Exception e) {
            System.err.println("AffectationDAO getLatestAffectationStudentId error: " + e.getMessage());
        }
        return null;
    } // <-- Fin de getLatestAffectationStudentId

} // <-- FIN DE LA CLASSE (Dernière accolade du fichier)