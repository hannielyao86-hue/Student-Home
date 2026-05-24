package org.example.controller;

// ── Imports JavaFX ────────────────────────────────────────────────────────────
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

// ── Imports projet ────────────────────────────────────────────────────────────
import org.example.dao.PaymentDAO;
import org.example.model.Payment;

import java.util.List;

/**
 * ════════════════════════════════════════════════════════════
 * PaiementsController
 * ════════════════════════════════════════════════════════════
 *
 * Gère l'écran de gestion des paiements.
 *
 * Responsabilités :
 *   - Afficher tous les paiements dans le TableView
 *   - Afficher les paiements en retard dans l'onglet Retards
 *   - Ajouter un nouveau paiement
 *   - Marquer un paiement comme PAYE
 *   - Marquer un paiement comme RETARD
 *   - Supprimer un paiement
 *   - Mettre à jour les statistiques en bas
 *   - Retourner au dashboard
 *
 * @author Smart Student Residence Team
 * @version 1.0
 * ════════════════════════════════════════════════════════════
 */
public class PaiementsController {

    // ════════════════════════════════════════════════════════════════════════
    // FORMULAIRE — Champs de saisie
    // ════════════════════════════════════════════════════════════════════════

    /** Champ ID paiement (rempli automatiquement) */
    @FXML private TextField idField;

    /** Champ montant du loyer */
    @FXML private TextField montantField;

    /** Champ date du paiement */
    @FXML private TextField datePaiementField;

    /** Sélecteur de statut (ComboBox) */
    @FXML private ComboBox<String> statutCombo;

    /** Champ pénalité */
    @FXML private TextField penaliteField;

    /** Label de feedback (succès ou erreur) */
    @FXML private Label messageLabel;

    // ════════════════════════════════════════════════════════════════════════
    // TABLEAU 1 — Tous les paiements
    // ════════════════════════════════════════════════════════════════════════

    @FXML private TableView<Payment>            tableTous;
    @FXML private TableColumn<Payment, String>  colId;
    @FXML private TableColumn<Payment, Double>  colMontant;
    @FXML private TableColumn<Payment, String>  colDate;
    @FXML private TableColumn<Payment, String>  colStatut;
    @FXML private TableColumn<Payment, Double>  colPenalite;
    @FXML private TableColumn<Payment, Double>  colTotal;

    // ════════════════════════════════════════════════════════════════════════
    // TABLEAU 2 — Paiements en retard
    // ════════════════════════════════════════════════════════════════════════

    @FXML private TableView<Payment>            tableRetards;
    @FXML private TableColumn<Payment, String>  colRetardId;
    @FXML private TableColumn<Payment, Double>  colRetardMontant;
    @FXML private TableColumn<Payment, String>  colRetardDate;
    @FXML private TableColumn<Payment, Double>  colRetardPenalite;
    @FXML private TableColumn<Payment, Double>  colRetardTotal;

    // ════════════════════════════════════════════════════════════════════════
    // STATISTIQUES — Labels en bas de l'écran
    // ════════════════════════════════════════════════════════════════════════

    @FXML private Label statTotalLabel;
    @FXML private Label statRetardLabel;
    @FXML private Label statMontantLabel;

    // ════════════════════════════════════════════════════════════════════════
    // DAO + état
    // ════════════════════════════════════════════════════════════════════════

    /** DAO pour accéder à la base de données payments */
    private final PaymentDAO dao = new PaymentDAO();

    /** Paiement actuellement sélectionné dans le tableau */
    private Payment selectedPayment = null;

