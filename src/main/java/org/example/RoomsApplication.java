package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe d'application JavaFX principale
 * Démarre l'interface graphique de gestion des chambres d'hôtel
 */
public class RoomsApplication extends Application {

    /**
     * Méthode de démarrage de l'application JavaFX
     * Charge le fichier FXML et affiche la fenêtre principale
     * @param stage La fenêtre principale fournie par JavaFX
     */
    @Override
    public void start(Stage stage) throws Exception {
        try {
            // Charger le fichier FXML qui définit l'interface graphique
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/rooms.fxml"));
            
            // Obtenir la racine de la scène (le nœud BorderPane du FXML)
            Parent root = loader.load();

            // Créer la scène avec la racine chargée depuis le FXML
            Scene scene = new Scene(root, 1400, 800);

            // Ajouter le fichier de style CSS externe
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

            // Définir le titre de la fenêtre
            stage.setTitle("Gestion des Chambres d'Hôtel");

            // Ajouter la scène à la fenêtre
            stage.setScene(scene);

            // Afficher la fenêtre
            stage.show();

        } catch (Exception e) {
            // Afficher les erreurs en cas de problème
            System.err.println("Erreur lors du chargement de l'interface: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Point d'entrée de l'application
     * @param args Arguments de ligne de commande
     */
    public static void main(String[] args) {
        // Lancer l'application JavaFX
        launch(args);
    }
}
