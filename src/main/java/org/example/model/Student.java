package org.example.model;

public class Student {

    // ================= ATTRIBUTS =================

    private int idStudent;
    private String nom;
    private String prenom;
    private String email;
    private String ecole;
    private String telephone;
    private String dateEntree;
    private String dateSortie;
    private int idUsers;

    // ================= CONSTRUCTEURS =================

    // Ajouté : Constructeur vide pour permettre l'instanciation sans arguments
    public Student() {
    }

    public Student(int idStudent,
                   String nom,
                   String prenom,
                   String email,
                   String ecole,
                   String telephone,
                   String dateEntree,
                   String dateSortie,
                   int idUsers) {

        this.idStudent = idStudent;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.ecole = ecole;
        this.telephone = telephone;
        this.dateEntree = dateEntree;
        this.dateSortie = dateSortie;
        this.idUsers = idUsers;
    }

    // ================= GETTERS =================

    public int getIdStudent() { return idStudent; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }
    public String getEcole() { return ecole; }
    public String getTelephone() { return telephone; }
    public String getDateEntree() { return dateEntree; }
    public String getDateSortie() { return dateSortie; }
    public int getIdUsers() { return idUsers; }

    // ================= SETTERS =================

    public void setIdStudent(int idStudent) { this.idStudent = idStudent; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setEmail(String email) { this.email = email; }
    public void setEcole(String ecole) { this.ecole = ecole; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public void setDateEntree(String dateEntree) { this.dateEntree = dateEntree; }
    public void setDateSortie(String dateSortie) { this.dateSortie = dateSortie; }
    public void setIdUsers(int idUsers) { this.idUsers = idUsers; }

    // ================= toString =================

    @Override
    public String toString() {
        return "Student{" +
                "idStudent=" + idStudent +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", ecole='" + ecole + '\'' +
                ", telephone='" + telephone + '\'' +
                ", dateEntree='" + dateEntree + '\'' +
                ", dateSortie='" + dateSortie + '\'' +
                ", idUsers=" + idUsers +
                '}';
    }
}