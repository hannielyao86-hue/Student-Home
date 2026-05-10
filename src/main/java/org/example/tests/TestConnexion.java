package org.example.tests;

import org.example.config.DatabaseConnection;

public class TestConnexion {
    public static void main(String[] args) {
        // Si ce message s'affiche sans erreur → connexion OK !
        DatabaseConnection.getInstance();
        System.out.println("Test terminé !");
    }
}