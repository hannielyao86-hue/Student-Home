package org.example.controller;

import java.io.IOException;
import org.example.model.User;
import org.example.service.AuthService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class StudentDashboardController {

    @FXML private Circle avatarCircle;
    @FXML private Label studentNameLabel;
    @FXML private Label roomLabel;
    @FXML private Label welcomeLabel;
    @FXML private VBox dynamicContentArea;

    @FXML private Button btnHome;
    @FXML private Button btnProfil;
    @FXML private Button btnLogement;
    @FXML private Button btnReservation;
    @FXML private Button btnIncident;
    @FXML private Button btnPaiement;

    @FXML
    public void initialize() {
        User currentUser = AuthService.getCurrentUser();
        if (currentUser != null) {
            String fullName = currentUser.getPrenomUser() + " " + currentUser.getNomUsers();
            studentNameLabel.setText(fullName);
            welcomeLabel.setText("Bonjour, " + currentUser.getPrenomUser() + " 👋");
        } else {
            studentNameLabel.setText("Étudiant connecté");
            welcomeLabel.setText("Bonjour 👋");
        }
        roomLabel.setText("Chambre active");

        // Charger le contenu de l'accueil par défaut au démarrage
        loadSubPage("/view/student_home.fxml");
    }

    @FXML
    public void showHome() {
        setButtonActive(btnHome);
        User currentUser = AuthService.getCurrentUser();
        welcomeLabel.setText(currentUser != null ? "Bonjour, " + currentUser.getPrenomUser() + " 👋" : "Bonjour 👋");
        loadSubPage("/view/student_home.fxml");
    }

    @FXML
    public void showProfil() {
        setButtonActive(btnProfil);
        welcomeLabel.setText("Mon Profil Personnel");
        loadSubPage("/view/student_profil.fxml");
    }

    @FXML
    public void showLogement() {
        setButtonActive(btnLogement);
        welcomeLabel.setText("Détails de mon Logement");
        loadSubPage("/view/student_logement.fxml");
    }

    @FXML
    public void showReservation() {
        setButtonActive(btnReservation);
        welcomeLabel.setText("Réserver une chambre disponible");
        // Vérifie bien ce chemin :
        loadSubPage("/view/student_reservation.fxml");
    }

    /**
     * CORRIGÉ : Appelle maintenant précisément ton fichier student_incident.fxml
     */
    @FXML
    public void showIncident() {
        setButtonActive(btnIncident);
        welcomeLabel.setText("Déclaration d'Incident");
        loadSubPage("/view/student_incident.fxml");
    }

    @FXML
    public void showPaiement() {
        setButtonActive(btnPaiement);
        welcomeLabel.setText("Suivi de mes Loyers & Paiements");
        loadSubPage("/view/student_paiement.fxml");
    }

    @FXML
    public void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Stage stage = (Stage) btnHome.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setMaximized(true);
            stage.setTitle("Connexion");
            System.out.println("✅ Étudiant déconnecté avec succès.");
        } catch (IOException e) {
            System.err.println("❌ Erreur lors de la déconnexion : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * SÉCURISÉ : Utilise 'javafx.scene.Node' pour éviter tout crash de conversion
     */
    private void loadSubPage(String fxmlPath) {
        try {
            dynamicContentArea.getChildren().clear();
            java.net.URL url = getClass().getResource(fxmlPath);

            if (url == null) {
                System.err.println("❌ ERREUR FATALE : Le fichier est introuvable à l'adresse : " + fxmlPath);
                return;
            }

            FXMLLoader loader = new FXMLLoader(url);
            dynamicContentArea.getChildren().add(loader.load());
            System.out.println("✅ Page chargée : " + fxmlPath);

        } catch (IOException e) {
            System.err.println("❌ Erreur lors du chargement : " + e.getMessage());
            e.printStackTrace(); // Affiche la ligne exacte de l'erreur
        }
    }

    private void setButtonActive(Button activeButton) {
        Button[] allButtons = {btnHome, btnProfil, btnLogement, btnReservation, btnIncident, btnPaiement};
        for (Button btn : allButtons) {
            if (btn == activeButton) {
                btn.setStyle("-fx-background-color: #0284c7; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 12 15; -fx-background-radius: 8; -fx-cursor: hand;");
            } else {
                btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #94a3b8; -fx-font-size: 14px; -fx-padding: 12 15; -fx-background-radius: 8; -fx-cursor: hand;");
            }
        }
    }
}