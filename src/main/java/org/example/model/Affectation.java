
package org.example.model;

public class Affectation {

    private Student student;
    private Room room;
    private String affectationDate;

    public Affectation(Student student,
                       Room room,
                       String affectationDate) {

        this.student = student;
        this.room = room;
        this.affectationDate = affectationDate;
    }

    // ===== GETTERS =====

    public Student getStudent() {
        return student;
    }

    public Room getRoom() {
        return room;
    }

    public String getAffectationDate() {
        return affectationDate;
    }

    // ===== SETTERS =====

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setAffectationDate(String affectationDate) {
        this.affectationDate = affectationDate;
    }

    @Override
    public String toString() {
        return "Affectation{" +
                "student=" + student +
                ", room=" + room +
                ", affectationDate='" + affectationDate + '\'' +
                '}';
    }
}