package org.example.controller;

import org.example.dao.StudentDAO;
import org.example.dao.AffectationDAO;
import org.example.model.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.beans.property.*;

public class StudentController {

    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, Integer> idColumn;
    @FXML private TableColumn<Student, String> nomColumn, prenomColumn, emailColumn, ecoleColumn, telephoneColumn, dateEntreeColumn, dateSortieColumn;
    @FXML private TextField idField, nomField, prenomField, emailField, ecoleField, telephoneField;
    @FXML private DatePicker dateEntreePicker, dateSortiePicker;

    private final StudentDAO dao = new StudentDAO();
    private final AffectationDAO affectationDAO = new AffectationDAO();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getIdStudent()).asObject());
        nomColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNom()));
        prenomColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPrenom()));
        emailColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
        ecoleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEcole()));
        telephoneColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTelephone()));
        dateEntreeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDateEntree()));
        dateSortieColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDateSortie()));

        loadStudents();
    }

    @FXML
    public void loadStudents() {
        studentTable.getItems().setAll(dao.getAllStudents());
    }

    // ========================================================
    // GESTION DES ÉTUDIANTS (CRUD)
    // ========================================================

    @FXML
    public void handleAddStudent() {
        try {
            Student student = new Student(0, nomField.getText(), prenomField.getText(),
                    emailField.getText(), ecoleField.getText(),
                    telephoneField.getText(),
                    dateEntreePicker.getValue() != null ? dateEntreePicker.getValue().toString() : null,
                    dateSortiePicker.getValue() != null ? dateSortiePicker.getValue().toString() : null, 1);
            dao.createStudent(student);
            loadStudents();
            clearFields();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    public void handleUpdateStudent() {
        try {
            Student student = new Student(Integer.parseInt(idField.getText()), nomField.getText(), prenomField.getText(),
                    emailField.getText(), ecoleField.getText(), telephoneField.getText(),
                    dateEntreePicker.getValue().toString(), dateSortiePicker.getValue().toString(), 1);
            dao.updateStudent(student);
            loadStudents();
            clearFields();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    public void handleDeleteStudent() {
        try {
            dao.deleteStudent(Integer.parseInt(idField.getText()));
            loadStudents();
            clearFields();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void clearFields() {
        idField.clear(); nomField.clear(); prenomField.clear(); emailField.clear();
        ecoleField.clear(); telephoneField.clear();
        dateEntreePicker.setValue(null); dateSortiePicker.setValue(null);
    }

    // ========================================================
    // GESTION RÉSERVATIONS
    // ========================================================

    @FXML
    public void handleAccepterReservation() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        int affId = affectationDAO.getAffectationIdByStudent(selected.getIdStudent());
        int roomId = affectationDAO.getRoomIdByStudent(selected.getIdStudent());

        if (affId != -1) {
            affectationDAO.accepterReservation(affId, roomId);
            loadStudents();
        }
    }

    // ========================================================
    // NAVIGATION
    // ========================================================

    @FXML public void handleDashboard() { loadPage("/view/dashboard.fxml"); }
    @FXML public void handleEtudiants() { System.out.println("Déjà sur la page"); }
    @FXML public void handleLogements() { loadPage("/view/logements.fxml"); }
    @FXML public void handlePaiements() { loadPage("/view/paiements.fxml"); }
    @FXML public void handleIncidents() { loadPage("/view/Incident.fxml"); }
    @FXML public void handleLogout() { loadPage("/view/login.fxml"); }

    private void loadPage(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Stage stage = (Stage) studentTable.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setMaximized(true);
        } catch (Exception e) { e.printStackTrace(); }
    }
}