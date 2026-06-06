package org.example.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.example.dao.AffectationDAO;
import org.example.dao.ContractDAO;
import org.example.dao.RoomDAO;
import org.example.dao.StudentDAO;
import org.example.model.Affectation;
import org.example.model.Contract;
import org.example.model.Room;
import org.example.model.Student;
import org.example.service.AuthService;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

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
    @FXML
    private TextField searchField;
    @FXML
    private FlowPane roomsFlowPane;

    private final Map<String, RoomInfo> roomDetails = new HashMap<>();
    private final List<Room> allRooms = new ArrayList<>();
    private Room selectedRoom;

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
            if (searchField != null) {
                searchField.textProperty().addListener((obs, oldValue, newValue) -> filterRooms(newValue));
            }
            if (!roomDetails.isEmpty()) {
                String firstRoom = roomDetails.keySet().iterator().next();
                updateRoomDetail(roomDetails.get(firstRoom));
            } else {
                updateRoomDetail(createDefaultRoom("Aucune chambre"));
            }
            System.out.println("✅ LogementsController initialisé avec succès");
        } catch (Exception e) {
            System.out.println("❌ Erreur initialisation LogementsController : " + e.getMessage());
            nomUserLabel.setText("Utilisateur inconnu");
            roleLabel.setText("");
        }
    }

    private void initializeRoomDetails() {
        if (roomsFlowPane != null) {
            roomsFlowPane.getChildren().clear();
        }
        roomDetails.clear();
        allRooms.clear();
        selectedRoom = null;

        RoomDAO roomDAO = new RoomDAO();
        List<Room> rooms = roomDAO.getAllRooms();

        if (rooms.isEmpty()) {
            System.out.println("⚠️ Aucune chambre trouvée en base de données. Verifie la table rooms.");
            return;
        }

        allRooms.addAll(rooms);
        for (Room room : rooms) {
            allRooms.add(room);
            RoomInfo info = createRoomInfoFromRoom(room);
            roomDetails.put(room.getNumeroRoom(), info);
            createRoomCard(room, info);
        }
    }

    private void filterRooms(String query) {
        if (roomsFlowPane == null) {
            return;
        }
        roomsFlowPane.getChildren().clear();
        if (query == null || query.isBlank()) {
            allRooms.forEach(room -> createRoomCard(room, createRoomInfoFromRoom(room)));
            return;
        }

        String normalized = query.trim().toUpperCase();
        allRooms.stream()
                .filter(room -> room.getNumeroRoom().toUpperCase().contains(normalized)
                        || room.getTypeRoom().toUpperCase().contains(normalized)
                        || (room.getStatutRoom() != null && room.getStatutRoom().toUpperCase().contains(normalized))
                        || String.valueOf(room.getLoyer()).contains(normalized))
                .forEach(room -> createRoomCard(room, createRoomInfoFromRoom(room)));
    }

    private void createRoomCard(Room room, RoomInfo info) {
        String statusColor = getStatusColor(room.getStatutRoom());

        Button roomButton = new Button();
        roomButton.setPrefWidth(220);
        roomButton.setPrefHeight(160);
        roomButton.setStyle(
                "-fx-background-color: " + statusColor + ";" +
                " -fx-background-radius: 18;" +
                " -fx-text-fill: white;" +
                " -fx-padding: 0;"
        );
        roomButton.setUserData(room);
        roomButton.setOnAction(this::handleRoomClick);

        Label status = new Label(info.status());
        status.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

        Label roomNumber = new Label(room.getNumeroRoom());
        roomNumber.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");

        Label roomType = new Label(room.getTypeRoom());
        roomType.setStyle("-fx-font-size: 13px;");

        Label rentLabel = new Label(info.leaseAmount());
        rentLabel.setStyle("-fx-font-size: 12px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox topRow = new HBox(8, status, spacer, rentLabel);
        topRow.setAlignment(Pos.CENTER_LEFT);

        VBox content = new VBox(10, topRow, roomNumber, roomType);
        content.setAlignment(Pos.TOP_LEFT);
        content.setStyle("-fx-padding: 18;");

        roomButton.setGraphic(content);
        if (roomsFlowPane != null) {
            roomsFlowPane.getChildren().add(roomButton);
        }
    }

    private String getStatusColor(String statut) {
        if (statut == null) {
            return "#7f8c8d";
        }
        String normalized = statut.trim().toUpperCase();
        if (normalized.contains("LIBRE")) {
            return "#27ae60"; // vert uniforme
        }
        if (normalized.contains("OCCUPE")) {
            return "#c0392b"; // rouge vif uniforme
        }
        if (normalized.contains("RESERVE") || normalized.contains("RÉSERVÉ") || normalized.contains("RESERVÉ")) {
            return "#f39c12"; // jaune orangé uniforme
        }
        return "#7f8c8d";
    }

    private RoomInfo createRoomInfoFromRoom(Room room) {
        String statut = room.getStatutRoom() == null ? "Indisponible" : room.getStatutRoom().trim();
        String statusLabel;
        String statusColor;

        if (statut.equalsIgnoreCase("LIBRE")) {
            statusLabel = "Libre";
            statusColor = "#27ae60";
        } else if (statut.equalsIgnoreCase("OCCUPEE") || statut.equalsIgnoreCase("OCCUPE")) {
            statusLabel = "Occupé";
            statusColor = "#c0392b";
        } else if (statut.equalsIgnoreCase("RESERVE") || statut.equalsIgnoreCase("RÉSERVÉ") || statut.equalsIgnoreCase("RESERVÉ")) {
            statusLabel = "Réservé";
            statusColor = "#f39c12";
        } else {
            statusLabel = statut;
            statusColor = "#7f8c8d";
        }

        String contractStart = "--";
        String contractEnd = "--";
        String residentName = "Aucun résident";
        String contact = "--";

        try {
            ContractDAO contractDAO = new ContractDAO();
            org.example.model.Contract contract = contractDAO.getLatestContractByRoom(room.getIdRoom());
            if (contract != null) {
                contractStart = contract.getStartDate() != null ? contract.getStartDate() : "--";
                contractEnd = contract.getEndDate() != null ? contract.getEndDate() : "--";

                int studentId = contract.getIdStudent();
                if (studentId > 0) {
                    Student s = new StudentDAO().getStudentById(studentId);
                    if (s != null) {
                        residentName = s.getNom() + " " + s.getPrenom();
                        contact = s.getEmail() + " / " + s.getTelephone();
                    }
                }
            } else {
                // Pas de contrat : chercher la dernière affectation pour afficher l'étudiant
                Integer lastStudentId = new AffectationDAO().getLatestAffectationStudentId(room.getIdRoom());
                if (lastStudentId != null && lastStudentId > 0) {
                    Student s = new StudentDAO().getStudentById(lastStudentId);
                    if (s != null) {
                        residentName = s.getNom() + " " + s.getPrenom();
                        contact = s.getEmail() + " / " + s.getTelephone();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lecture contrat/chambre: " + e.getMessage());
        }

        return new RoomInfo(
                room.getNumeroRoom(),
                statusLabel,
                residentName,
                contact,
                contractStart,
                contractEnd,
                String.format("%.2f €", room.getLoyer()),
                statusLabel,
                statusColor
        );
    }

    @FXML
    public void handleEtudiants(ActionEvent event) {
        loadPage("/view/students.fxml");
    }

    @FXML
    public void handleAjouterRoom() {
        showAddRoomDialog();
    }
    @FXML
    public void handlePaiements(ActionEvent event) {
        loadPage("/view/paiements.fxml");
    }

    @FXML
    public void handleIncidents(ActionEvent event) {
        loadPage("/view/Incident.fxml");
    }

    @FXML
    public void handleDashboard(ActionEvent event) {
        loadPage("/view/dashboard.fxml");
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
        Object userData = source.getUserData();
        if (userData instanceof Room room) {
            selectedRoom = room;
        } else {
            String roomNumber = extractRoomNumber(source);
            selectedRoom = allRooms.stream()
                    .filter(r -> r.getNumeroRoom().equals(roomNumber))
                    .findFirst()
                    .orElse(null);
        }
        String roomNumber = selectedRoom != null ? selectedRoom.getNumeroRoom() : extractRoomNumber(source);
        System.out.println("→ Chambre sélectionnée : " + roomNumber);
        RoomInfo info = roomDetails.getOrDefault(roomNumber, createDefaultRoom(roomNumber));
        updateRoomDetail(info);
    }

    private String extractRoomNumber(Button button) {
        Object userData = button.getUserData();
        if (userData instanceof Room room) {
            return room.getNumeroRoom();
        }
        if (userData instanceof String roomNumber) {
            return roomNumber;
        }

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

    private void showAddRoomDialog() {
        Dialog<Room> dialog = new Dialog<>();
        dialog.setTitle("Ajouter une chambre");
        dialog.setHeaderText("Enregistre une nouvelle chambre dans la base de données");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField numeroField = new TextField();
        numeroField.setPromptText("A106");
        TextField typeField = new TextField();
        typeField.setPromptText("SIMPLE / DOUBLE");
        TextField capaciteField = new TextField();
        capaciteField.setPromptText("1");
        TextField statutField = new TextField("LIBRE");
        statutField.setPromptText("LIBRE / OCCUPEE / RESERVE");
        TextField loyerField = new TextField();
        loyerField.setPromptText("2500.00");

        grid.add(new Label("Numéro de chambre:"), 0, 0);
        grid.add(numeroField, 1, 0);
        grid.add(new Label("Type de chambre:"), 0, 1);
        grid.add(typeField, 1, 1);
        grid.add(new Label("Capacité:"), 0, 2);
        grid.add(capaciteField, 1, 2);
        grid.add(new Label("Statut:"), 0, 3);
        grid.add(statutField, 1, 3);
        grid.add(new Label("Loyer:"), 0, 4);
        grid.add(loyerField, 1, 4);

        Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);

        Runnable validateInputs = () -> {
            String numero = numeroField.getText().trim();
            String type = typeField.getText().trim();
            String capacite = capaciteField.getText().trim();
            String loyer = loyerField.getText().trim();
            boolean valid = !numero.isEmpty() && !type.isEmpty() && isInteger(capacite) && isDouble(loyer);
            okButton.setDisable(!valid);
        };

        numeroField.textProperty().addListener((obs, oldValue, newValue) -> validateInputs.run());
        typeField.textProperty().addListener((obs, oldValue, newValue) -> validateInputs.run());
        capaciteField.textProperty().addListener((obs, oldValue, newValue) -> validateInputs.run());
        loyerField.textProperty().addListener((obs, oldValue, newValue) -> validateInputs.run());

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(numeroField::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    int capacite = Integer.parseInt(capaciteField.getText().trim());
                    double loyer = Double.parseDouble(loyerField.getText().trim());
                    String numero = numeroField.getText().trim().toUpperCase();
                    String type = typeField.getText().trim().toUpperCase();
                    String statut = statutField.getText().trim().toUpperCase();
                    if (statut.isEmpty()) {
                        statut = "LIBRE";
                    }
                    return new Room(0, numero, type, capacite, statut, loyer);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return null;
        });

        Optional<Room> result = dialog.showAndWait();

        result.ifPresent(room -> {
            RoomDAO roomDAO = new RoomDAO();
            if (roomDAO.createRoom(room)) {
                initializeRoomDetails();
                updateRoomDetail(roomDetails.get(room.getNumeroRoom()));
                showAlert(Alert.AlertType.INFORMATION, "Chambre ajoutée", "La chambre a bien été enregistrée en base de données.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'enregistrer la chambre en base de données.");
            }
        });
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean isInteger(String value) {
        return value != null && !value.isEmpty() && value.matches("\\d+");
    }

    private boolean isDouble(String value) {
        return value != null && !value.isEmpty() && value.matches("\\d+(\\.\\d+)?");
    }

    @FXML
    public void handleModifier(ActionEvent event) {
        if (selectedRoom == null) {
            showAlert(Alert.AlertType.WARNING, "Aucune chambre", "Veuillez sélectionner une chambre.");
            return;
        }

        Dialog<Room> dialog = new Dialog<>();
        dialog.setTitle("Modifier chambre " + selectedRoom.getNumeroRoom());
        dialog.setHeaderText("Modifiez le statut, la date d'entrée et la date de sortie (contrat)");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ComboBox<String> statusCombo = new ComboBox<>(FXCollections.observableArrayList("LIBRE", "OCCUPEE", "RESERVE"));
        statusCombo.setValue(selectedRoom.getStatutRoom() != null ? selectedRoom.getStatutRoom() : "LIBRE");

        DatePicker startDatePicker = new DatePicker();
        DatePicker endDatePicker = new DatePicker();

        grid.add(new Label("Statut:"), 0, 0);
        grid.add(statusCombo, 1, 0);
        grid.add(new Label("Date entrée (contrat):"), 0, 1);
        grid.add(startDatePicker, 1, 1);
        grid.add(new Label("Date sortie (contrat):"), 0, 2);
        grid.add(endDatePicker, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                String newStatus = statusCombo.getValue();

                selectedRoom.setStatutRoom(newStatus);

                // Mettre à jour la chambre
                RoomDAO roomDAO = new RoomDAO();
                boolean updated = roomDAO.updateRoom(selectedRoom);

                // Créer un contrat si des dates sont fournies
                if (startDatePicker.getValue() != null || endDatePicker.getValue() != null) {
                    String idContract = "CTR" + System.currentTimeMillis();
                    String start = startDatePicker.getValue() != null ? startDatePicker.getValue().toString() : "";
                    String end = endDatePicker.getValue() != null ? endDatePicker.getValue().toString() : "";

                    Contract contract = new Contract(
                            idContract,
                            start,
                            end,
                            0.0,
                            newStatus,
                            "NONE",
                            selectedRoom.getIdRoom(),
                            0
                    );
                    new ContractDAO().createContract(contract);
                }

                if (updated) {
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Chambre mise à jour avec succès.");
                    initializeRoomDetails();
                    updateRoomDetail(createRoomInfoFromRoom(selectedRoom));
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la mise à jour.");
                }

                return selectedRoom;
            }
            return null;
        });

        dialog.showAndWait();
    }

    @FXML
    public void handleAffecterRoom(ActionEvent event) {
        if (selectedRoom == null) {
            showAlert(Alert.AlertType.WARNING, "Affectation impossible", "Sélectionne d'abord une chambre.");
            return;
        }

        if (!"LIBRE".equalsIgnoreCase(selectedRoom.getStatutRoom())) {
            showAlert(Alert.AlertType.WARNING, "Affectation impossible", "La chambre doit être libre pour être affectée.");
            return;
        }

        List<Student> students = new StudentDAO().getAllStudents();
        if (students.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Aucun étudiant", "Veuillez ajouter au moins un étudiant avant d'affecter une chambre.");
            return;
        }

        Dialog<Affectation> dialog = new Dialog<>();
        dialog.setTitle("Affecter une chambre");
        dialog.setHeaderText("Affecter " + selectedRoom.getNumeroRoom() + " à un étudiant");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        ComboBox<Student> studentCombo = new ComboBox<>(FXCollections.observableArrayList(students));
        studentCombo.setPrefWidth(320);
        studentCombo.setConverter(new StringConverter<>() {
            @Override
            public String toString(Student student) {
                return student == null ? "" : student.getNom() + " " + student.getPrenom() + " (" + student.getIdStudent() + ")";
            }

            @Override
            public Student fromString(String string) {
                return null;
            }
        });
        studentCombo.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Student student, boolean empty) {
                super.updateItem(student, empty);
                setText(empty || student == null ? "" : student.getNom() + " " + student.getPrenom() + " (" + student.getIdStudent() + ")");
            }
        });
        studentCombo.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Student student, boolean empty) {
                super.updateItem(student, empty);
                setText(empty || student == null ? "" : student.getNom() + " " + student.getPrenom() + " (" + student.getIdStudent() + ")");
            }
        });
        studentCombo.getSelectionModel().selectFirst();

        DatePicker datePicker = new DatePicker(LocalDate.now());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.add(new Label("Étudiant:"), 0, 0);
        grid.add(studentCombo, 1, 0);
        grid.add(new Label("Date affectation:"), 0, 1);
        grid.add(datePicker, 1, 1);

        Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(studentCombo.getSelectionModel().getSelectedItem() == null);

        studentCombo.valueProperty().addListener((obs, oldValue, newValue) -> okButton.setDisable(newValue == null));

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            // Remplace ton bloc actuel par celui-ci :
            if (dialogButton == ButtonType.OK) {
                return new Affectation(
                        null, // 1. ID (null car nouvelle affectation)
                        studentCombo.getSelectionModel().getSelectedItem(), // 2. Student
                        selectedRoom, // 3. Room
                        datePicker.getValue() != null ? datePicker.getValue().toString() : LocalDate.now().toString(), // 4. Date Entrée
                        LocalDate.now().plusMonths(1).toString() // 5. Date Sortie (ajoute une valeur par défaut ici)
                );
            }
            return null;
        });

        Optional<Affectation> result = dialog.showAndWait();
        result.ifPresent(affectation -> {
            new AffectationDAO().createAffectation(affectation);
            
            // Recharger juste la chambre affectée sans réinitialiser toutes les cartes
            RoomDAO roomDAO = new RoomDAO();
            List<Room> updatedRooms = roomDAO.getAllRooms();
            int roomId = selectedRoom.getIdRoom();
            selectedRoom = updatedRooms.stream()
                    .filter(r -> r.getIdRoom() == roomId)
                    .findFirst()
                    .orElse(selectedRoom);

            if (selectedRoom != null) {
                RoomInfo updatedInfo = createRoomInfoFromRoom(selectedRoom);
                updateRoomDetail(updatedInfo);
                
                Student student = affectation.getStudent();
                roomNomLabel.setText(student.getNom() + " " + student.getPrenom());
                roomContactLabel.setText(student.getEmail() + " / " + student.getTelephone());
            }
            showAlert(Alert.AlertType.INFORMATION, "Affectation réussie", "La chambre a été affectée à " + affectation.getStudent().getNom() + " " + affectation.getStudent().getPrenom() + ".");
        });
    }

    @FXML
    public void handleGenererContrat(ActionEvent event) {
        if (selectedRoom == null) {
            showAlert(Alert.AlertType.WARNING, "Aucune chambre", "Veuillez sélectionner une chambre.");
            return;
        }

        try {
            Contract contract = new ContractDAO().getLatestContractByRoom(selectedRoom.getIdRoom());
            Student student = null;

            if (contract != null && contract.getIdStudent() > 0) {
                student = new StudentDAO().getStudentById(contract.getIdStudent());
            }

            if (student == null) {
                Integer lastStudentId = new AffectationDAO().getLatestAffectationStudentId(selectedRoom.getIdRoom());
                if (lastStudentId != null && lastStudentId > 0) {
                    student = new StudentDAO().getStudentById(lastStudentId);
                }
            }

            if (student == null) {
                showAlert(Alert.AlertType.INFORMATION, "Aucune information", "Aucun étudiant trouvé pour cette chambre.");
                return;
            }

            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Informations étudiant - " + selectedRoom.getNumeroRoom());
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            grid.add(new Label("ID Étudiant:"), 0, 0);
            grid.add(new Label(String.valueOf(student.getIdStudent())), 1, 0);

            grid.add(new Label("Nom:"), 0, 1);
            grid.add(new Label(student.getNom()), 1, 1);

            grid.add(new Label("Prénom:"), 0, 2);
            grid.add(new Label(student.getPrenom()), 1, 2);

            grid.add(new Label("Email:"), 0, 3);
            grid.add(new Label(student.getEmail() != null ? student.getEmail() : "--"), 1, 3);

            grid.add(new Label("Téléphone:"), 0, 4);
            grid.add(new Label(student.getTelephone() != null ? student.getTelephone() : "--"), 1, 4);

            if (contract != null) {
                grid.add(new Label("Contrat - Début:"), 0, 5);
                grid.add(new Label(contract.getStartDate() != null ? contract.getStartDate() : "--"), 1, 5);

                grid.add(new Label("Contrat - Fin:"), 0, 6);
                grid.add(new Label(contract.getEndDate() != null ? contract.getEndDate() : "--"), 1, 6);
            }

            dialog.getDialogPane().setContent(grid);
            dialog.showAndWait();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de récupérer les informations : " + e.getMessage());
        }
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
