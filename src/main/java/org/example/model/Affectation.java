package org.example.model;

public class Affectation {

    // ================= ATTRIBUTS =================

    private Integer id;
    private Student student;
    private Room room;
    private String dateEntree;
    private String dateSortie;

    // ================= CONSTRUCTEURS =================

    // Constructeur complet (celui utilisé par ton contrôleur)
    public Affectation(Integer id, Student student, Room room, String dateEntree, String dateSortie) {
        this.id = id;
        this.student = student;
        this.room = room;
        this.dateEntree = dateEntree;
        this.dateSortie = dateSortie;
    }

    // Constructeur vide (utile pour les frameworks)
    public Affectation() {
    }

    // ================= GETTERS =================

    public Integer getId() { return id; }
    public Student getStudent() { return student; }
    public Room getRoom() { return room; }
    public String getDateEntree() { return dateEntree; }
    public String getDateSortie() { return dateSortie; }

    // ================= SETTERS =================

    public void setId(Integer id) { this.id = id; }
    public void setStudent(Student student) { this.student = student; }
    public void setRoom(Room room) { this.room = room; }
    public void setDateEntree(String dateEntree) { this.dateEntree = dateEntree; }
    public void setDateSortie(String dateSortie) { this.dateSortie = dateSortie; }

    // ================= toString =================

    @Override
    public String toString() {
        return "Affectation{" +
                "id=" + id +
                ", student=" + (student != null ? student.getIdStudent() : "null") +
                ", room=" + (room != null ? room.getNumeroRoom() : "null") +
                ", dateEntree='" + dateEntree + '\'' +
                ", dateSortie='" + dateSortie + '\'' +
                '}';
    }
}