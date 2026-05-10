package org.example.tests;

// Import du DAO (accès base de données)
import org.example.dao.AffectationDAO;

// Import du modèle (objet Affectation)
import org.example.model.Affectation;

/**
 * Classe de test pour vérifier :
 * - connexion MySQL
 * - insertion dans la base
 * - fonctionnement du DAO
 *
 * ⚠️ Cette classe est uniquement pour TEST
 * elle ne fait pas partie de l'application finale
 */
public class TestAffectation {

    public static void main(String[] args) {

        // 1️⃣ Création du DAO (pont vers la base de données)
        AffectationDAO dao = new AffectationDAO();

        try {

            // 2️⃣ Création d’une affectation TEST
            Affectation affectation = new Affectation(
                    1,              // student_id fictif
                    2,              // room_id fictif
                    "2026-05-10"    // date test
            );

            // 3️⃣ Insertion dans la base de données
            dao.createAffectation(affectation);

            // 4️⃣ Confirmation côté Java
            System.out.println("✔ TEST RÉUSSI : insertion OK");

        } catch (Exception e) {

            // 5️⃣ Affichage des erreurs si problème
            System.out.println("❌ ERREUR TEST :");
            e.printStackTrace();
        }
    }
}