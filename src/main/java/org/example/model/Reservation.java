package org.example.model;

/*
Classe Reservation

Cette classe représente
une réservation.
*/
public class Reservation {

    /*
    ATTRIBUTS
    */

    // ID réservation
    private String idReservation;

    // Date réservation
    private String dateReservation;

    // Heure début
    private String heureDebut;

    // Heure fin
    private String heureFin;

    // Statut réservation
    private String statutReservation;

    // ID espace commun
    private int idCommonSpeace;

    // ID étudiant
    private int idStudent;


    /*
    CONSTRUCTEUR VIDE
    */
    public Reservation() {
    }


    /*
    GETTERS ET SETTERS
    */

    public String getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(String idReservation) {
        this.idReservation = idReservation;
    }

    public String getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(String dateReservation) {
        this.dateReservation = dateReservation;
    }

    public String getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public String getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public String getStatutReservation() {
        return statutReservation;
    }

    public void setStatutReservation(String statutReservation) {
        this.statutReservation = statutReservation;
    }

    public int getIdCommonSpeace() {
        return idCommonSpeace;
    }

    public void setIdCommonSpeace(int idCommonSpeace) {
        this.idCommonSpeace = idCommonSpeace;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }
}