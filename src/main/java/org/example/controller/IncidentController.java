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
    @FXML private VBox listeIncidents;

    private IncidentDAO IncidentDAO = new IncidentDAO();
    private int idStudentConnecte = 1;

    @FXML
    public void initialize() {
        typeIncidentCombo.getItems().addAll(
                "Plomberie", "Électricité", "Serrure",
                "Chauffage", "Internet", "Autre"
        );
        urgenceCombo.getItems().addAll("Faible", "Moyen", "Élevé", "Critique");
        chargerIncidents();
    }

    @FXML
    private void signalerIncident() {
        String typeIncident = typeIncidentCombo.getValue();
        String description  = descriptionField.getText().trim();
        String priorite     = urgenceCombo.getValue();
        LocalDate date      = datePicker.getValue();

        if (typeIncident == null || description.isEmpty()
                || priorite == null || date == null) {
            messageLabel.setText("⚠️ Veuillez remplir tous les champs.");
            return;
        }

        Incident incident = new Incident(
                typeIncident, description, priorite,
                "En cours", date, idStudentConnecte
        );

        boolean succes = IncidentDAO.sauvegarder(incident);

        if (succes) {
            ajouterIncidentDansListe(incident);
            reinitialiserFormulaire();
            messageLabel.setTextFill(Color.GREEN);
            messageLabel.setText("✅ Incident signalé avec succès !");
        } else {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("❌ Erreur lors de l'enregistrement.");
        }
    }

    // ✅ Méthodes de navigation — BIEN en dehors de signalerIncident()

    @FXML
    private void handleDashboard() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/view/dashboard.fxml"));
            javafx.scene.Parent root = loader.load();
            listeIncidents.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEtudiants() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/view/students.fxml"));
            javafx.scene.Parent root = loader.load();
            listeIncidents.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogements() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/view/logements.fxml"));
            javafx.scene.Parent root = loader.load();
            listeIncidents.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePaiements() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/view/paiements.fxml"));
            javafx.scene.Parent root = loader.load();
            listeIncidents.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleIncidents() {
        // Déjà sur cette page
    }

    @FXML
    private void handleLogout() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/view/login.fxml"));
            javafx.scene.Parent root = loader.load();
            listeIncidents.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    // ✅ ICI — entre les handle et les méthodes privées
    private void loadPage(String fxmlPath) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource(fxmlPath));
            javafx.stage.Stage stage = (javafx.stage.Stage)
                    listeIncidents.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(loader.load()));
            stage.setMaximized(true);
        } catch (Exception e) {
            System.out.println("❌ Erreur chargement page : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void ajouterIncidentDansListe(Incident incident) {
        String couleur = switch (incident.getPriorite()) {
            case "Faible"   -> "#2ecc71";
            case "Moyen"    -> "#f39c12";
            case "Élevé"    -> "#e67e22";
            case "Critique" -> "#e74c3c";
            default         -> "#95a5a6";
        };

        VBox carte = new VBox(5);
        carte.setStyle("-fx-background-color: #f9f9f9; -fx-padding: 15; " +
                "-fx-border-color: " + couleur + "; -fx-border-width: 0 0 0 5;");

        HBox entete = new HBox(10);
        Label typeLabel = new Label(incident.getTypeIncident());
        typeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Label prioriteLabel = new Label(incident.getPriorite());
        prioriteLabel.setStyle("-fx-background-color: " + couleur + "; " +
                "-fx-text-fill: white; -fx-padding: 2 8; -fx-font-size: 11px;");

        Label statutLabel = new Label(incident.getStatutIncident());
        statutLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 11px;");

        entete.getChildren().addAll(typeLabel, prioriteLabel, statutLabel);

        Label dateLabel = new Label(
                incident.getDateSignalement()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );
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
        typeIncidentCombo.setValue(null);
        descriptionField.clear();
        urgenceCombo.setValue(null);
        datePicker.setValue(null);
    }
}