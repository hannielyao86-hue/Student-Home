package org.example.tests;

import org.example.dao.StudentDAO;
import org.example.model.Student;

import java.util.List;

public class TestStudent {

    public static void main(String[] args) {

        StudentDAO dao = new StudentDAO();

        // ═══════════════════════════════════════════════
        // TEST AJOUT
        // ═══════════════════════════════════════════════

        Student student = new Student(
                0,
                "Ahmed",
                "Ben Ali",
                "ahmed@gmail.com",
                "ESPRIT",
                "22114455",
                "2026-05-20",
                "2026-12-30",
                1
        );

        dao.createStudent(student);

        System.out.println("✅ Étudiant ajouté");

        // ═══════════════════════════════════════════════
        // TEST AFFICHAGE
        // ═══════════════════════════════════════════════

        List<Student> students = dao.getAllStudents();

        System.out.println("\n══════ LISTE DES ÉTUDIANTS ═════=\n");

        for (Student s : students) {

            System.out.println(
                    "ID : " + s.getIdStudent()
            );

            System.out.println(
                    "Nom : " + s.getNom()
            );

            System.out.println(
                    "Prénom : " + s.getPrenom()
            );

            System.out.println(
                    "Email : " + s.getEmail()
            );

            System.out.println(
                    "École : " + s.getEcole()
            );

            System.out.println(
                    "Téléphone : " + s.getTelephone()
            );

            System.out.println(
                    "Date entrée : " + s.getDateEntree()
            );

            System.out.println(
                    "Date sortie : " + s.getDateSortie()
            );

            System.out.println(
                    "ID User : " + s.getIdUsers()
            );

            System.out.println("-----------------------------------");
        }

        // ═══════════════════════════════════════════════
        // TEST COUNT
        // ═══════════════════════════════════════════════

        int total = dao.countStudents();

        System.out.println("\n📌 Nombre total étudiants : " + total);

        // ═══════════════════════════════════════════════
        // TEST UPDATE
        // ═══════════════════════════════════════════════

        if (!students.isEmpty()) {

            Student first = students.get(0);

            first.setTelephone("99999999");

            dao.updateStudent(first);

            System.out.println("✅ Téléphone modifié");
        }

        // ═══════════════════════════════════════════════
        // TEST DELETE
        // ═══════════════════════════════════════════════

        /*
        dao.deleteStudent(1);

        System.out.println("✅ Étudiant supprimé");
        */
    }
}