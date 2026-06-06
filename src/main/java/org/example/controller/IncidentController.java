package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import org.example.dao.IncidentDAO;
import org.example.model.Incident;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class IncidentController {

    @FXML private ComboBox<String> typeIncidentCombo;
    @FXML private TextArea descriptionField;
    @FXML private ComboBox<String> urgenceCombo;
    @FXML private DatePicker datePicker;
    @FXML private Label messageLabel;

    // Présent uniquement sur la vue Admin (Sera automatiquement null sur la vue Étudiant)
    @FXML private VBox listeIncidents;

    private IncidentDAO IncidentDAO = new IncidentDAO();
    private int idStudentConnecte = 1;

    @FXML
    public void initialize() {
        // SÉCURITÉ : On remplit le formulaire étudiant uniquement si les composants existent à l'écran
        if (typeIncidentCombo != null) {
            typeIncidentCombo.getItems().addAll(
                    "Plomberie", "Électricité", "Serrure",
                    "Chauffage", "Internet", "Autre"
            );
        }
        if (urgenceCombo != null) {
            urgenceCombo.getItems().addAll("Faible", "Moyen", "Élevé", "Critique");
        }

        // SÉCURITÉ CRITIQUE : On ne charge la liste BDD que si la VBox Admin existe !
        if (listeIncidents != null) {
            chargerIncidents();
        }
    }

    @FXML
    private void signalerIncident() {
        String typeIncident = typeIncidentCombo.getValue();
        String description  = descriptionField.getText().trim();
        String priorite     = urgenceCombo.getValue();
        LocalDate date      = datePicker.getValue();

        if (typeIncident == null || description.isEmpty() || priorite == null || date == null) {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("⚠️ Veuillez remplir tous les champs.");
            return;
        }

        Incident incident = new Incident(
                typeIncident, description, priorite,
                "En cours", date, idStudentConnecte
        );

        boolean succes = IncidentDAO.sauvegarder(incident);

        if (succes) {
            // Si la liste est visible (Vue Admin / Double panneau), on ajoute en temps réel
            if (listeIncidents != null) {
                ajouterIncidentDansListe(incident);
            }
            reinitialiserFormulaire();
            messageLabel.setTextFill(Color.GREEN);
            messageLabel.setText("✅ Incident signalé avec succès !");
        } else {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("❌ Erreur lors de l'enregistrement.");
        }
    }

    // Centralisation propre de la navigation Admin basée sur l'événement du clic bouton
    private void naviguerVers(String fxmlPath, javafx.event.ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource(fxmlPath));
            javafx.scene.Parent root = loader.load();
            javafx.scene.Node source = (javafx.scene.Node) event.getSource();
            javafx.stage.Stage stage = (javafx.stage.Stage) source.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println("❌ Erreur de navigation vers " + fxmlPath + " : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML private void handleDashboard(javafx.event.ActionEvent event) { naviguerVers("/view/dashboard.fxml", event); }
    @FXML private void handleEtudiants(javafx.event.ActionEvent event) { naviguerVers("/view/students.fxml", event); }
    @FXML private void handleLogements(javafx.event.ActionEvent event) { naviguerVers("/view/logements.fxml", event); }
    @FXML private void handlePaiements(javafx.event.ActionEvent event) { naviguerVers("/view/paiements.fxml", event); }
    @FXML private void handleIncidents() { /* Déjà sur la page active admin */ }
    @FXML private void handleLogout(javafx.event.ActionEvent event) { naviguerVers("/view/login.fxml", event); }

    private void ajouterIncidentDansListe(Incident incident) {
        if (listeIncidents == null) return;

        String couleur = switch (incident.getPriorite()) {
            case "Faible"   -> "#2ecc71";
            case "Moyen"    -> "#f39c12";
            case "Élevé"    -> "#e67e22";
            case "Critique" -> "#e74c3c";
            default         -> "#95a5a6";
        };

        VBox carte = new VBox(5);
        carte.setStyle("-fx-background-color: #f9f9f9; -fx-padding: 15; -fx-border-color: " + couleur + "; -fx-border-width: 0 0 0 5;");

        HBox entete = new HBox(10);
        Label typeLabel = new Label(incident.getTypeIncident());
        typeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Label prioriteLabel = new Label(incident.getPriorite());
        prioriteLabel.setStyle("-fx-background-color: " + couleur + "; -fx-text-fill: white; -fx-padding: 2 8; -fx-font-size: 11px;");

        Label statutLabel = new Label(incident.getStatutIncident());
        statutLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 11px;");

        entete.getChildren().addAll(typeLabel, prioriteLabel, statutLabel);

        Label dateLabel = new Label(incident.getDateSignalement().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        dateLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 12px;");

        Label descLabel = new Label(incident.getDescription());
        descLabel.setWrapText(true);
        descLabel.setStyle("-fx-font-size: 13px; -fx-padding: 5 0 0 0;");

        carte.getChildren().addAll(entete, dateLabel, descLabel);
        listeIncidents.getChildren().add(0, carte);
    }

    private void chargerIncidents() {
        List<Incident> incidents = IncidentDAO.getAll();
        for (Incident incident : incidents) {
            ajouterIncidentDansListe(incident);
        }
    }

    private void reinitialiserFormulaire() {
        if (typeIncidentCombo != null) typeIncidentCombo.setValue(null);
        if (descriptionField != null) descriptionField.clear();
        if (urgenceCombo != null) urgenceCombo.setValue(null);
        if (datePicker != null) datePicker.setValue(null);
    }
}