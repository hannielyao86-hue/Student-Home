package org.example.tests;

/*
Import DAO
*/
import org.example.dao.ReservationDAO;

/*
Import modèle
*/
import org.example.model.Reservation;


/*
Classe de test
*/
public class TestReservation {

    public static void main(String[] args) {

        /*
        Création objet réservation
        */
        Reservation reservation =
                new Reservation();


        /*
        Données test
        */

        // Date réservation
        reservation.setDateReservation(
                "2025-07-20"
        );

        // Heure début
        reservation.setHeureDebut(
                "14:00:00"
        );

        // Heure fin
        reservation.setHeureFin(
                "16:00:00"
        );

        // Statut réservation
        reservation.setStatutReservation(
                "CONFIRMEE"
        );

        // ID espace commun
        reservation.setIdCommonSpeace(
                1
        );

        // ID étudiant
        reservation.setIdStudent(
                1
        );


        /*
        DAO réservation
        */
        ReservationDAO reservationDAO =
                new ReservationDAO();


        /*
        Insertion SQL
        */
        reservationDAO.createReservation(
                reservation
        );


        /*
        Message console
        */
        System.out.println(
                "Test réservation terminé"
        );
    }
}