package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

import org.example.dao.PaymentDAO;
import org.example.model.Payment;
import org.example.model.Student;
import org.example.utils.Session;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StudentPaiementController {

    @FXML private TextField amountField;
    @FXML private TextField cardOwnerField;

    @FXML private Label cardNumberLabel;
    @FXML private TextField cardNumberField;
    @FXML private Label expiryLabel;
    @FXML private TextField expiryField;
    @FXML private Label cvvLabel;
    @FXML private PasswordField cvvField;

    @FXML private Button payButton;
    @FXML private Label statusLabel;

    private final PaymentDAO paymentDAO = new PaymentDAO();

    @FXML
    public void initialize() {
        // Formulaire de paiement par carte uniquement.
    }

    @FXML
    public void handlePaymentSubmit(ActionEvent event) {
        statusLabel.setText("");

        // Validation simple
        String amountText = amountField.getText().trim();
        if (amountText.isEmpty()) {
            statusLabel.setText("Veuillez saisir un montant.");
            return;
        }

        double montant;
        try {
            montant = Double.parseDouble(amountText.replace(',', '.'));
            if (montant <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            statusLabel.setText("Montant invalide.");
            return;
        }

        if (cardOwnerField.getText().trim().isEmpty() || cardNumberField.getText().trim().isEmpty() || expiryField.getText().trim().isEmpty() || cvvField.getText().trim().isEmpty()) {
            statusLabel.setText("Veuillez remplir les informations de carte.");
            return;
        }

        // Générer ID, date et créer Payment
        String id = paymentDAO.generateId();
        String date = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        Student student = Session.getLoggedInStudent();
        if (student == null) {
            statusLabel.setStyle("-fx-text-fill: #ef4444;");
            statusLabel.setText("Erreur : étudiant non connecté.");
            return;
        }

        Payment p = new Payment(id, montant, date, Payment.STATUT_PAYE, 0.0, student.getIdStudent());

        boolean ok = paymentDAO.createPayment(p);

        if (ok) {
            statusLabel.setStyle("-fx-text-fill: #059669;");
            statusLabel.setText("Paiement enregistré avec succès (ID: " + id + ").");
            // vider le formulaire
            amountField.clear(); cardOwnerField.clear(); cardNumberField.clear(); expiryField.clear(); cvvField.clear();
        } else {
            statusLabel.setStyle("-fx-text-fill: #ef4444;");
            statusLabel.setText("Erreur lors de l'enregistrement du paiement.");
        }
    }

    @FXML
    public void handleCancel(ActionEvent event) {
        // Nettoie le formulaire
        amountField.clear(); cardOwnerField.clear(); cardNumberField.clear(); expiryField.clear(); cvvField.clear();
        statusLabel.setText("");
    }
}
