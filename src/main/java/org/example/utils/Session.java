package org.example.utils;

import org.example.model.Student;

public class Session {
    // Variable statique pour stocker l'étudiant connecté
    private static Student loggedInStudent;

    // Méthode pour définir l'étudiant lors du login
    public static void setLoggedInStudent(Student student) {
        loggedInStudent = student;
    }

    // Méthode pour récupérer l'étudiant partout dans l'application
    public static Student getLoggedInStudent() {
        return loggedInStudent;
    }

    // Méthode pour nettoyer la session lors de la déconnexion
    public static void clear() {
        loggedInStudent = null;
    }
}