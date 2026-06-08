package org.example.controller;

import java.util.List;
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

    private final AffectationDAO affectationDAO = new AffectationDAO();
    private final RoomDAO roomDAO = new RoomDAO();

    @FXML
    public void initialize() {
        // 1. Lier les colonnes aux propriétés du modèle Room
        colChambre.setCellValueFactory(new PropertyValueFactory<>("numeroRoom"));
        colType.setCellValueFactory(new PropertyValueFactory<>("typeRoom"));
        colCapacite.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        colLoyer.setCellValueFactory(new PropertyValueFactory<>("loyer"));
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statutRoom"));

        // 2. Ajouter un écouteur pour la Fiche d'Information
        tableChambres.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                infoCapacite.setText("Capacité : " + newVal.getCapaciterRoom() + " pers.");
                infoLoyer.setText("Loyer : " + newVal.getLoyer() + " €/mois");
                infoStatut.setText("Statut : " + newVal.getStatutRoom());
            }
        });

        int studentId = AuthService.getCurrentUser().getIdUsers();
        if (affectationDAO.hasActiveReservation(studentId)) {
            tableChambres.setVisible(false);
        } else {
            loadAvailableRooms();
        }
    }

    private void loadAvailableRooms() {
        List<Room> rooms = roomDAO.getAvailableRooms();
        System.out.println("Chambres trouvées : " + rooms.size());
        tableChambres.getItems().setAll(rooms);
    }

    @FXML
    public void handleReservation(ActionEvent event) {
        Room selected = tableChambres.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une chambre.");
            return;
        }

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
                processReservation(selected, dateEntree.getValue().toString(), dateSortie.getValue().toString());
            }
            return null;
        });
        dialog.showAndWait();
    }

    private void processReservation(Room room, String debut, String fin) {
        int studentId = AuthService.getCurrentUser().getIdUsers();
        Student student = new Student();
        student.setIdStudent(studentId);

        Affectation aff = new Affectation(null, student, room, debut, fin);
        affectationDAO.createAffectation(aff);

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