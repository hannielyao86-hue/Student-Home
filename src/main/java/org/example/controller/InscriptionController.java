package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class InscriptionController {

    // Champs du formulaire liés au FXML
    @FXML
    private TextField INEField;

    @FXML
    private TextField NomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField etablissementField;

    @FXML
    private TextField mailField;

    @FXML
    private PasswordField motDePasseField;

    // Méthode appelée lors du clic sur "S'inscrire"
    @FXML
    private void sInscrire() {

        // Récupération des valeurs des champs
        String INE = INEField.getText().trim();
        String nom = NomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String etablissement = etablissementField.getText().trim();
        String mail = mailField.getText().trim();
        String motDePasse = motDePasseField.getText().trim();

        // Vérification que les champs obligatoires ne sont pas vides
        if (nom.isEmpty() || prenom.isEmpty() || mail.isEmpty() || motDePasse.isEmpty()) {
            System.out.println("Veuillez remplir tous les champs obligatoires.");
            return;
        }

        // TODO : Appeler le service d'inscription pour enregistrer l'utilisateur
        System.out.println("Inscription de : " + nom + " " + prenom);
    }
}