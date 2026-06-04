package org.example.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import org.example.dao.StudentDAO;
import org.example.dao.UserDAO;
import org.example.model.Student;
import org.example.model.User;
import org.example.utils.PasswordUtil;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InscriptionController {

    // Champs du formulaire liés au FXML
    @FXML
    private TextField NomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField mailField;

    @FXML
    private PasswordField motDePasseField;

    // Méthode appelée lors du clic sur "S'inscrire"
    @FXML
    private void sInscrire() {

        // Récupération des valeurs des champs
        String nom = NomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String mail = mailField.getText().trim();
        String motDePasse = motDePasseField.getText().trim();

        // Vérification que les champs obligatoires ne sont pas vides
        if (nom.isEmpty() || prenom.isEmpty() || mail.isEmpty() || motDePasse.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs obligatoires.");
            a.showAndWait();
            return;
        }

        // Vérifier unicité de l'email
        UserDAO userDAO = new UserDAO();
        if (userDAO.emailExists(mail)) {
            Alert a = new Alert(Alert.AlertType.WARNING, "Cet email est déjà utilisé.");
            a.showAndWait();
            return;
        }

        // Créer et enregistrer le User
        User user = new User();
        user.setNomUsers(nom);
        user.setPrenomUser(prenom);
        user.setEmailUser(mail);
        user.setMotPasseHash(PasswordUtil.hashPassword(motDePasse));
        user.setStatut("ACTIF");
        user.setDateCreation(LocalDateTime.now());
        user.setRoleId(2); // 2 = étudiant (à ajuster si besoin)

        boolean created = userDAO.inscrire(user);
        if (!created) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Erreur lors de la création du compte.");
            a.showAndWait();
            return;
        }

        // Récupérer l'utilisateur créé pour obtenir son id
        User createdUser = userDAO.findByEmail(mail);
        if (createdUser == null) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Impossible de récupérer l'utilisateur créé.");
            a.showAndWait();
            return;
        }

        // Créer l'entité Student liée
        Student student = new Student(
                0,
                nom,
                prenom,
                mail,
                "", // ecole
                "", // telephone
                "", // dateEntree
                "", // dateSortie
                createdUser.getIdUsers()
        );

        Alert success = new Alert(Alert.AlertType.INFORMATION, "Inscription réussie ! Redirection vers la page de connexion...");
        success.showAndWait();

        // CORRECTION : Rediriger vers la page de LOGIN et non le Dashboard Admin
        try {
            // Remplacer "/view/dashboard.fxml" par "/view/login.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Parent root = loader.load();

            // Récupérer la fenêtre actuelle pour y injecter la scène de connexion
            Stage stage = (Stage) NomField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen(); // Recentrer proprement la petite fenêtre de login

        } catch (IOException e) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Erreur lors de la redirection : " + e.getMessage());
            a.showAndWait();
            e.printStackTrace();
        }
    }
}