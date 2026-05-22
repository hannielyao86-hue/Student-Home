package org.example.dao;

/*
Import connexion MySQL
*/
import org.example.config.DatabaseConnection;

/*
Import modèle Reservation
*/
import org.example.model.Reservation;

/*
Imports SQL
*/
import java.sql.Connection;
import java.sql.PreparedStatement;


/*
DAO = Data Access Object

Cette classe permet
d'ajouter une réservation
dans MySQL.
*/
public class ReservationDAO {

    /*
    Méthode :
    créer une réservation
    */
    public void createReservation(
            Reservation reservation
    ) {

        /*
        Requête SQL INSERT
        */
        String insertSql =
                "INSERT INTO reservations " +
                        "(date_reservation, heure_debut, heure_fin, statut_reservation, id_common_speace, id_student) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";


        try {

            /*
            Connexion MySQL
            */
            Connection connection =
                    DatabaseConnection
                            .getInstance()
                            .getConnection();


            /*
            Préparation SQL
            */
            PreparedStatement statement =
                    connection.prepareStatement(insertSql);


            /*
            Remplacement des ?
            */

            // date_reservation
            statement.setString(
                    1,
                    reservation.getDateReservation()
            );

            // heure_debut
            statement.setString(
                    2,
                    reservation.getHeureDebut()
            );

            // heure_fin
            statement.setString(
                    3,
                    reservation.getHeureFin()
            );

            // statut_reservation
            statement.setString(
                    4,
                    reservation.getStatutReservation()
            );

            // id_common_speace
            statement.setInt(
                    5,
                    reservation.getIdCommonSpeace()
            );

            // id_student
            statement.setInt(
                    6,
                    reservation.getIdStudent()
            );


            /*
            Exécution SQL
            */
            statement.executeUpdate();


            /*
            Message succès
            */
            System.out.println(
                    "✔ Réservation ajoutée avec succès"
            );

        } catch (Exception e) {

            /*
            Message erreur
            */
            System.out.println(
                    "❌ Erreur lors de la réservation"
            );

            e.printStackTrace();
        }
    }
}