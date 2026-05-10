package org.example.config;

// ── Imports nécessaires
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**

 * DatabaseConnection — Gestionnaire de connexion MySQL

 * Ce fichier fait le lien entre Java et ta base de données MySQL.
 *
 * PATTERN UTILISÉ : Singleton
 *   → On crée UNE SEULE connexion pour toute l'application.
 *   → Toutes les classes qui ont besoin de la BDD utilisent
 *     cette même connexion via getInstance().
 *
 * SÉCURITÉ :
 *   → Les identifiants (url, user, password) ne sont JAMAIS
 *     écrits en dur ici. Ils viennent du fichier db.properties.
 *
 * UTILISATION dans une autre classe :
 *   Connection conn = DatabaseConnection.getInstance().getConnection();
 */
public class DatabaseConnection {

    // Instance unique (Singleton)
    // "static" = partagée par toute l'application, pas juste un objet
    // "private" = personne ne peut y accéder directement de l'extérieur
    private static DatabaseConnection instance;

    //  La connexion SQL elle-même
    private Connection connection;

    //  Paramètres de connexion (chargés depuis db.properties)
    private String url;
    private String user;
    private String password;

    // CONSTRUCTEUR PRIVÉ
    // "private" = on ne peut pas faire "new DatabaseConnection()" depuis
    // l'extérieur. On est forcé de passer par getInstance().

    private DatabaseConnection() {
        // Étape 1 : lire le fichier db.properties
        loadProperties();
        // Étape 2 : établir la connexion MySQL
        connect();
    }

    // getInstance() — Point d'accès unique au Singleton
    //
    // Si l'instance n'existe pas encore → on la crée.
    // Si elle existe déjà → on retourne celle qui existe.
    // Résultat : une seule connexion pour toute l'application.

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            // Première fois : on crée la connexion
            instance = new DatabaseConnection();
        }
        // Les fois suivantes : on retourne la connexion existante
        return instance;
    }

    // getConnection() — Retourne la connexion active
    //
    // Vérifie que la connexion est encore ouverte.
    // Si elle est fermée (timeout, redémarrage MySQL), on se reconnecte.

    public Connection getConnection() {
        try {
            // isClosed() = true si la connexion a été fermée
            if (connection == null || connection.isClosed()) {
                System.out.println("[DatabaseConnection] Connexion perdue, reconnexion...");
                connect();
            }
        } catch (SQLException e) {
            System.err.println("[DatabaseConnection] Erreur vérification connexion : " + e.getMessage());
            connect();
        }
        return connection;
    }

    // loadProperties() — Charge les paramètres depuis db.properties
    //
    // On lit le fichier src/main/resources/db.properties
    // et on récupère : db.url, db.user, db.password

    private void loadProperties() {
        // Properties = classe Java pour lire les fichiers .properties
        Properties props = new Properties();

        // getResourceAsStream cherche le fichier dans src/main/resources/
        try (InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream("db.properties")) {

            // Si le fichier est introuvable → message d'erreur clair
            if (input == null) {
                throw new RuntimeException(
                        "[DatabaseConnection] Fichier db.properties introuvable !\n" +
                                "Verifie qu'il existe dans src/main/resources/"
                );
            }

            // Chargement des propriétés depuis le fichier
            props.load(input);

            // Récupération des 3 valeurs nécessaires
            this.url      = props.getProperty("db.url");
            this.user     = props.getProperty("db.user");
            this.password = props.getProperty("db.password");

            System.out.println("[DatabaseConnection] Parametres charges depuis db.properties");

        } catch (IOException e) {
            throw new RuntimeException(
                    "[DatabaseConnection] Erreur lecture db.properties : " + e.getMessage()
            );
        }
    }

    // connect() — Établit la connexion JDBC vers MySQL
    //
    // JDBC = Java Database Connectivity
    // C'est le mécanisme standard Java pour parler à une base de données.
    //
    // Étapes :
    //   1. Charger le driver MySQL (le "traducteur" Java ↔ MySQL)
    //   2. Ouvrir la connexion avec url + user + password

    private void connect() {
        try {
            // Étape 1 : charger le driver MySQL
            // Sans ça, Java ne sait pas comment communiquer avec MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Étape 2 : ouvrir la connexion
            // DriverManager crée le "canal" entre Java et MySQL
            this.connection = DriverManager.getConnection(url, user, password);

            System.out.println("[DatabaseConnection] Connexion MySQL etablie avec succes !");

        } catch (ClassNotFoundException e) {
            // Arrive si mysql-connector-java n'est pas dans le pom.xml
            throw new RuntimeException(
                    "[DatabaseConnection] Driver MySQL introuvable.\n" +
                            "Verifie que mysql-connector-java est dans ton pom.xml"
            );
        } catch (SQLException e) {
            // Arrive si MySQL est éteint, mauvais mot de passe, etc.
            throw new RuntimeException(
                    "[DatabaseConnection] Connexion MySQL echouee !\n" +
                            "Verifie que MySQL est demarre (XAMPP)\n" +
                            "Verifie le mot de passe dans db.properties\n" +
                            "Detail : " + e.getMessage()
            );
        }
    }

    // closeConnection() — Ferme proprement la connexion
    //
    // À appeler quand l'application se ferme (dans App.java).
    // Libère les ressources MySQL proprement.

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("[DatabaseConnection] Connexion MySQL fermee proprement.");
            } catch (SQLException e) {
                System.err.println("[DatabaseConnection] Erreur fermeture : " + e.getMessage());
            }
        }
    }
}
