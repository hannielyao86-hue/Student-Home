package org.example.dao;

import org.example.config.DatabaseConnection;
import org.example.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * DAO = Data Access Object
 * Responsable des requêtes SQL sur la table Users
 */
public class UserDAO {

    private Connection connection;

    public UserDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    /** Trouver un utilisateur par email
     */
    public User findByEmail(String email) {

        User user = null;
        String sql = "SELECT * FROM Users WHERE email_user = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setIdUsers(rs.getInt("id_users"));
                user.setNomUsers(rs.getString("nom_users"));
                user.setPrenomUser(rs.getString("prenom_user"));
                user.setEmailUser(rs.getString("email_user"));
                user.setMotPasseHash(rs.getString("mot_passe_hash"));
                user.setStatut(rs.getString("statut"));
                user.setDateCreation(rs.getTimestamp("date_creation").toLocalDateTime());

                // relation role (simple version)
                user.setRoleId(rs.getInt("id_roles"));
            }

        } catch (Exception e) {
            System.out.println("❌ Erreur findByEmail : " + e.getMessage());
        }

        return user;
    }

    /**
     * Vérifier si email existe
     */
    public boolean emailExists(String email) {

        String sql = "SELECT id_users FROM Users WHERE email_user = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            System.out.println("❌ Erreur emailExists : " + e.getMessage());
            return false;
        }
    }

    /**
     * Vérifier si compte actif
     */
    public boolean isUserActive(String email) {

        String sql = "SELECT statut FROM Users WHERE email_user = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("statut").equalsIgnoreCase("ACTIF");
            }

        } catch (Exception e) {
            System.out.println("❌ Erreur isUserActive : " + e.getMessage());
        }

        return false;
    }

    /**
     *  Récupérer mot de passe hashé
     */
    public String getPasswordHash(String email) {

        String sql = "SELECT mot_passe_hash FROM Users WHERE email_user = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("mot_passe_hash");
            }

        } catch (Exception e) {
            System.out.println("❌ Erreur getPasswordHash : " + e.getMessage());
        }

        return null;
    }
}
