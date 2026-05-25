package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import org.example.dao.IncidentDAO;
import org.example.dao.StudentDAO;
import org.example.model.Incident;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class IncidentController {

    // Champs du formulaire
    @FXML private ComboBox<String> typeIncidentCombo;
    @FXML private TextArea descriptionField;
    @FXML private ComboBox<String> urgenceCombo;
    @FXML private DatePicker datePicker;
    @FXML private Label messageLabel;

    // Conteneur de la liste des incidents
    @FXML private VBox listeIncidents;

    // DAO
    private IncidentDAO incidentDAO = new IncidentDAO();

    // Id étudiant connecté (à remplacer par la session utilisateur)
    private int idStudentConnecte = 1; // TEMPORAIRE

    @FXML
    public void initialize() {

        // Types d'incidents
        typeIncidentCombo.getItems().addAll(
                "Plomberie", "Électricité", "Serrure",
                "Chauffage", "Internet", "Autre"
        );

        // Niveaux de priorité
        urgenceCombo.getItems().addAll("Faible", "Moyen", "Élevé", "Critique");

        // Charger les incidents existants
        chargerIncidents();
    }

    @FXML
    private void signalerIncident() {

        String typeIncident = typeIncidentCombo.getValue();
        String description  = descriptionField.getText().trim();
        String priorite     = urgenceCombo.getValue();
        LocalDate date      = datePicker.getValue();

        // Validation
        if (typeIncident == null || description.isEmpty()
                || priorite == null || date == null) {
            messageLabel.setText("⚠️ Veuillez remplir tous les champs.");
            return;
        }

        // Création de l'incident
        Incident incident = new Incident(
                typeIncident,
                description,
                priorite,
                "En cours",       // statut par défaut
                date,
                idStudentConnecte
        );

        // Sauvegarde
        boolean succes = incidentDAO.sauvegarder(incident);

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

        // En-tête
        HBox entete = new HBox(10);
        Label typeLabel = new Label(incident.getTypeIncident());
        typeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Label prioriteLabel = new Label(incident.getPriorite());
        prioriteLabel.setStyle("-fx-background-color: " + couleur + "; " +
                "-fx-text-fill: white; -fx-padding: 2 8; -fx-font-size: 11px;");

        Label statutLabel = new Label(incident.getStatutIncident());
        statutLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 11px;");

        entete.getChildren().addAll(typeLabel, prioriteLabel, statutLabel);

        // Date
        Label dateLabel = new Label(
                incident.getDateSignalement()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );
        dateLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 12px;");

        // Description
        Label descLabel = new Label(incident.getDescription());
        descLabel.setWrapText(true);
        descLabel.setStyle("-fx-font-size: 13px; -fx-padding: 5 0 0 0;");

        carte.getChildren().addAll(entete, dateLabel, descLabel);
        listeIncidents.getChildren().add(0, carte);
    }

    private void chargerIncidents() {
        List<Incident> incidents = incidentDAO.getAll();
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