    // ════════════════════════════════════════════════════════════════════════
    // INITIALIZE
    // Appelée automatiquement au chargement du FXML.
    // Configure les colonnes, remplit le ComboBox et charge les données.
    // ════════════════════════════════════════════════════════════════════════
    @FXML
    public void initialize() {

        System.out.println("→ Initialisation PaiementsController");

        // ── Configuration ComboBox statut ─────────────────────────────────
        // On remplit la liste déroulante avec les 3 statuts possibles
        statutCombo.setItems(FXCollections.observableArrayList(
                Payment.STATUT_PAYE,
                Payment.STATUT_EN_ATTENTE,
                Payment.STATUT_RETARD
        ));
        // Valeur par défaut
        statutCombo.setValue(Payment.STATUT_PAYE);

        // ── Génération automatique de l'ID ────────────────────────────────
        // On pré-remplit le champ ID avec le prochain ID disponible
        idField.setText(dao.generateId());

        // ── Configuration colonnes Tableau 1 (tous les paiements) ─────────
        // PropertyValueFactory lie la colonne au getter du modèle Payment
        colId.setCellValueFactory(
                new PropertyValueFactory<>("idPayment")       // → getIdPayment()
        );
        colMontant.setCellValueFactory(
                new PropertyValueFactory<>("montant")         // → getMontant()
        );
        colDate.setCellValueFactory(
                new PropertyValueFactory<>("datePaiement")    // → getDatePaiement()
        );
        colStatut.setCellValueFactory(
                new PropertyValueFactory<>("statutPaiement")  // → getStatutPaiement()
        );
        colPenalite.setCellValueFactory(
                new PropertyValueFactory<>("penaliter")       // → getPenaliter()
        );
        colTotal.setCellValueFactory(
                new PropertyValueFactory<>("montantTotal")    // → getMontantTotal()
        );

        // ── Configuration colonnes Tableau 2 (retards) ────────────────────
        colRetardId.setCellValueFactory(
                new PropertyValueFactory<>("idPayment")
        );
        colRetardMontant.setCellValueFactory(
                new PropertyValueFactory<>("montant")
        );
        colRetardDate.setCellValueFactory(
                new PropertyValueFactory<>("datePaiement")
        );
        colRetardPenalite.setCellValueFactory(
                new PropertyValueFactory<>("penaliter")
        );
        colRetardTotal.setCellValueFactory(
                new PropertyValueFactory<>("montantTotal")
        );

        // ── Sélection ligne → remplir le formulaire ───────────────────────
        // Quand on clique sur une ligne, le formulaire se remplit
        tableTous.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> {
                    selectedPayment = newVal;
                    if (newVal != null) {
                        // On remplit les champs avec les données de la ligne
                        idField.setText(newVal.getIdPayment());
                        montantField.setText(String.valueOf(newVal.getMontant()));
                        datePaiementField.setText(newVal.getDatePaiement());
                        statutCombo.setValue(newVal.getStatutPaiement());
                        penaliteField.setText(String.valueOf(newVal.getPenaliter()));
                    }
                });

        // ── Chargement initial des données ────────────────────────────────
        loadAllData();

