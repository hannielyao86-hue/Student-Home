package org.example;

/**
 * AppLauncher — Lance l'application JavaFX.
 *
 * Cette classe est utile lorsque l'environnement d'exécution
 * demande un point d'entrée principal distinct pour le lancement
 * de l'application (par exemple certains IDE ou configurations Maven).
 */
public class AppLauncher {

    /**
     * Point d'entrée principal pour démarrer l'application.
     * Appelle la méthode main de `MainApp` pour lancer JavaFX.
     */
    public static void main(String[] args) {
        // Cette ligne appelle directement ton application JavaFX
        MainApp.main(args);
    }
}