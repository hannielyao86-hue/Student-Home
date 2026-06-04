package org.example.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.dao.AffectationDAO;
import org.example.dao.RoomDAO;
import org.example.dao.StudentDAO;
import org.example.model.Affectation;
import org.example.model.Room;
import org.example.model.Student;
import org.example.model.User;
import org.example.service.AuthService;

import java.time.LocalDate;
import java.util.List;

public class StudentReservationController {

    @FXML private TableView<Room> availableRoomsTable;
    @FXML private TableColumn<Room, String> numberColumn;
    @FXML private TableColumn<Room, String> typeColumn;
    @FXML private TableColumn<Room, Integer> capacityColumn;
    @FXML private TableColumn<Room, String> priceColumn;
    @FXML private TableColumn<Room, String> statusColumn;
    @FXML private Button reserveButton;
    @FXML private Label infoLabel;

    private final RoomDAO roomDAO = new RoomDAO();
    private final StudentDAO studentDAO = new StudentDAO();
    private final AffectationDAO affectationDAO = new AffectationDAO();
    private Room selectedRoom;

    @FXML
    public void initialize() {
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("numeroRoom"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("typeRoom"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capaciterRoom"));
        priceColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(String.format("%.2f €", cell.getValue().getLoyer())));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("statutRoom"));

        reserveButton.setDisable(true);
        availableRoomsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedRoom = newSelection;
            reserveButton.setDisable(newSelection == null);
        });

        loadAvailableRooms();
    }

    private void loadAvailableRooms() {
        List<Room> availableRooms = roomDAO.getAvailableRooms();
        availableRoomsTable.getItems().setAll(availableRooms);

        if (availableRooms.isEmpty()) {
            infoLabel.setText("Aucune chambre disponible pour le moment. Vérifiez ultérieurement.");
        } else {
            infoLabel.setText("Sélectionnez une chambre libre dans la liste, puis cliquez sur Réserver.");
        }
    }

    @FXML
    public void handleReserve() {
        if (selectedRoom == null) {
            showAlert("Aucune sélection", "Veuillez sélectionner une chambre à réserver.", Alert.AlertType.WARNING);
            return;
        }

        User currentUser = AuthService.getCurrentUser();
        if (currentUser == null) {
            showAlert("Session expirée", "Impossible de trouver l'utilisateur connecté.", Alert.AlertType.ERROR);
            return;
        }

        Student student = studentDAO.getStudentByUserId(currentUser.getIdUsers());
        if (student == null) {
            showAlert("Profil incomplet", "Votre profil étudiant n'est pas encore lié à cette session.", Alert.AlertType.ERROR);
            return;
        }

        Affectation affectation = new Affectation(student, selectedRoom, LocalDate.now().toString());
        affectationDAO.createAffectation(affectation);

        showAlert("Réservation envoyée", "Votre demande de réservation pour la chambre " + selectedRoom.getNumeroRoom() + " a été prise en compte.", Alert.AlertType.INFORMATION);
        loadAvailableRooms();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
