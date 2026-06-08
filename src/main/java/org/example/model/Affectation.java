package org.example.model;

public class Affectation {

    private Integer id;
    private Student student; // L'objet Student complet
    private Room room;
    private String dateEntree;
    private String dateSortie;

    // Constructeur utilisé par votre DAO
    public Affectation(Integer id, Student student, Room room, String dateEntree, String dateSortie) {
        this.id = id;
        this.student = student;
        this.room = room;
        this.dateEntree = dateEntree;
        this.dateSortie = dateSortie;
    }

    public Affectation() {}

    // GETTERS IMPORTANTS
    public Student getStudent() {
        return student;
    }

    public Room getRoom() {
        return room;
    }

    public Integer getId() { return id; }
    public String getDateEntree() { return dateEntree; }
    public String getDateSortie() { return dateSortie; }

    // SETTERS
    public void setStudent(Student student) { this.student = student; }
    public void setRoom(Room room) { this.room = room; }
    // ... autres setters
}