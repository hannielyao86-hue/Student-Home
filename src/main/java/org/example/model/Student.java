package org.example.model;

public class Student {

    private int idStudent;
    private String numeroEtudiant;
    private String ecole;
    private String telephone;
    private String dateEntree;
    private String dateSortie;

    public Student(int idStudent,
                   String numeroEtudiant,
                   String ecole,
                   String telephone,
                   String dateEntree,
                   String dateSortie) {

        this.idStudent = idStudent;
        this.numeroEtudiant = numeroEtudiant;
        this.ecole = ecole;
        this.telephone = telephone;
        this.dateEntree = dateEntree;
        this.dateSortie = dateSortie;
    }

    // ===== GETTERS =====

    public int getIdStudent() {
        return idStudent;
    }

    public String getNumeroEtudiant() {
        return numeroEtudiant;
    }

    public String getEcole() {
        return ecole;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getDateEntree() {
        return dateEntree;
    }

    public String getDateSortie() {
        return dateSortie;
    }

    // ===== SETTERS =====

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public void setNumeroEtudiant(String numeroEtudiant) {
        this.numeroEtudiant = numeroEtudiant;
    }

    public void setEcole(String ecole) {
        this.ecole = ecole;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setDateEntree(String dateEntree) {
        this.dateEntree = dateEntree;
    }

    public void setDateSortie(String dateSortie) {
        this.dateSortie = dateSortie;
    }

    @Override
    public String toString() {
        return "Student{" +
                "idStudent=" + idStudent +
                ", numeroEtudiant='" + numeroEtudiant + '\'' +
                ", ecole='" + ecole + '\'' +
                '}';
    }
}