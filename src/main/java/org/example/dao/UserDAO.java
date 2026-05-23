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
    /**
     * Inscrire un nouvel utilisateur
     */
    public boolean inscrire(User user) {

        String sql = "INSERT INTO Users (nom_users, prenom_user, email_user, " +
                "mot_passe_hash, statut, date_creation, id_roles) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getNomUsers());
            ps.setString(2, user.getPrenomUser());
            ps.setString(3, user.getEmailUser());
            ps.setString(4, user.getMotPasseHash());
            ps.setString(5, user.getStatut());
            ps.setTimestamp(6, java.sql.Timestamp.valueOf(user.getDateCreation()));
            ps.setInt(7, user.getRoleId());

            int lignes = ps.executeUpdate();
            return lignes > 0;

        } catch (Exception e) {
            System.out.println("❌ Erreur inscrire : " + e.getMessage());
            return false;
        }
    }
}
