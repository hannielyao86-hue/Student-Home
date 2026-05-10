package org.example.model;

/*
Classe Affectation

Cette classe représente
l'affectation d’un étudiant
à une chambre.
*/

public class Affectation {

    // ID étudiant
    private int studentId;

    // ID chambre
    private int roomId;

    // Date affectation
    private String affectationDate;


    /*
    Constructeur :
    permet de créer une affectation
    */
    public Affectation(int studentId,
                       int roomId,
                       String affectationDate) {

        this.studentId = studentId;

        this.roomId = roomId;

        this.affectationDate = affectationDate;
    }


    /*
    Getter studentId
    */
    public int getStudentId() {

        return studentId;
    }


    /*
    Getter roomId
    */
    public int getRoomId() {

        return roomId;
    }


    /*
    Getter affectationDate
    */
    public String getAffectationDate() {

        return affectationDate;
    }
}