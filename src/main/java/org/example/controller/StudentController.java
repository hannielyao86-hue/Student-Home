package org.example.controller;

import org.example.dao.StudentDAO;
import org.example.model.Student;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StudentController {

    @FXML private TableView<Student> studentTable;

    @FXML private TableColumn<Student, Integer> idColumn;
    @FXML private TableColumn<Student, String> numeroColumn;
    @FXML private TableColumn<Student, String> ecoleColumn;
    @FXML private TableColumn<Student, String> telephoneColumn;
    @FXML private TableColumn<Student, String> dateEntreeColumn;
    @FXML private TableColumn<Student, String> dateSortieColumn;

    @FXML private TextField idField;
    @FXML private TextField numeroField;
    @FXML private TextField ecoleField;
    @FXML private TextField telephoneField;

    @FXML private DatePicker dateEntreePicker;
    @FXML private DatePicker dateSortiePicker;

    private final StudentDAO dao = new StudentDAO();

    @FXML
    public void initialize() {

        idColumn.setCellValueFactory(d ->
                new SimpleIntegerProperty(d.getValue().getIdStudent()).asObject());

        numeroColumn.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getNumeroEtudiant()));

        ecoleColumn.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getEcole()));

        telephoneColumn.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getTelephone()));

        dateEntreeColumn.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getDateEntree()));

        dateSortieColumn.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getDateSortie()));

        loadStudents();

        studentTable.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldV, s) -> {

                    if (s == null) return;

                    idField.setText(String.valueOf(s.getIdStudent()));
                    numeroField.setText(s.getNumeroEtudiant());
                    ecoleField.setText(s.getEcole());
                    telephoneField.setText(s.getTelephone());

                    if (s.getDateEntree() != null)
                        dateEntreePicker.setValue(java.time.LocalDate.parse(s.getDateEntree()));
                    else
                        dateEntreePicker.setValue(null);

                    if (s.getDateSortie() != null)
                        dateSortiePicker.setValue(java.time.LocalDate.parse(s.getDateSortie()));
                    else
                        dateSortiePicker.setValue(null);
                });
    }

    // ================= LOAD =================
    @FXML
    public void loadStudents() {
        studentTable.getItems().setAll(dao.getAllStudents());
    }

    // ================= ADD =================
    @FXML
    public void handleAddStudent() {

        Student s = new Student(
                0,
                numeroField.getText(),
                ecoleField.getText(),
                telephoneField.getText(),
                dateEntreePicker.getValue() != null ? dateEntreePicker.getValue().toString() : null,
                dateSortiePicker.getValue() != null ? dateSortiePicker.getValue().toString() : null,
                1 // id_users par défaut
        );

        dao.createStudent(s);
        loadStudents();
        clear();
    }

    // ================= UPDATE =================
    @FXML
    public void handleUpdateStudent() {

        if (idField.getText().isEmpty()) return;

        Student s = new Student(
                Integer.parseInt(idField.getText()),
                numeroField.getText(),
                ecoleField.getText(),
                telephoneField.getText(),
                dateEntreePicker.getValue() != null ? dateEntreePicker.getValue().toString() : null,
                dateSortiePicker.getValue() != null ? dateSortiePicker.getValue().toString() : null,
                1
        );

        dao.updateStudent(s);
        loadStudents();
        clear();
    }

    // ================= DELETE =================
    @FXML
    public void handleDeleteStudent() {

        if (idField.getText().isEmpty()) return;

        dao.deleteStudent(Integer.parseInt(idField.getText()));
        loadStudents();
        clear();
    }

    private void clear() {
        idField.clear();
        numeroField.clear();
        ecoleField.clear();
        telephoneField.clear();
        dateEntreePicker.setValue(null);
        dateSortiePicker.setValue(null);
    }

    // NAVIGATION inchangée
    @FXML
    public void handleDashboard() {

        System.out.println("→ Chargement Dashboard...");

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));

            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) studentTable.getScene().getWindow();

            stage.setScene(scene);
            stage.setTitle("Tableau de bord");
            stage.setMaximized(true);

            System.out.println("✅ Dashboard chargé");

        } catch (Exception e) {

            System.out.println("❌ Erreur ouverture dashboard");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleEtudiants() {

        System.out.println("→ Chargement module étudiants...");

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/students.fxml"));

            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) studentTable.getScene().getWindow();

            stage.setScene(scene);
            stage.setTitle("Gestion des Étudiants");
            stage.setMaximized(true);

            System.out.println("✅ Module étudiants chargé");

        } catch (Exception e) {

            System.out.println("❌ Erreur ouverture étudiants");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleLogements() {

        System.out.println("→ Chargement logements...");

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/logements.fxml"));

            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) studentTable.getScene().getWindow();

            stage.setScene(scene);
            stage.setTitle("Gestion des Logements");
            stage.setMaximized(true);

            System.out.println("✅ Logements chargés");

        } catch (Exception e) {

            System.out.println("❌ Erreur logements");
            e.printStackTrace();
        }
    }

    @FXML
    public void handlePaiements() {

        System.out.println("→ Module Paiements");
    }

    @FXML
    public void handleIncidents() {

        System.out.println("→ Module Incidents");
    }

    @FXML
    public void handleLogout() {

        System.out.println("→ Déconnexion...");

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));

            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) studentTable.getScene().getWindow();

            stage.setScene(scene);
            stage.setTitle("Connexion");
            stage.setMaximized(true);

            System.out.println("✅ Déconnexion réussie");

        } catch (Exception e) {

            System.out.println("❌ Erreur déconnexion");
            e.printStackTrace();
        }
    }
}