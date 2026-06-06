package org.example.model;

public class StudentDisplay {
    private final int idStudent;
    private final String nom;
    private final String prenom;

    public StudentDisplay(int idStudent, String nom, String prenom) {
        this.idStudent = idStudent;
        this.nom = nom;
        this.prenom = prenom;
    }

    public int getIdStudent() { return idStudent; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }

    @Override
    public String toString() {
        return nom.toUpperCase() + " " + prenom;
    }
}