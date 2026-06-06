package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import org.example.dao.AffectationDAO;
import org.example.dao.RoomDAO;
import org.example.model.Affectation;
import org.example.model.Room;
import org.example.model.Student;
import org.example.service.AuthService;

import java.time.LocalDate;

public class ReservationEtudiantController {

    @FXML private TableView<Room> tableChambres;
    @FXML private TableColumn<Room, String> colChambre, colType, colStatut;
    @FXML private TableColumn<Room, Integer> colCapacite;
    @FXML private TableColumn<Room, Double> colLoyer;
    @FXML private Label infoCapacite, infoLoyer, infoStatut;

    @FXML
    public void initialize() {
        colChambre.setCellValueFactory(new PropertyValueFactory<>("numeroRoom"));
        colType.setCellValueFactory(new PropertyValueFactory<>("typeRoom"));
        colCapacite.setCellValueFactory(new PropertyValueFactory<>("capaciterRoom"));
        colLoyer.setCellValueFactory(new PropertyValueFactory<>("loyer"));
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statutRoom"));

        int studentId = AuthService.getCurrentUser().getIdUsers();

        if (new AffectationDAO().hasActiveReservation(studentId)) {
            tableChambres.setPlaceholder(new Label("Vous avez déjà une réservation en cours."));
            tableChambres.setDisable(true);
        } else {
            loadAvailableRooms();
        }

        tableChambres.getSelectionModel().selectedItemProperty().addListener((obs, old, n) -> {
            if (n != null) {
                infoCapacite.setText("Capacité : " + n.getCapaciterRoom());
                infoLoyer.setText("Loyer : " + n.getLoyer() + " €/mois");
                infoStatut.setText("Statut : " + n.getStatutRoom());
            }
        });
    }

    private void loadAvailableRooms() {
        tableChambres.getItems().setAll(new RoomDAO().getAvailableRooms());
    }

    @FXML
    public void handleReservation(ActionEvent event) {
        Room selected = tableChambres.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une chambre.");
            return;
        }

        // Fenêtre de saisie des dates
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Dates de séjour");
        dialog.setHeaderText("Chambre : " + selected.getNumeroRoom());

        DatePicker dateEntree = new DatePicker(LocalDate.now());
        DatePicker dateSortie = new DatePicker(LocalDate.now().plusMonths(1));

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.add(new Label("Date d'entrée:"), 0, 0); grid.add(dateEntree, 1, 0);
        grid.add(new Label("Date de sortie:"), 0, 1); grid.add(dateSortie, 1, 1);
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                if (dateEntree.getValue().isAfter(dateSortie.getValue())) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "La date de sortie doit être après l'entrée.");
                } else {
                    processReservation(selected, dateEntree.getValue().toString(), dateSortie.getValue().toString());
                }
            }
            return null;
        });
        dialog.showAndWait();
    }

    private void processReservation(Room room, String debut, String fin) {
        int studentId = AuthService.getCurrentUser().getIdUsers();

        Student student = new Student();
        student.setIdStudent(studentId);

        // MODIFIE CETTE LIGNE : Ajoute 'null' au début
        Affectation aff = new Affectation(null, student, room, debut, fin);

        new AffectationDAO().createAffectation(aff);
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Votre demande a été envoyée !");
        loadAvailableRooms();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}