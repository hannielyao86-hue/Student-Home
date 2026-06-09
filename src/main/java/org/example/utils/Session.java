package org.example.utils;

import org.example.model.Student;

/**
 * Session — Gestion de la session utilisateur côté application.
 *
 * Cette classe stocke l'étudiant connecté de manière statique
 * pour pouvoir le réutiliser depuis n'importe quel contrôleur.
 */
public class Session {

    // Étudiant actuellement connecté
    private static Student loggedInStudent;

    /**
     * Définit l'étudiant connecté dans la session.
     *
     * @param student instance de Student à stocker
     */
    public static void setLoggedInStudent(Student student) {
        loggedInStudent = student;
    }

    /**
     * Récupère l'étudiant connecté.
     *
     * @return Student connecté ou null si aucun
     */
    public static Student getLoggedInStudent() {
        return loggedInStudent;
    }

    /**
     * Vide la session en supprimant l'étudiant connecté.
     */
    public static void clear() {
        loggedInStudent = null;
    }
}