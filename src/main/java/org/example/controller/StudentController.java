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

    // Instance unique nommée "dao" utilisée partout
    private AffectationDAO dao = new AffectationDAO();

    @FXML
    public void initialize() {
        // Mapping des colonnes
        nomColumn.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getStudent() != null ? data.getValue().getStudent().getNom() : ""));

        prenomColumn.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getStudent() != null ? data.getValue().getStudent().getPrenom() : ""));

        chambreColumn.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getRoom() != null ? data.getValue().getRoom().getNumeroRoom() : ""));

        dateEntreeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDateEntree()));

        dateSortieColumn.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getDateSortie() != null ? data.getValue().getDateSortie() : ""));

        // Charger les données dès l'initialisation
        loadDemandesEnAttente();
    }

    @FXML
    public void loadDemandesEnAttente() {
        // Utilisation de "dao"
        List<Affectation> list = dao.getAllAffectationsByStatus("EN_ATTENTE");

        if (list.isEmpty()) {
            System.out.println("DEBUG : Aucune donnée EN_ATTENTE trouvée dans la base.");
        }

        reservationTable.getItems().setAll(list);
    }

    @FXML
    public void handleAccepterReservation() {
        Affectation selected = reservationTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Attention", "Veuillez sélectionner une demande dans le tableau.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Valider cette réservation ?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait();

        if (confirm.getResult() == ButtonType.YES) {
            // Utilisation de "dao"
            dao.accepterReservation(selected.getId(), selected.getRoom().getIdRoom());
            loadDemandesEnAttente();
            showAlert("Succès", "La réservation a été acceptée.");
        }
    }

    @FXML
    public void handleRefuserReservation() {
        Affectation selected = reservationTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Attention", "Veuillez sélectionner une demande à refuser.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Êtes-vous sûr de vouloir refuser cette réservation ?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait();

        if (confirm.getResult() == ButtonType.YES) {
            // Utilisation de "dao"
            dao.refuserReservation(selected.getId(), selected.getRoom().getIdRoom());
            loadDemandesEnAttente();
            showAlert("Information", "La réservation a été refusée.");
        }
    }

    // --- Navigation ---
    @FXML public void handleDashboard(ActionEvent event) { changeScene(event, "/view/dashboard.fxml"); }
    @FXML public void handleEtudiants(ActionEvent event) { /* Déjà sur cette vue */ }
    @FXML public void handleLogements(ActionEvent event) { changeScene(event, "/view/logements.fxml"); }
    @FXML public void handleLogout(ActionEvent event) { changeScene(event, "/view/login.fxml"); }

    private void changeScene(ActionEvent event, String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger la page : " + fxmlPath);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}