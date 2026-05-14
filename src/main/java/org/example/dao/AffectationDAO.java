package org.example.dao;

import org.example.config.DatabaseConnection;
import org.example.model.Affectation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AffectationDAO {

    public void createAffectation(Affectation affectation) {

        // ===== Vérification chambre =====

        String checkRoomSql =
                "SELECT * FROM rooms " +
                        "WHERE id_room = ? " +
                        "AND statut_room = 'LIBRE'";

        // ===== INSERT affectation =====

        String insertSql =
                "INSERT INTO affectations " +
                        "(student_id, room_id, affectation_date) " +
                        "VALUES (?, ?, ?)";

        // ===== UPDATE chambre =====

        String updateRoomSql =
                "UPDATE rooms " +
                        "SET statut_room = 'OCCUPEE' " +
                        "WHERE id_room = ?";

        try {

            Connection connection =
                    DatabaseConnection
                            .getInstance()
                            .getConnection();

            // ===== Vérifier chambre =====

            PreparedStatement checkStatement =
                    connection.prepareStatement(checkRoomSql);

            checkStatement.setInt(
                    1,
                    affectation.getRoom().getIdRoom()
            );

            ResultSet resultSet =
                    checkStatement.executeQuery();

            // Chambre non libre
            if (!resultSet.next()) {

                System.out.println(
                        "❌ Chambre inexistante ou déjà occupée"
                );

                return;
            }

            // ===== INSERT affectation =====

            PreparedStatement insertStatement =
                    connection.prepareStatement(insertSql);

            insertStatement.setInt(
                    1,
                    affectation.getStudent().getIdStudent()
            );

            insertStatement.setInt(
                    2,
                    affectation.getRoom().getIdRoom()
            );

            insertStatement.setString(
                    3,
                    affectation.getAffectationDate()
            );

            insertStatement.executeUpdate();

            // ===== UPDATE room =====

            PreparedStatement updateStatement =
                    connection.prepareStatement(updateRoomSql);

            updateStatement.setInt(
                    1,
                    affectation.getRoom().getIdRoom()
            );

            updateStatement.executeUpdate();

            System.out.println(
                    "✔ Affectation ajoutée avec succès"
            );

            System.out.println(
                    "✔ Chambre mise à jour : OCCUPEE"
            );

        } catch (Exception e) {

            System.out.println(
                    "❌ Erreur lors de l'affectation"
            );

            e.printStackTrace();
        }
    }
}