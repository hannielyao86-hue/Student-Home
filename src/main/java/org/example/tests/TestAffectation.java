package org.example.tests;

import org.example.dao.AffectationDAO;
import org.example.model.Affectation;
import org.example.model.Room;
import org.example.model.Student;

public class TestAffectation {

    public static void main(String[] args) {

        // ===== Student =====

        Student student =
                new Student(
                        1,
                        "ET001",
                        "ESPRIT",
                        "12345678",
                        "2026-05-10",
                        "2027-05-10"
                );

        // ===== Room =====

        Room room =
                new Room(
                        1,
                        "A101",
                        "SIMPLE",
                        1,
                        "LIBRE",
                        500
                );

        // ===== Affectation =====

        Affectation affectation =
                new Affectation(
                        student,
                        room,
                        "2026-05-13"
                );

        // ===== DAO =====

        AffectationDAO dao =
                new AffectationDAO();

        dao.createAffectation(affectation);

        System.out.println(
                "✔ TEST TERMINÉ"
        );
    }
}