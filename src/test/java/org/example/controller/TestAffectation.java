package org.example.tests;

import org.example.model.Student;

public class TestAffectation {

    public static void main(String[] args) {

        Student student = new Student(
                1,
                "Ahmed",
                "Ben Ali",
                "ahmed@gmail.com",
                "ESPRIT",
                "22114455",
                "2026-05-20",
                "2026-12-30",
                1
        );

        System.out.println(student);
    }
}