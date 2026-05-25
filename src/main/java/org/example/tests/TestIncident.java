package org.example.tests;

import org.example.dao.IncidentDAO;
import org.example.model.Incident;

import java.time.LocalDate;
import java.util.List;

public class TestIncident {

    public static void main(String[] args) {

        System.out.println("===== TESTS INCIDENT =====\n");

        // -------------------------------------------------------
        // TEST 1 : Création du modèle
        // -------------------------------------------------------
        Incident incident = new Incident(
                "Plomberie",
                "Fuite d'eau dans la salle de bain",
                "Moyen",
                "En cours",
                LocalDate.of(2026, 5, 20),
                1
        );

        try {
            assert incident.getTypeIncident().equals("Plomberie")                        : "getTypeIncident() échoué";
            assert incident.getDescription().equals("Fuite d'eau dans la salle de bain"): "getDescription() échoué";
            assert incident.getPriorite().equals("Moyen")                                : "getPriorite() échoué";
            assert incident.getStatutIncident().equals("En cours")                       : "getStatutIncident() échoué";
            assert incident.getIdStudent() == 1                                          : "getIdStudent() échoué";
            assert incident.getIdIncident() == null                                      : "getIdIncident() doit être null avant insertion";

            incident.setIdIncident("INC001");
            assert incident.getIdIncident().equals("INC001") : "setIdIncident() échoué";

            System.out.println("✔ TEST 1 — MODÈLE INCIDENT RÉUSSI");

        } catch (Exception e) {
            System.out.println("❌ TEST 1 — MODÈLE INCIDENT ÉCHOUÉ");
            e.printStackTrace();
        }

        // -------------------------------------------------------
        // TEST 2 : Sauvegarde en base de données
        // -------------------------------------------------------
        IncidentDAO dao = new IncidentDAO();

        Incident incidentTest = new Incident(
                "Électricité",
                "Prise électrique défectueuse",
                "Élevé",
                "En cours",
                LocalDate.of(2026, 5, 20),
                1
        );

        try {
            boolean succes = dao.sauvegarder(incidentTest);
            if (succes) {
                System.out.println("✔ TEST 2 — SAUVEGARDE INCIDENT RÉUSSI — id : "
                        + incidentTest.getIdIncident());
            } else {
                System.out.println("❌ TEST 2 — SAUVEGARDE INCIDENT ÉCHOUÉ");
            }
        } catch (Exception e) {
            System.out.println("❌ TEST 2 — SAUVEGARDE INCIDENT ÉCHOUÉ");
            e.printStackTrace();
        }

        // -------------------------------------------------------
        // TEST 3 : Récupération depuis la base
        // -------------------------------------------------------
        try {
            List<Incident> incidents = dao.getAll();
            if (incidents != null && !incidents.isEmpty()) {
                System.out.println("✔ TEST 3 — GETALL INCIDENT RÉUSSI — "
                        + incidents.size() + " incident(s) trouvé(s)");
            } else {
                System.out.println("❌ TEST 3 — GETALL INCIDENT ÉCHOUÉ — liste vide ou null");
            }
        } catch (Exception e) {
            System.out.println("❌ TEST 3 — GETALL INCIDENT ÉCHOUÉ");
            e.printStackTrace();
        }

        // -------------------------------------------------------
        // TEST 4 : Vérification des données récupérées
        // -------------------------------------------------------
        try {
            List<Incident> incidents = dao.getAll();
            if (incidents != null && !incidents.isEmpty()) {
                Incident recupere = incidents.get(0);
                if (recupere.getIdIncident() != null
                        && recupere.getTypeIncident() != null
                        && recupere.getDescription() != null
                        && recupere.getPriorite() != null
                        && recupere.getStatutIncident() != null
                        && recupere.getIdStudent() > 0) {
                    System.out.println("✔ TEST 4 — DONNÉES INCIDENT RÉUSSI");
                } else {
                    System.out.println("❌ TEST 4 — DONNÉES INCIDENT ÉCHOUÉ — champs null détectés");
                }
            } else {
                System.out.println("❌ TEST 4 — DONNÉES INCIDENT ÉCHOUÉ — liste vide");
            }
        } catch (Exception e) {
            System.out.println("❌ TEST 4 — DONNÉES INCIDENT ÉCHOUÉ");
            e.printStackTrace();
        }

        System.out.println("\n===== FIN DES TESTS INCIDENT =====");
    }
}