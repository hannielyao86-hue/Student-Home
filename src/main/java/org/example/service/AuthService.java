package org.example.service;

import org.example.config.DatabaseConnection;
import org.example.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthService {

    private static User currentUser;

    // Méthode de connexion
    public boolean login(String email, String password) {
        String query = "SELECT * FROM users WHERE email_user = ? AND mot_passe_hash = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                currentUser = new User(
                        rs.getInt("id_users"),
                        rs.getString("nom_users"),
                        rs.getString("prenom_user"),
                        rs.getString("email_user"),
                        rs.getString("mot_passe_hash"),
                        rs.getString("statut"),
                        null, // ou rs.getTimestamp("date_creation").toLocalDateTime()
                        rs.getInt("id_roles")
                );
                return true;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // Méthode pour récupérer le nom du rôle
    public String getRoleName(User user) {
        if (user == null) return null;
        String query = "SELECT nom FROM roles WHERE id_roles = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, user.getRoleId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("nom");
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser = null; // Réinitialise l'utilisateur dans le service
        org.example.utils.Session.clear(); // Vide également votre classe Session globale
    }
}