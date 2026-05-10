package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.model.User;
import org.example.service.AuthService;

/**
 * DashboardController.java
 *
 * Controller JavaFX du tableau de bord principal.
 * S'initialise automatiquement après le chargement du FXML.
 *
 * Responsabilités :
 * - afficher le nom et rôle de l'utilisateur connecté
 * - afficher les statistiques (étudiants, logements, incidents)
 * - gérer la déconnexion
 */
public class DashboardController {

    // COMPOSANTS FXML (liés aux fx:id du dashboard.fxml)

    /** Affiche le prénom + nom de l'utilisateur connecté */
    @FXML private Label nomUserLabel;

    /** Affiche le rôle de l'utilisateur connecté */
    @FXML private Label roleLabel;

    /** Nombre d'étudiants enregistrés */
    @FXML private Label nbEtudiantsLabel;

    /** Nombre de logements disponibles */
    @FXML private Label nbLogementsLabel;

    /** Nombre d'incidents en cours */
    @FXML private Label nbIncidentsLabel;

    // INITIALISATION AUTOMATIQUE

    /**
     * Méthode appelée automatiquement par JavaFX
     * au chargement du fichier FXML.
     *
     * On y récupère l'utilisateur connecté depuis la session
     * et on remplit les labels du dashboard.
     */
    @FXML
    public void initialize() {

        // Récupérer l'utilisateur connecté depuis AuthService
        User user = AuthService.getCurrentUser();

        if (user != null) {
            // Afficher prénom + nom dans la sidebar
            nomUserLabel.setText(user.getPrenomUser() + " " + user.getNomUsers());

            // Afficher le rôle (on affichera le nom du rôle plus tard)
            roleLabel.setText("Rôle ID : " + user.getRoleId());
        }

        // Statistiques — valeurs à 0 pour l'instant
        // On les connectera à la base de données au Sprint 2

        nbEtudiantsLabel.setText("0");
        nbLogementsLabel.setText("0");
        nbIncidentsLabel.setText("0");
    }

    // ACTIONS

    /**
     * Gère le clic sur "Déconnexion".
     *
     * Étapes :
     * 1. Appel logout() dans AuthService → vide la session
     * 2. Retour à l'écran de login
     */
    @FXML
    public void handleLogout() {

        try {
            // 1. Vider la session utilisateur
            new AuthService().logout();

            // 2. Charger le fichier FXML de login
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/login.fxml")
            );

            // 3. Récupérer la fenêtre actuelle
            Stage stage = (Stage) nomUserLabel.getScene().getWindow();

            // 4. Remplacer la scène par le login
            stage.setScene(new Scene(loader.load()));
            stage.setMaximized(true);

        } catch (Exception e) {
            System.out.println("❌ Erreur lors du logout : " + e.getMessage());
        }
    }

    /**
     * Navigation vers la gestion des étudiants
     * (page à créer au Sprint 2)
     */
    @FXML
    public void handleEtudiants() {
        System.out.println("→ Module Étudiants (Sprint 2)");
        // TODO : charger etudiants.fxml
    }

    /**
     * Navigation vers la gestion des logements
     */
    @FXML
    public void handleLogements() {
        System.out.println("→ Module Logements (Sprint 2)");
        // TODO : charger logements.fxml
    }

    /**
     * Navigation vers la gestion des paiements
     */
    @FXML
    public void handlePaiements() {
        System.out.println("→ Module Paiements (Sprint 2)");
        // TODO : charger paiements.fxml
    }

    /**
     * Navigation vers la gestion des incidents
     */
    @FXML
    public void handleIncidents() {
        System.out.println("→ Module Incidents (Sprint 2)");
        // TODO : charger incidents.fxml
    }
}