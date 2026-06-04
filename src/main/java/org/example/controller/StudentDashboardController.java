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

    // Éléments injectés depuis le FXML (doivent avoir le même fx:id)
    @FXML private Circle avatarCircle;
    @FXML private Label studentNameLabel;
    @FXML private Label roomLabel;
    @FXML private Label welcomeLabel;
    @FXML private VBox dynamicContentArea;

    // Boutons du menu pour pouvoir modifier leur style (ex: mettre en surbrillance l'actif)
    @FXML private Button btnHome;
    @FXML private Button btnProfil;
    @FXML private Button btnLogement;
    @FXML private Button btnReservation;
    @FXML private Button btnIncident;
    @FXML private Button btnPaiement;

    /**
     * Méthode appelée automatiquement au chargement de la page
     */
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

    /**
     * Action du bouton : Tableau de bord
     */
    @FXML
    public void showHome() {
        setButtonActive(btnHome);
        User currentUser = AuthService.getCurrentUser();
        welcomeLabel.setText(currentUser != null ? "Bonjour, " + currentUser.getPrenomUser() + " 👋" : "Bonjour 👋");
        loadSubPage("/view/student_home.fxml");
    }

    /**
     * Action du bouton : Mon Profil
     */
    @FXML
    public void showProfil() {
        setButtonActive(btnProfil);
        welcomeLabel.setText("👤 Mon Profil Personnel");
        loadSubPage("/view/student_profil.fxml");
    }

    /**
     * Action du bouton : Mon Logement
     */
    @FXML
    public void showLogement() {
        setButtonActive(btnLogement);
        welcomeLabel.setText("🛏️ Détails de mon Logement");
        loadSubPage("/view/student_logement.fxml");
    }

    /**
     * Action du bouton : Réservation
     */
    @FXML
    public void showReservation() {
        setButtonActive(btnReservation);
        welcomeLabel.setText("🛌 Réserver une chambre disponible");
        loadSubPage("/view/student_reservation.fxml");
    }

    /**
     * Action du bouton : Signaler un incident
     */
    @FXML
    public void showIncident() {
        setButtonActive(btnIncident);
        welcomeLabel.setText("🛠️ Déclaration d'Incident");
        loadSubPage("/view/student_incident.fxml");
    }

    /**
     * Action du bouton : Mes Paiements
     */
    @FXML
    public void showPaiement() {
        setButtonActive(btnPaiement);
        welcomeLabel.setText("💳 Suivi de mes Loyers & Paiements");
        loadSubPage("/view/student_paiement.fxml");
    }

    /**
     * Action du bouton : Déconnexion
     */
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
     * Méthode utilitaire pour injecter une sous-page FXML au centre du Dashboard
     */
    private void loadSubPage(String fxmlPath) {
        try {
            dynamicContentArea.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            VBox newContent = loader.load();
            dynamicContentArea.getChildren().add(newContent);
            System.out.println("🔄 Sous-page chargée : " + fxmlPath);
        } catch (IOException e) {
            System.err.println("❌ Impossible de charger la sous-page : " + fxmlPath);
            System.err.println("Détail de l'erreur : " + e.getMessage());
        }
    }

    /**
     * Gère visuellement l'état "sélectionné" des boutons du menu
     */
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