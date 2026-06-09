package org.example.controller;

import java.io.IOException;
import org.example.model.User;
import org.example.model.Student;
import org.example.service.AuthService;
import org.example.dao.StudentDAO;
import org.example.utils.Session; // Assurez-vous que le nom du package est bien 'util' ou 'utils'

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * LoginController — Contrôleur de l'écran de connexion.
 *
 * Ce contrôleur gère :
 *   - l'analyse des champs de connexion,
 *   - l'appel au service d'authentification,
 *   - la redirection vers le dashboard admin ou étudiant,
 *   - l'initialisation de la session étudiante.
 */
public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    private final AuthService authService = new AuthService();
    private final StudentDAO studentDAO = new StudentDAO();

    @FXML
    public void handleLogin() {
        String emailInput = usernameField.getText().trim();
        String passwordInput = passwordField.getText();

        if (emailInput.isEmpty() || passwordInput.isEmpty()) {
            showError("Veuillez remplir tous les champs.");
            return;
        }

        // 1. Authentification via le service
        if (!authService.login(emailInput, passwordInput)) {
            showError("Email ou mot de passe incorrect.");
            return;
        }

        // 2. Récupération de l'utilisateur courant
        User currentUser = AuthService.getCurrentUser();
        if (currentUser == null) {
            showError("Erreur système : impossible de charger l'utilisateur.");
            return;
        }

        // 3. Gestion de la Session si c'est un étudiant
        String roleName = authService.getRoleName(currentUser);
        if ("STUDENT".equalsIgnoreCase(roleName)) {
            Student student = studentDAO.getStudentByUserId(currentUser.getIdUsers());
            Session.setLoggedInStudent(student);
        }

        // 4. Redirection selon le rôle
        if ("ADMIN".equalsIgnoreCase(roleName)) {
            redirectToPage("/view/dashboard.fxml", "Tableau de Bord - Administration");
        } else {
            redirectToPage("/view/student_dashboard.fxml", "Espace Résident Étudiant");
        }
    }

    /**
     * Déconnexion de l'utilisateur courant.
     *
     * Nettoie l'utilisateur de l'AuthService et vide la session globale.
     * Redirige ensuite vers l'écran de connexion.
     */
    public void handleLogout(ActionEvent event) {
        authService.logout(); // Nettoie le service et la Session
        redirectToPage("/view/login.fxml", "Connexion");
    }

    private void showError(String message) {
        if (messageLabel != null) {
            messageLabel.setText(message);
            messageLabel.setStyle("-fx-text-fill: #ef4444;");
        }
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
            e.printStackTrace();
        }
    }

    @FXML
    private void allerInscription() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/inscription.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}