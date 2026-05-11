package org.example.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.example.service.AuthService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LogementsController {

    @FXML
    private Label nomUserLabel;
    @FXML
    private Label roleLabel;

    @FXML
    private Label roomTitleLabel;
    @FXML
    private Label roomResidentLabel;
    @FXML
    private Label roomNomLabel;
    @FXML
    private Label roomContactLabel;
    @FXML
    private Label contractStartLabel;
    @FXML
    private Label contractEndLabel;
    @FXML
    private Label leaseAmountLabel;
    @FXML
    private Label roomStatusLabel;

    private final Map<String, RoomInfo> roomDetails = new HashMap<>();

    @FXML
    public void initialize() {
        System.out.println("→ Initialisation LogementsController");
        try {
            if (AuthService.getCurrentUser() != null) {
                nomUserLabel.setText(AuthService.getCurrentUser().getPrenomUser() + " " + AuthService.getCurrentUser().getNomUsers());
                roleLabel.setText("Rôle ID : " + AuthService.getCurrentUser().getRoleId());
            } else {
                nomUserLabel.setText("Test User");
                roleLabel.setText("Rôle ID : 1");
            }
            initializeRoomDetails();
            updateRoomDetail(roomDetails.get("A101"));
            System.out.println("✅ LogementsController initialisé avec succès");
        } catch (Exception e) {
            System.out.println("❌ Erreur initialisation LogementsController : " + e.getMessage());
            nomUserLabel.setText("Utilisateur inconnu");
            roleLabel.setText("");
        }
    }

    private void initializeRoomDetails() {
        roomDetails.put("A101", new RoomInfo("A101", "Occupé", "Nohe d'ahandrove", "(019) 904 3776", "19/02/2024", "03/12/2024", "3000 €", "Payé", "#27ae60"));
        roomDetails.put("A102", new RoomInfo("A102", "Libre", "Aucun résident", "--", "--", "--", "0 €", "Libre", "#2ecc71"));
        roomDetails.put("A103", new RoomInfo("A103", "Réservé", "Réservation en attente", "--", "--", "--", "0 €", "Réservé", "#f1c40f"));
        roomDetails.put("A104", new RoomInfo("A104", "Libre", "Aucun résident", "--", "--", "--", "0 €", "Libre", "#2ecc71"));
        roomDetails.put("A105", new RoomInfo("A105", "Occupé", "Marcien Rannemana", "(020) 333 2211", "12/01/2024", "12/01/2025", "3200 €", "En cours", "#e74c3c"));

        roomDetails.put("B101", new RoomInfo("B101", "Libre", "Aucun résident", "--", "--", "--", "0 €", "Libre", "#2ecc71"));
        roomDetails.put("B102", new RoomInfo("B102", "Occupé", "Lala Rakoto", "(021) 112 4455", "05/03/2024", "05/03/2025", "2800 €", "Payé", "#27ae60"));
        roomDetails.put("B103", new RoomInfo("B103", "Réservé", "Réservation en cours", "--", "20/05/2024", "20/11/2024", "2900 €", "Réservé", "#f1c40f"));
        roomDetails.put("B104", new RoomInfo("B104", "Libre", "Aucun résident", "--", "--", "--", "0 €", "Libre", "#2ecc71"));
        roomDetails.put("B105", new RoomInfo("B105", "Occupé", "Fanja Ramaroson", "(022) 998 7744", "10/09/2023", "10/09/2024", "3100 €", "En cours", "#e74c3c"));

        roomDetails.put("C101", new RoomInfo("C101", "Réservé", "Réservation en attente", "--", "01/06/2024", "30/11/2024", "2700 €", "Réservé", "#f1c40f"));
        roomDetails.put("C102", new RoomInfo("C102", "Libre", "Aucun résident", "--", "--", "--", "0 €", "Libre", "#2ecc71"));
        roomDetails.put("C103", new RoomInfo("C103", "Occupé", "Aina Ravelonarivo", "(023) 556 8899", "15/02/2024", "15/02/2025", "3300 €", "Payé", "#27ae60"));
        roomDetails.put("C104", new RoomInfo("C104", "Libre", "Aucun résident", "--", "--", "--", "0 €", "Libre", "#2ecc71"));
        roomDetails.put("C105", new RoomInfo("C105", "Réservé", "Réservation confirmée", "--", "07/04/2024", "07/10/2024", "2950 €", "Réservé", "#f1c40f"));

        roomDetails.put("D101", new RoomInfo("D101", "Occupé", "Miora Rasolofonirina", "(024) 334 6677", "18/01/2024", "18/01/2025", "3400 €", "En cours", "#e74c3c"));
        roomDetails.put("D102", new RoomInfo("D102", "Libre", "Aucun résident", "--", "--", "--", "0 €", "Libre", "#2ecc71"));
        roomDetails.put("D103", new RoomInfo("D103", "Réservé", "Réservation en attente", "--", "22/07/2024", "22/01/2025", "2850 €", "Réservé", "#f1c40f"));
        roomDetails.put("D104", new RoomInfo("D104", "Occupé", "Tiana Rakotomalala", "(025) 778 9900", "02/12/2023", "02/12/2024", "3050 €", "Payé", "#27ae60"));
        roomDetails.put("D105", new RoomInfo("D105", "Libre", "Aucun résident", "--", "--", "--", "0 €", "Libre", "#2ecc71"));
    }

    @FXML
    public void handleDashboard(ActionEvent event) {
        loadPage("/view/dashboard.fxml");
    }

    @FXML
    public void handleEtudiants(ActionEvent event) {
        System.out.println("→ Module Étudiants (Sprint 2)");
    }

    @FXML
    public void handleLogements(ActionEvent event) {
        System.out.println("Vous êtes déjà sur la page Logements.");
    }

    @FXML
    public void handlePaiements(ActionEvent event) {
        System.out.println("→ Module Paiements (Sprint 2)");
    }

    @FXML
    public void handleIncidents(ActionEvent event) {
        System.out.println("→ Module Incidents (Sprint 2)");
    }

    @FXML
    public void handleLogout(ActionEvent event) {
        try {
            new AuthService().logout();
            loadPage("/view/login.fxml");
        } catch (Exception e) {
            System.out.println("❌ Erreur lors du logout : " + e.getMessage());
        }
    }

    @FXML
    public void handleRoomClick(ActionEvent event) {
        Button source = (Button) event.getSource();
        String roomNumber = extractRoomNumber(source);
        System.out.println("→ Chambre sélectionnée : " + roomNumber);
        RoomInfo info = roomDetails.getOrDefault(roomNumber, createDefaultRoom(roomNumber));
        updateRoomDetail(info);
    }

    private String extractRoomNumber(Button button) {
        if (button.getGraphic() != null && button.getGraphic() instanceof javafx.scene.layout.VBox) {
            javafx.scene.layout.VBox graphicBox = (javafx.scene.layout.VBox) button.getGraphic();
            if (graphicBox.getChildren().size() > 1 && graphicBox.getChildren().get(1) instanceof Label) {
                return ((Label) graphicBox.getChildren().get(1)).getText();
            }
        }
        return button.getText();
    }

    private void updateRoomDetail(RoomInfo info) {
        roomTitleLabel.setText(info.roomNumber());
        roomResidentLabel.setText(info.status());
        roomNomLabel.setText(info.residentName());
        roomContactLabel.setText(info.contact());
        contractStartLabel.setText(info.contractStart());
        contractEndLabel.setText(info.contractEnd());
        leaseAmountLabel.setText(info.leaseAmount());
        roomStatusLabel.setText(info.rentStatus());
        roomStatusLabel.setStyle("-fx-padding: 8 14; -fx-background-radius: 999; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: " + info.statusColor() + ";");
    }

    private RoomInfo createDefaultRoom(String roomNumber) {
        return new RoomInfo(roomNumber, "Indisponible", "Aucun résident", "--", "--", "--", "0 €", "N/A", "#7f8c8d");
    }

    @FXML
    public void handleModifier(ActionEvent event) {
        System.out.println("→ Modifier la chambre");
    }

    @FXML
    public void handleGenererContrat(ActionEvent event) {
        System.out.println("→ Générer le contrat");
    }

    private void loadPage(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Stage stage = (Stage) nomUserLabel.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setMaximized(true);
        } catch (IOException e) {
            System.out.println("❌ Erreur chargement page : " + e.getMessage());
        }
    }

    private static record RoomInfo(
            String roomNumber,
            String status,
            String residentName,
            String contact,
            String contractStart,
            String contractEnd,
            String leaseAmount,
            String rentStatus,
            String statusColor) {
    }
}
