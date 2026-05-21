package org.example.controller;

import org.example.service.AuthService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * LoginController.java
 *
 * Controller JavaFX de l'écran de connexion.
 *
 * Responsabilités :
 * - récupérer email + mot de passe saisis
 * - appeler AuthService.login()
 * - afficher les messages d'erreur
 * - rediriger vers le dashboard si connexion OK
 * - rediriger vers la page inscription si clic sur "S'inscrire"
 */
public class LoginController {


    // COMPOSANTS FXML (liés aux fx:id du login.fxml)

    /** Champ de saisie de l'email */
    @FXML private TextField emailField;

    /** Champ de saisie du mot de passe (masqué) */
    @FXML private PasswordField passwordField;

    /** Label pour afficher les messages d'erreur ou succès */
    @FXML private Label messageLabel;

    /** Bouton Se connecter (utilisé pour récupérer la fenêtre) */
    @FXML private Button loginButton;

    // SERVICE

    /** Service d'authentification — logique de connexion */
    // private AuthService authService = new AuthService();
    private AuthService authService; // TEMPORAIRE

    // ACTIONS

    /**
     * Action du bouton "Se connecter".
     *
     * Étapes :
     * 1. Vérifier que les champs ne sont pas vides
     * 2. Appeler AuthService.login()
     * 3. Si OK → redirection dashboard
     * 4. Si KO → afficher message d'erreur
     */
    @FXML
    public void handleLogin() {

        // 1. Récupérer les valeurs saisies
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        // 2. Validation : champs obligatoires
        if (email.isEmpty() || password.isEmpty()) {
            messageLabel.setText("⚠️ Veuillez remplir tous les champs");
            return;
        }

        // 3. Appel du service d'authentification
        // boolean success = authService.login(email, password);
        boolean success = true; // TEMPORAIRE - connexion toujours réussie

        if (success) {

            // 4a. Connexion réussie → charger le dashboard
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/view/dashboard.fxml")
                );

                // Récupérer la fenêtre actuelle via le bouton
                Stage stage = (Stage) loginButton.getScene().getWindow();

                // Remplacer la scène par le dashboard
                stage.setScene(new Scene(loader.load()));
                stage.setMaximized(true);

            } catch (Exception e) {
                System.out.println("❌ Erreur chargement dashboard : " + e.getMessage());
                messageLabel.setText("Erreur technique, contactez l'admin");
            }

        } else {

            // 4b. Connexion échouée → message d'erreur dans l'UI
            messageLabel.setText("❌ Email ou mot de passe incorrect");
        }
    }

    /**
     * Action du lien "S'inscrire".
     * Redirige vers la page d'inscription.
     */
    @FXML
    private void allerInscription() {
        try {
            // Charger le FXML de la page inscription
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/Inscription.fxml")
            );
            Parent root = loader.load();

            // Récupérer la fenêtre actuelle et changer la scène
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.out.println("❌ Erreur chargement inscription : " + e.getMessage());
            messageLabel.setText("Erreur technique, contactez l'admin");
        }
    }
}