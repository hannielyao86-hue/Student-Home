package org.example.dao;

import org.example.config.DatabaseConnection;
import org.example.model.Incident;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IncidentDAO {

    private Connection connection;

    public IncidentDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    // Génère automatiquement un id type "INC001", "INC002"...
    private String genererIdIncident() throws SQLException {
        String sql = "SELECT COUNT(*) FROM incidents";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                int count = rs.getInt(1) + 1;
                return String.format("INC%03d", count);
            }
        }
        return "INC001";
    }

    // Sauvegarde un incident en base
    public boolean sauvegarder(Incident incident) {
        try {
            String idIncident = genererIdIncident();

            String sql = "INSERT INTO incidents (id_incident, type_incident, description, " +
                    "priorite, statut_incident, date_signalement, id_student) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, idIncident);
                stmt.setString(2, incident.getTypeIncident());
                stmt.setString(3, incident.getDescription());
                stmt.setString(4, incident.getPriorite());
                stmt.setString(5, incident.getStatutIncident());
                stmt.setDate(6, Date.valueOf(incident.getDateSignalement()));
                stmt.setInt(7, incident.getIdStudent());
                stmt.executeUpdate();

                incident.setIdIncident(idIncident);
                return true;
            }

        } catch (SQLException e) {
            System.out.println("❌ Erreur sauvegarde incident : " + e.getMessage());
            return false;
        }
    }

    // Récupère tous les incidents depuis la base
    public List<Incident> getAll() {
        List<Incident> incidents = new ArrayList<>();
        String sql = "SELECT * FROM incidents ORDER BY date_signalement DESC";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Incident incident = new Incident(
                        rs.getString("type_incident"),
                        rs.getString("description"),
                        rs.getString("priorite"),
                        rs.getString("statut_incident"),
                        rs.getDate("date_signalement").toLocalDate(),
                        rs.getInt("id_student")
                );
                incident.setIdIncident(rs.getString("id_incident"));
                incidents.add(incident);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur récupération incidents : " + e.getMessage());
        }
        return incidents;
    }
}