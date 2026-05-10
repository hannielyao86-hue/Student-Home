package org.example.dao;

// Import du modèle Affectation (objet métier)
import org.example.model.Affectation;

// Import de la classe de connexion à la base de données
import org.example.config.DatabaseConnection;

// Imports SQL nécessaires pour exécuter des requêtes
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AffectationDAO {

    // Méthode qui permet d'insérer une affectation dans la base de données
    public void createAffectation(Affectation affectation) {

        // Requête SQL d'insertion avec paramètres "?"
        // On utilise des paramètres pour éviter les injections SQL
        String sql =
                "INSERT INTO affectations(student_id, room_id, affectation_date) VALUES (?, ?, ?)";

        try {

            // On récupère l'instance unique de connexion (Singleton)
            DatabaseConnection databaseConnection =
                    DatabaseConnection.getInstance();

            // On récupère la connexion MySQL active
            Connection connection =
                    databaseConnection.getConnection();

            // Préparation de la requête SQL (plus sécurisée et optimisée)
            PreparedStatement statement =
                    connection.prepareStatement(sql);

            // Remplacement du premier "?" par l'ID étudiant
            statement.setInt(
                    1,
                    affectation.getStudentId()
            );

            // Remplacement du deuxième "?" par l'ID de la chambre
            statement.setInt(
                    2,
                    affectation.getRoomId()
            );

            // Remplacement du troisième "?" par la date d'affectation
            statement.setString(
                    3,
                    affectation.getAffectationDate()
            );

            // Exécution de la requête INSERT dans la base
            statement.executeUpdate();

            // Message de confirmation dans la console
            System.out.println("Affectation ajoutée avec succès !");

        } catch (Exception e) {

            // Affichage des erreurs si quelque chose échoue
            e.printStackTrace();
        }
    }
}