        System.out.println("✅ PaiementsController initialisé");
    }

    // ════════════════════════════════════════════════════════════════════════
    // loadAllData() — Charge toutes les données depuis MySQL
    //
    // Recharge le tableau principal + le tableau des retards + les stats.
    // Appelée au démarrage et après chaque modification.
    // ════════════════════════════════════════════════════════════════════════
    private void loadAllData() {

        // ── Charger tous les paiements ────────────────────────────────────
        List<Payment> tous = dao.getAllPayments();
        tableTous.getItems().setAll(tous);

        // ── Charger uniquement les retards ────────────────────────────────
        List<Payment> retards = dao.getPaymentsEnRetard();
        tableRetards.getItems().setAll(retards);

        // ── Mettre à jour les statistiques ───────────────────────────────
        updateStats(tous, retards);
    }

    // ════════════════════════════════════════════════════════════════════════
    // updateStats() — Met à jour les labels de statistiques en bas
    // ════════════════════════════════════════════════════════════════════════
    private void updateStats(List<Payment> tous, List<Payment> retards) {

        // Nombre total de paiements
        statTotalLabel.setText("Total : " + tous.size() + " paiements");

        // Nombre de retards
        statRetardLabel.setText("En retard : " + retards.size());

        // Montant total collecté (somme des paiements PAYE)
        double totalCollecte = tous.stream()
                .filter(Payment::isPaye)       // uniquement les PAYE
                .mapToDouble(Payment::getMontant)
                .sum();
        statMontantLabel.setText("Total collecté : " + totalCollecte + " €");
    }

    // ════════════════════════════════════════════════════════════════════════
    // addPayment() — Ajoute un nouveau paiement
    //
    // Appelée quand on clique sur "➕ Ajouter".
    // Lit les champs, valide, insère en BDD, rafraîchit.
    // ════════════════════════════════════════════════════════════════════════
    @FXML
    public void addPayment() {

        // ── Validation des champs obligatoires ────────────────────────────
        if (idField.getText().isEmpty()           ||
                montantField.getText().isEmpty()      ||
                datePaiementField.getText().isEmpty()) {

            showMessage("⚠️ Remplissez tous les champs obligatoires", false);
            return;
        }

        try {
            // Création de l'objet Payment avec les valeurs du formulaire
            Payment p = new Payment(
                    idField.getText().trim(),              // id_payment
                    Double.parseDouble(montantField.getText().trim()), // montant
                    datePaiementField.getText().trim(),    // date_paiement
                    statutCombo.getValue(),               // statut_paiement
                    penaliteField.getText().isEmpty()
                            ? 0.0
                            : Double.parseDouble(penaliteField.getText().trim()) // penaliter
            );

            // Insertion en base via le DAO
            boolean ok = dao.createPayment(p);

            if (ok) {
                showMessage("✅ Paiement ajouté avec succès", true);
                clearFields();
                loadAllData();
            } else {
                showMessage("❌ Erreur lors de l'ajout", false);
            }

        } catch (NumberFormatException e) {
            // Si le montant ou la pénalité ne sont pas des nombres valides
            showMessage("⚠️ Montant ou pénalité invalide (ex: 500.0)", false);
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // marquerPaye() — Marque le paiement sélectionné comme PAYE
    //
    // Appelée quand on clique sur "✅ Marquer Payé".
    // ════════════════════════════════════════════════════════════════════════
    @FXML
    public void marquerPaye() {

        if (selectedPayment == null) {
            showMessage("⚠️ Sélectionnez un paiement dans le tableau", false);
            return;
        }

        // Mise à jour du statut en BDD
        boolean ok = dao.updateStatut(
                selectedPayment.getIdPayment(),
                Payment.STATUT_PAYE
        );

        if (ok) {
            showMessage("✅ Paiement marqué comme PAYÉ", true);
            loadAllData();
        } else {
            showMessage("❌ Erreur lors de la mise à jour", false);
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // marquerRetard() — Marque le paiement sélectionné comme RETARD
    // ════════════════════════════════════════════════════════════════════════
    @FXML
    public void marquerRetard() {

        if (selectedPayment == null) {
            showMessage("⚠️ Sélectionnez un paiement dans le tableau", false);
            return;
        }

        boolean ok = dao.updateStatut(
                selectedPayment.getIdPayment(),
                Payment.STATUT_RETARD
        );

        if (ok) {
            showMessage("⚠️ Paiement marqué comme RETARD", true);
            loadAllData();
        } else {
            showMessage("❌ Erreur lors de la mise à jour", false);
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // deletePayment() — Supprime le paiement sélectionné
    // ════════════════════════════════════════════════════════════════════════
    @FXML
    public void deletePayment() {

        if (selectedPayment == null) {
            showMessage("⚠️ Sélectionnez un paiement à supprimer", false);
            return;
        }

        // Confirmation avant suppression
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmer la suppression");
        confirm.setHeaderText("Supprimer le paiement " + selectedPayment.getIdPayment() + " ?");
        confirm.setContentText("Cette action est irréversible.");

        // On attend la réponse de l'utilisateur
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean ok = dao.deletePayment(selectedPayment.getIdPayment());
                if (ok) {
                    showMessage("✅ Paiement supprimé", true);
                    clearFields();
                    loadAllData();
                } else {
                    showMessage("❌ Erreur suppression", false);
                }
            }
        });
    }

    // ════════════════════════════════════════════════════════════════════════
    // refreshTable() — Rafraîchit les données depuis MySQL
    // ════════════════════════════════════════════════════════════════════════
    @FXML
    public void refreshTable() {
        loadAllData();
        // Génère un nouvel ID pour le prochain paiement
        idField.setText(dao.generateId());
        showMessage("🔄 Données actualisées", true);
    }

    // ════════════════════════════════════════════════════════════════════════
    // handleRetour() — Retourne au dashboard principal
    // ════════════════════════════════════════════════════════════════════════
    @FXML
    public void handleRetour() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/dashboard.fxml")
            );
            Scene scene = new Scene(loader.load());

            // Récupération du Stage via le label des stats
            Stage stage = (Stage) statTotalLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Smart Student Residence — Dashboard");
            stage.setMaximized(true);

        } catch (Exception e) {
            System.out.println("[Paiements] Erreur retour dashboard : " + e.getMessage());
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // clearFields() — Vide tous les champs du formulaire
    // ════════════════════════════════════════════════════════════════════════
    private void clearFields() {
        montantField.clear();
        datePaiementField.clear();
        penaliteField.clear();
        statutCombo.setValue(Payment.STATUT_PAYE);
        selectedPayment = null;
        // Génère un nouvel ID automatiquement
        idField.setText(dao.generateId());
    }

    // ════════════════════════════════════════════════════════════════════════
    // showMessage() — Affiche un message de feedback à l'utilisateur
    //
    // @param msg    message à afficher
    // @param succes true = vert (succès), false = rouge (erreur)
    // ════════════════════════════════════════════════════════════════════════
    private void showMessage(String msg, boolean succes) {
        messageLabel.setText(msg);
        messageLabel.setStyle(succes
                ? "-fx-text-fill: #27ae60; -fx-font-weight: bold;"  // vert
                : "-fx-text-fill: #e74c3c; -fx-font-weight: bold;"  // rouge
        );
    }
}