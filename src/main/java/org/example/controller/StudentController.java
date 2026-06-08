package org.example.controller;

import org.example.dao.AffectationDAO;
import org.example.model.Affectation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.beans.property.SimpleStringProperty;
import java.io.IOException;
import java.util.List;

public class StudentController {

    @FXML private TableView<Affectation> reservationTable;
    @FXML private TableColumn<Affectation, String> nomColumn, prenomColumn, chambreColumn, dateEntreeColumn, dateSortieColumn;

    private AffectationDAO dao = new AffectationDAO();

    @FXML
    public void initialize() {
        nomColumn.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getStudent() != null ? data.getValue().getStudent().getNom() : "N/A"));

        prenomColumn.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getStudent() != null ? data.getValue().getStudent().getPrenom() : "N/A"));

        chambreColumn.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getRoom() != null ? data.getValue().getRoom().getNumeroRoom() : "N/A"));

        dateEntreeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDateEntree()));

        dateSortieColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDateSortie()));

        loadDemandesEnAttente();
    }

    // UNE SEULE FOIS : Cette méthode contient tout le nécessaire
    @FXML
    public void loadDemandesEnAttente() {
        List<Affectation> list = dao.getAllAffectationsByStatus("EN_ATTENTE");

        // Log de debug pour voir si Java récupère bien les données
        for (Affectation aff : list) {
            String nom = (aff.getStudent() != null) ? aff.getStudent().getNom() : "NULL";
            System.out.println("DEBUG : Affectation ID " + aff.getId() + " - Étudiant: " + nom);
        }

        reservationTable.getItems().setAll(list);
    }

    @FXML
    public void handleAccepterReservation() {
        Affectation selected = reservationTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Attention", "Veuillez sélectionner une demande.");
            return;
        }
        dao.accepterReservation(selected.getId(), selected.getRoom().getIdRoom());
        loadDemandesEnAttente();
        showAlert("Succès", "Réservation acceptée.");
    }

    @FXML
    public void handleRefuserReservation() {
        Affectation selected = reservationTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Attention", "Veuillez sélectionner une demande.");
            return;
        }
        dao.refuserReservation(selected.getId(), selected.getRoom().getIdRoom());
        loadDemandesEnAttente();
        showAlert("Information", "Réservation refusée.");
    }

    // --- Navigation (Inchangé) ---
    @FXML public void handleDashboard(ActionEvent event) { changeScene(event, "/view/dashboard.fxml"); }
    @FXML public void handleEtudiants(ActionEvent event) { }
    @FXML public void handleLogements(ActionEvent event) { changeScene(event, "/view/logements.fxml"); }
    @FXML public void handleLogout(ActionEvent event) { changeScene(event, "/view/login.fxml"); }

    private void changeScene(ActionEvent event, String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}