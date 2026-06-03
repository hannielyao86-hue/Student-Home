package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.io.IOException;

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
    @FXML private Button btnIncident;
    @FXML private Button btnPaiement;

    /**
     * Méthode appelée automatiquement au chargement de la page
     */
    @FXML
    public void initialize() {
        // Ici, tu pourras récupérer plus tard les infos de l'étudiant connecté
        // depuis une session ou la base de données
        studentNameLabel.setText("Jean Dupont");
        roomLabel.setText("Chambre N° 204");
        welcomeLabel.setText("Bonjour, Jean 👋");

        // Charger le contenu de l'accueil par défaut au démarrage
        loadSubPage("/view/student_home.fxml");
    }

    /**
     * Action du bouton : Tableau de bord
     */
    @FXML
    public void showHome() {
        setButtonActive(btnHome);
        welcomeLabel.setText("Bonjour, Jean 👋");
        loadSubPage("/view/student_home.fxml"); // Il faudra créer ce FXML (mini résumé)
    }

    /**
     * Action du bouton : Mon Profil
     */
    @FXML
    public void showProfil() {
        setButtonActive(btnProfil);
        welcomeLabel.setText("👤 Mon Profil Personnel");
        loadSubPage("/view/student_profil.fxml"); // FXML avec ses informations de compte
    }

    /**
     * Action du bouton : Mon Logement
     */
    @FXML
    public void showLogement() {
        setButtonActive(btnLogement);
        welcomeLabel.setText("🛏️ Détails de mon Logement");
        loadSubPage("/view/student_logement.fxml"); // FXML avec le bail, état des lieux, etc.
    }

    /**
     * Action du bouton : Signaler un incident
     */
    @FXML
    public void showIncident() {
        setButtonActive(btnIncident);
        welcomeLabel.setText("🛠️ Déclaration d'Incident");
        loadSubPage("/view/student_incident.fxml"); // Ton formulaire de création d'incident
    }

    /**
     * Action du bouton : Mes Paiements
     */
    @FXML
    public void showPaiement() {
        setButtonActive(btnPaiement);
        welcomeLabel.setText("💳 Suivi de mes Loyers & Paiements");
        loadSubPage("/view/student_paiement.fxml"); // Liste des quittances et statuts de paiement
    }

    /**
     * Action du bouton : Déconnexion
     */
    @FXML
    public void handleLogout() {
        try {
            // Recharge l'interface de connexion principale
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
            // 1. On vide la zone centrale actuelle
            dynamicContentArea.getChildren().clear();

            // 2. On charge le nouveau fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            VBox newContent = loader.load();

            // 3. On l'ajoute au centre
            dynamicContentArea.getChildren().add(newContent);
            System.out.println("🔄 Sous-page chargée : " + fxmlPath);
        } catch (IOException e) {
            System.err.println("❌ Impossible de charger la sous-page : " + fxmlPath);
            System.err.println("Détail de l'erreur : " + e.getMessage());
            // Optionnel : ajouter un label d'erreur visuel au centre si le fichier manque
        }
    }

    /**
     * Gère visuellement l'état "sélectionné" des boutons du menu
     */
    private void setButtonActive(Button activeButton) {
        // Liste de tous les boutons de navigation
        Button[] allButtons = {btnHome, btnProfil, btnLogement, btnIncident, btnPaiement};

        for (Button btn : allButtons) {
            if (btn == activeButton) {
                // Style bleu vif pour le bouton cliqué
                btn.setStyle("-fx-background-color: #0284c7; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 12 15; -fx-background-radius: 8; -fx-cursor: hand;");
            } else {
                // Style transparent par défaut pour les autres
                btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #94a3b8; -fx-font-size: 14px; -fx-padding: 12 15; -fx-background-radius: 8; -fx-cursor: hand;");
            }
        }
    }
}