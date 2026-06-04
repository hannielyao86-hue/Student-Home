package org.example.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.example.config.DatabaseConnection;
import org.example.model.User;
import org.example.service.AuthService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    private final AuthService authService = new AuthService();

    @FXML
    public void handleLogin() {
        String emailInput = usernameField.getText().trim();
        String passwordInput = passwordField.getText();

        if (emailInput.isEmpty() || passwordInput.isEmpty()) {
            showError("Veuillez remplir tous les champs.");
            return;
        }

        if (!authService.login(emailInput, passwordInput)) {
            showError("Email ou mot de passe incorrect.");
            return;
        }

        User currentUser = AuthService.getCurrentUser();
        if (currentUser == null) {
            showError("Impossible de récupérer l'utilisateur connecté.");
            return;
        }

        String roleName = getRoleNameForUser(currentUser);
        if ("ADMIN".equalsIgnoreCase(roleName)) {
            redirectToPage("/view/dashboard.fxml", "Tableau de Bord - Administration");
        } else {
            redirectToPage("/view/student_dashboard.fxml", "Espace Résident Étudiant");
        }
    }

    private String getRoleNameForUser(User user) {
        if (user == null) {
            return null;
        }

        String query = "SELECT nom FROM roles WHERE id_roles = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, user.getRoleId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nom");
            }
        } catch (Exception e) {
            System.err.println("❌ Erreur récupération rôle : " + e.getMessage());
        }

        return null;
    }

    private void showError(String message) {
        if (messageLabel != null) {
            messageLabel.setText(message);
            messageLabel.setStyle("-fx-text-fill: #ef4444;");
        }
        System.out.println("❌ " + message);
    }

    private void redirectToPage(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setMaximized(true);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            System.err.println("❌ Impossible de charger l'interface : " + fxmlPath);
            e.printStackTrace();
        }
    }

    @FXML
    private void allerInscription() {
        try {
            // 1. Charger le fichier FXML de la page d'inscription
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/inscription.fxml"));
            javafx.scene.Parent root = loader.load();

            // 2. Récupérer la fenêtre (Stage) actuelle via un des composants du login (le bouton ou le champ texte)
            javafx.stage.Stage stage = (javafx.stage.Stage) usernameField.getScene().getWindow();

            // 3. Remplacer la scène par celle de l'inscription
            stage.setScene(new javafx.scene.Scene(root));
            stage.centerOnScreen(); // Optionnel : recentrer proprement la fenêtre

        } catch (java.io.IOException e) {
            System.err.println("Erreur de chargement de la page inscription.fxml : " + e.getMessage());
            e.printStackTrace();
        }
    }
}