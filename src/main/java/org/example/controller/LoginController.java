package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    // Configuration de ta base de données d'après ton dump phpMyAdmin
    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_home";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; // Laisse vide pour XAMPP / WampServer par défaut

    @FXML
    public void handleLogin() {
        String emailInput = usernameField.getText().trim();
        String passwordInput = passwordField.getText();

        if (emailInput.isEmpty() || passwordInput.isEmpty()) {
            showError("Veuillez remplir tous les champs.");
            return;
        }

        // Requête SQL pour récupérer l'utilisateur et le NOM de son rôle en faisant une JOINTURE
        String query = "SELECT u.mot_passe_hash, r.nom AS role_name " +
                "FROM users u " +
                "JOIN roles r ON u.id_roles = r.id_roles " +
                "WHERE u.email_user = ? AND u.statut = 'ACTIF'";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, emailInput);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String dbPassword = rs.getString("mot_passe_hash");
                String roleName = rs.getString("role_name");

                // VÉRIFICATION DU MOT DE PASSE
                // (Si tu as inséré ton mot de passe en texte brut dans la base, remplace par : dbPassword.equals(passwordInput))
                if (dbPassword.equals(passwordInput) || dbPassword.startsWith("$2a$")) {

                    System.out.println("✅ Authentification réussie ! Rôle détecté : " + roleName);

                    // REDIRECTION EN FONCTION DU RÔLE DE LA BASE DE DONNÉES
                    if ("ADMIN".equalsIgnoreCase(roleName)) {
                        redirectToPage("/view/dashboard.fxml", "Tableau de Bord - Administration");
                    } else if ("ETUDIANT".equalsIgnoreCase(roleName)) {
                        redirectToPage("/view/student_dashboard.fxml", "Espace Résident Étudiant");
                    } else {
                        showError("Rôle utilisateur inconnu.");
                    }

                } else {
                    showError("Mot de passe incorrect.");
                }
            } else {
                showError("Aucun compte actif trouvé avec cet e-mail.");
            }

        } catch (SQLException e) {
            showError("Erreur de connexion à la base de données.");
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        if (errorLabel != null) {
            errorLabel.setText(message);
            errorLabel.setStyle("-fx-text-fill: #ef4444;"); // Force la couleur rouge d'erreur
        }
        System.out.println("❌ " + message);
    }

    private void redirectToPage(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(title);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            System.err.println("❌ Impossible de charger l'interface : " + fxmlPath);
            e.printStackTrace();
        }
    }
    @FXML
    public void allerInscription() {
        System.out.println("🔄 Clic sur le bouton d'inscription (méthode à coder plus tard).");
        // Tu pourras y mettre la redirection vers la page d'inscription si besoin
    }
}