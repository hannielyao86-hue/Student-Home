package org.example.model;

/**
 * ════════════════════════════════════════════════════════════
 * Payment — Modèle Paiement
 * ════════════════════════════════════════════════════════════
 *
 * Représente un paiement de loyer dans l'application.
 * Correspond exactement à la table "payments" en base de données.
 *
 * Colonnes BDD réelles (vérifiées avec DESCRIBE payments) :
 *   id_payment       VARCHAR(50)  (PK) — identifiant manuel ex: PAY001
 *   montant          DECIMAL(15,2)     — montant du loyer
 *   date_paiement    DATE              — date du paiement
 *   statut_paiement  VARCHAR(250)      — PAYE / EN_ATTENTE / RETARD
 *   penaliter        DECIMAL(15,2)     — pénalité en cas de retard
 *
 * Relation :
 *   C'est la table CONTRACT qui référence PAYMENTS via id_payment (FK)
 *
 * @author Smart Student Residence Team
 * @version 1.0
 * ════════════════════════════════════════════════════════════
 */
public class Payment {

    // ════════════════════════════════════════════════════════════════════════
    // CONSTANTES — Statuts possibles d'un paiement
    // ════════════════════════════════════════════════════════════════════════

    /** Paiement effectué */
    public static final String STATUT_PAYE       = "PAYE";

    /** Paiement en attente */
    public static final String STATUT_EN_ATTENTE = "EN_ATTENTE";

    /** Paiement en retard */
    public static final String STATUT_RETARD     = "RETARD";

    // ════════════════════════════════════════════════════════════════════════
    // ATTRIBUTS — correspondent exactement aux colonnes de la table payments
    // ════════════════════════════════════════════════════════════════════════

    /** Identifiant unique du paiement (VARCHAR, ex: PAY001, PAY002...) */
    private String idPayment;

    /** Montant du loyer payé (en euros) */
    private double montant;

    /** Date du paiement (format : YYYY-MM-DD) */
    private String datePaiement;

    /** Statut du paiement : PAYE / EN_ATTENTE / RETARD */
    private String statutPaiement;

    /** Montant de la pénalité en cas de retard (0.0 si pas de retard) */
    private double penaliter;

    // ════════════════════════════════════════════════════════════════════════
    // CONSTRUCTEUR COMPLET
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Crée un paiement avec tous ses attributs.
     *
     * @param idPayment      identifiant manuel (ex: PAY001)
     * @param montant        montant du loyer en euros
     * @param datePaiement   date du paiement (YYYY-MM-DD)
     * @param statutPaiement PAYE / EN_ATTENTE / RETARD
     * @param penaliter      montant de la pénalité (0.0 si aucune)
     */
    public Payment(String idPayment,
                   double montant,
                   String datePaiement,
                   String statutPaiement,
                   double penaliter) {

        this.idPayment      = idPayment;
        this.montant        = montant;
        this.datePaiement   = datePaiement;
        this.statutPaiement = statutPaiement;
        this.penaliter      = penaliter;
    }

    // ════════════════════════════════════════════════════════════════════════
    // GETTERS
    // ════════════════════════════════════════════════════════════════════════

    public String getIdPayment()      { return idPayment; }
    public double getMontant()        { return montant; }
    public String getDatePaiement()   { return datePaiement; }
    public String getStatutPaiement() { return statutPaiement; }
    public double getPenaliter()      { return penaliter; }

    // ════════════════════════════════════════════════════════════════════════
    // SETTERS
    // ════════════════════════════════════════════════════════════════════════

    public void setIdPayment(String idPayment)         { this.idPayment = idPayment; }
    public void setMontant(double montant)             { this.montant = montant; }
    public void setDatePaiement(String datePaiement)   { this.datePaiement = datePaiement; }
    public void setStatutPaiement(String s)            { this.statutPaiement = s; }
    public void setPenaliter(double penaliter)         { this.penaliter = penaliter; }

    // ════════════════════════════════════════════════════════════════════════
    // MÉTHODES UTILITAIRES
    // ════════════════════════════════════════════════════════════════════════

    /** Vérifie si le paiement est en retard */
    public boolean isEnRetard() { return STATUT_RETARD.equals(this.statutPaiement); }

    /** Vérifie si le paiement est effectué */
    public boolean isPaye() { return STATUT_PAYE.equals(this.statutPaiement); }

    /** Calcule le montant total (loyer + pénalité) */
    public double getMontantTotal() { return montant + penaliter; }

    // ════════════════════════════════════════════════════════════════════════
    // toString
    // ════════════════════════════════════════════════════════════════════════
    @Override
    public String toString() {
        return "Payment{" +
                "id='"          + idPayment      + "'" +
                ", montant="    + montant        +
                ", date='"      + datePaiement   + "'" +
                ", statut='"    + statutPaiement + "'" +
                ", penalite="   + penaliter      +
                "}";
    }
}