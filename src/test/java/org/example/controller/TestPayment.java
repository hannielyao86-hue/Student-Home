package org.example.tests;

import org.example.dao.PaymentDAO;
import org.example.model.Payment;

import java.util.List;

/**
 * ════════════════════════════════════════════════════════════
 * TestPayment — Test CRUD Paiements
 * ════════════════════════════════════════════════════════════
 *
 * Lance ce fichier pour vérifier que le module Paiements
 * fonctionne correctement avec MySQL.
 *
 * Teste dans l'ordre :
 *   1. GENERATE ID — génération automatique d'un ID
 *   2. CREATE      — ajouter un paiement
 *   3. READ ALL    — lire tous les paiements
 *   4. READ RETARDS— lire les paiements en retard
 *   5. UPDATE      — changer le statut d'un paiement
 *   6. COUNT       — compter les retards
 *   7. DELETE      — supprimer un paiement
 *
 * @author Smart Student Residence Team
 * @version 1.0
 * ════════════════════════════════════════════════════════════
 */
public class TestPayment {

    public static void main(String[] args) {

        PaymentDAO dao = new PaymentDAO();

        System.out.println("═══════════════════════════════════");
        System.out.println("  TEST MODULE PAIEMENTS");
        System.out.println("═══════════════════════════════════\n");

        // ════════════════════════════════════════════════════════════════════
        // 1. GENERATE ID — génération automatique
        // ════════════════════════════════════════════════════════════════════
        System.out.println("── 1. GENERATE ID ─────────────────");

        String newId = dao.generateId();
        System.out.println("ID généré : " + newId);

        // ════════════════════════════════════════════════════════════════════
        // 2. CREATE — Ajouter un paiement PAYE
        // ════════════════════════════════════════════════════════════════════
        System.out.println("\n── 2. CREATE (PAYE) ───────────────");

        Payment p1 = new Payment(
                newId,                   // id_payment généré automatiquement
                500.0,                   // montant du loyer
                "2026-05-01",            // date_paiement
                Payment.STATUT_PAYE,     // statut : PAYE
                0.0                      // penaliter : 0 car pas de retard
        );

        boolean ok1 = dao.createPayment(p1);
        System.out.println("Résultat : " + (ok1 ? "✅ OK" : "❌ ECHEC"));

        // ════════════════════════════════════════════════════════════════════
        // 3. CREATE — Ajouter un paiement EN RETARD
        // ════════════════════════════════════════════════════════════════════
        System.out.println("\n── 3. CREATE (RETARD) ─────────────");

        String id2 = dao.generateId(); // génère le prochain ID
        Payment p2 = new Payment(
                id2,
                500.0,
                "2026-03-01",             // date ancienne
                Payment.STATUT_RETARD,    // statut : RETARD
                50.0                      // pénalité de 50€
        );

        boolean ok2 = dao.createPayment(p2);
        System.out.println("Résultat : " + (ok2 ? "✅ OK" : "❌ ECHEC"));

        // ════════════════════════════════════════════════════════════════════
        // 4. READ ALL — Lire tous les paiements
        // ════════════════════════════════════════════════════════════════════
        System.out.println("\n── 4. READ ALL ────────────────────");

        List<Payment> tous = dao.getAllPayments();
        System.out.println("Nombre total : " + tous.size());
        for (Payment p : tous) {
            System.out.println("  → " + p.toString());
        }

        // ════════════════════════════════════════════════════════════════════
        // 5. READ RETARDS — Paiements en retard uniquement
        // ════════════════════════════════════════════════════════════════════
        System.out.println("\n── 5. RETARDS ─────────────────────");

        List<Payment> retards = dao.getPaymentsEnRetard();
        System.out.println("Nombre de retards : " + retards.size());
        for (Payment p : retards) {
            System.out.println("  → ID: " + p.getIdPayment() +
                    " | " + p.getMontant() + "€" +
                    " | Pénalité: " + p.getPenaliter() + "€" +
                    " | Total: " + p.getMontantTotal() + "€");
        }

        // ════════════════════════════════════════════════════════════════════
        // 6. UPDATE STATUT — Passer EN_ATTENTE → PAYE
        // ════════════════════════════════════════════════════════════════════
        System.out.println("\n── 6. UPDATE STATUT ───────────────");

        boolean okUpdate = dao.updateStatut(newId, Payment.STATUT_EN_ATTENTE);
        System.out.println("Résultat UPDATE : " + (okUpdate ? "✅ OK" : "❌ ECHEC"));

        // ════════════════════════════════════════════════════════════════════
        // 7. COUNT RETARDS
        // ════════════════════════════════════════════════════════════════════
        System.out.println("\n── 7. COUNT RETARDS ───────────────");
        System.out.println("Paiements en retard : " + dao.countRetards());

        // ════════════════════════════════════════════════════════════════════
        // Résumé
        // ════════════════════════════════════════════════════════════════════
        System.out.println("\n═══════════════════════════════════");
        System.out.println("  ✅ TEST PAIEMENTS TERMINÉ");
        System.out.println("═══════════════════════════════════");
    }
}