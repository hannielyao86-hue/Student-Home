package org.example.service;

import org.example.config.DatabaseConnection;
import org.example.model.User;
import org.example.utils.PasswordUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * AuthService — Service d'authentification.
 *
 * Ce service vérifie les identifiants de l'utilisateur,
 * stocke l'utilisateur courant et permet de récupérer
 * le rôle associé à un compte.
 */
public class AuthService {

    private static User currentUser;

    /**
     * Authentifie l'utilisateur avec son email et mot de passe.
     *
     * @param email    email utilisé pour la connexion
     * @param password mot de passe en clair saisi par l'utilisateur
     * @return true si l'authentification réussit
     */
    public boolean login(String email, String password) {
        String query = "SELECT * FROM users WHERE email_user = ? LIMIT 1";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("mot_passe_hash");
                // Vérifier le mot de passe avec BCrypt
                if (storedHash != null && PasswordUtil.checkPassword(password, storedHash)) {
                    currentUser = new User(
                            rs.getInt("id_users"),
                            rs.getString("nom_users"),
                            rs.getString("prenom_user"),
                            rs.getString("email_user"),
                            storedHash,
                            rs.getString("statut"),
                            null, // ou rs.getTimestamp("date_creation").toLocalDateTime()
                            rs.getInt("id_roles")
                    );
                    return true;
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // Méthode pour récupérer le nom du rôle
    /**
     * Récupère le nom du rôle pour l'utilisateur donné.
     *
     * @param user utilisateur connecté
     * @return nom du rôle (ex: ADMIN, STUDENT) ou null si introuvable
     */
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

    /**
     * Déconnecte l'utilisateur courant.
     *
     * Réinitialise l'utilisateur stocké dans le service et vide
     * la session globale de l'application.
     */
    public void logout() {
        currentUser = null; // Réinitialise l'utilisateur dans le service
        org.example.utils.Session.clear(); // Vide également votre classe Session globale
    }
}