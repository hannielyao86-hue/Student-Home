package org.example.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import org.example.dao.StudentDAO;
import org.example.model.Student;

import java.time.LocalDate;

public class StudentController {

    // ═══════════════════════════════════════════════════════════════
    // TABLEVIEW
    // ═══════════════════════════════════════════════════════════════

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, Integer> idColumn;

    @FXML
    private TableColumn<Student, String> nomColumn;

    @FXML
    private TableColumn<Student, String> prenomColumn;

    @FXML
    private TableColumn<Student, String> emailColumn;

    @FXML
    private TableColumn<Student, String> ecoleColumn;

    @FXML
    private TableColumn<Student, String> telephoneColumn;

    @FXML
    private TableColumn<Student, String> dateEntreeColumn;

    @FXML
    private TableColumn<Student, String> dateSortieColumn;

    // ═══════════════════════════════════════════════════════════════
    // FORMULAIRE
    // ═══════════════════════════════════════════════════════════════

    @FXML
    private TextField idField;

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField ecoleField;

    @FXML
    private TextField telephoneField;

    @FXML
    private DatePicker dateEntreePicker;

    @FXML
    private DatePicker dateSortiePicker;

    // ═══════════════════════════════════════════════════════════════
    // DAO
    // ═══════════════════════════════════════════════════════════════

    private final StudentDAO dao = new StudentDAO();

    // ═══════════════════════════════════════════════════════════════
    // INITIALIZE
    // ═══════════════════════════════════════════════════════════════

    @FXML
    public void initialize() {

        System.out.println("→ Initialisation StudentController");

        // ================= TABLE COLUMNS =================

        idColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(
                        data.getValue().getIdStudent()
                ).asObject()
        );

        nomColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getNom()
                )
        );

        prenomColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getPrenom()
                )
        );

        emailColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getEmail()
                )
        );

        ecoleColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getEcole()
                )
        );

        telephoneColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getTelephone()
                )
        );

        dateEntreeColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getDateEntree()
                )
        );

        dateSortieColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getDateSortie()
                )
        );

        // ================= LOAD STUDENTS =================

        loadStudents();

        // ================= TABLE SELECTION =================

        studentTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldValue, newValue) -> {

                    if (newValue != null) {

                        idField.setText(
                                String.valueOf(newValue.getIdStudent())
                        );

                        nomField.setText(
                                newValue.getNom()
                        );

                        prenomField.setText(
                                newValue.getPrenom()
                        );

                        emailField.setText(
                                newValue.getEmail()
                        );

                        ecoleField.setText(
                                newValue.getEcole()
                        );

                        telephoneField.setText(
                                newValue.getTelephone()
                        );

                        // DATE ENTREE

                        if (newValue.getDateEntree() != null &&
                                !newValue.getDateEntree().isEmpty()) {

                            dateEntreePicker.setValue(
                                    LocalDate.parse(
                                            newValue.getDateEntree()
                                    )
                            );

                        } else {

                            dateEntreePicker.setValue(null);
                        }

                        // DATE SORTIE

                        if (newValue.getDateSortie() != null &&
                                !newValue.getDateSortie().isEmpty()) {

                            dateSortiePicker.setValue(
                                    LocalDate.parse(
                                            newValue.getDateSortie()
                                    )
                            );

                        } else {

                            dateSortiePicker.setValue(null);
                        }
                    }
                });

        System.out.println("✅ StudentController chargé");
    }

    // ═══════════════════════════════════════════════════════════════
    // LOAD STUDENTS
    // ═══════════════════════════════════════════════════════════════

    @FXML
    public void loadStudents() {

        studentTable.getItems().setAll(
                dao.getAllStudents()
        );
    }

    // ═══════════════════════════════════════════════════════════════
    // ADD STUDENT
    // ═══════════════════════════════════════════════════════════════

    @FXML
    public void handleAddStudent() {

        try {

            Student student = new Student(
                    0,
                    nomField.getText(),
                    prenomField.getText(),
                    emailField.getText(),
                    ecoleField.getText(),
                    telephoneField.getText(),

                    dateEntreePicker.getValue() != null
                            ? dateEntreePicker.getValue().toString()
                            : null,

                    dateSortiePicker.getValue() != null
                            ? dateSortiePicker.getValue().toString()
                            : null,

                    1
            );

            dao.createStudent(student);

            loadStudents();

            clearFields();

            System.out.println("✅ Étudiant ajouté");

        } catch (Exception e) {

            System.out.println("❌ Erreur ajout étudiant : " + e.getMessage());

            e.printStackTrace();
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // UPDATE STUDENT
    // ═══════════════════════════════════════════════════════════════

    @FXML
    public void handleUpdateStudent() {

        try {

            Student student = new Student(
                    Integer.parseInt(idField.getText()),

                    nomField.getText(),
                    prenomField.getText(),
                    emailField.getText(),
                    ecoleField.getText(),
                    telephoneField.getText(),

                    dateEntreePicker.getValue() != null
                            ? dateEntreePicker.getValue().toString()
                            : null,

                    dateSortiePicker.getValue() != null
                            ? dateSortiePicker.getValue().toString()
                            : null,

                    1
            );

            dao.updateStudent(student);

            loadStudents();

            clearFields();

            System.out.println("✅ Étudiant modifié");

        } catch (Exception e) {

            System.out.println("❌ Erreur modification : " + e.getMessage());

            e.printStackTrace();
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // DELETE STUDENT
    // ═══════════════════════════════════════════════════════════════

    @FXML
    public void handleDeleteStudent() {

        try {

            int id = Integer.parseInt(
                    idField.getText()
            );

            dao.deleteStudent(id);

            loadStudents();

            clearFields();

            System.out.println("✅ Étudiant supprimé");

        } catch (Exception e) {

            System.out.println("❌ Erreur suppression : " + e.getMessage());

            e.printStackTrace();
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // CLEAR FIELDS
    // ═══════════════════════════════════════════════════════════════

    private void clearFields() {

        idField.clear();

        nomField.clear();

        prenomField.clear();

        emailField.clear();

        ecoleField.clear();

        telephoneField.clear();

        dateEntreePicker.setValue(null);

        dateSortiePicker.setValue(null);
    }

    // ═══════════════════════════════════════════════════════════════
    // DASHBOARD
    // ═══════════════════════════════════════════════════════════════

    @FXML
    public void handleDashboard() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/dashboard.fxml")
            );

            Stage stage = (Stage)
                    studentTable.getScene().getWindow();

            stage.setScene(
                    new Scene(loader.load())
            );

            stage.setMaximized(true);

        } catch (Exception e) {

            System.out.println("❌ Erreur dashboard : " + e.getMessage());

            e.printStackTrace();
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // LOGOUT
    // ═══════════════════════════════════════════════════════════════

    @FXML
    public void handleLogout() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/login.fxml")
            );

            Stage stage = (Stage)
                    studentTable.getScene().getWindow();

            stage.setScene(
                    new Scene(loader.load())
            );

            stage.setMaximized(true);

        } catch (Exception e) {

            System.out.println("❌ Erreur logout : " + e.getMessage());

            e.printStackTrace();
        }
    }
}