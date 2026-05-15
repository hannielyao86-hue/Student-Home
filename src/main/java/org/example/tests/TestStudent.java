package org.example.tests;

import org.example.dao.StudentDAO;
import org.example.model.Student;
import java.util.List;

/**
 * Test CRUD Student
 * Lance ce fichier pour vérifier que tout fonctionne avec MySQL
 */
public class TestStudent {

    public static void main(String[] args) {

        StudentDAO dao = new StudentDAO();

        // ── CREATE ────────────────────────────────────────────────
        // On crée un étudiant test
        // idStudent = 0 car AUTO_INCREMENT (MySQL génère l'ID)
        // idUsers   = 1 (utilisateur existant en BDD)
        Student s = new Student(
                0,
                "ET099",
                "ESPRIT",
                "55667788",
                "2026-05-14",
                "2027-05-14",
                1
        );

        dao.createStudent(s);  // ← méthode correcte

        // ── READ ALL ──────────────────────────────────────────────
        // On affiche tous les étudiants
        List<Student> liste = dao.getAllStudents();

        System.out.println("\n===== LISTE ÉTUDIANTS =====");
        for (Student etudiant : liste) {
            System.out.println(etudiant.toString());
        }

        // ── COUNT ─────────────────────────────────────────────────
        System.out.println("\nNombre total : " + dao.countStudents());

        System.out.println("\n✅ TEST STUDENT TERMINÉ");
    }
}
