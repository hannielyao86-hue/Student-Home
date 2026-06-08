package org.example.model;

public class Room {

    private int idRoom;
    private String numeroRoom;
    private String typeRoom;
    private int capaciterRoom;
    private String statutRoom;
    private double loyer;

    public Room() {
    }

    public Room(int idRoom, String numeroRoom, String typeRoom, int capaciterRoom, String statutRoom, double loyer) {
        this.idRoom = idRoom;
        this.numeroRoom = numeroRoom;
        this.typeRoom = typeRoom;
        this.capaciterRoom = capaciterRoom;
        this.statutRoom = statutRoom;
        this.loyer = loyer;
    }

    // ===== GETTERS =====
    public int getIdRoom() { return idRoom; }
    public String getNumeroRoom() { return numeroRoom; }
    public String getTypeRoom() { return typeRoom; }
    public int getCapaciterRoom() { return capaciterRoom; }
    public String getStatutRoom() { return statutRoom; }
    public double getLoyer() { return loyer; }

    // ===== SETTERS =====
    public void setIdRoom(int idRoom) { this.idRoom = idRoom; }
    public void setNumeroRoom(String numeroRoom) { this.numeroRoom = numeroRoom; }
    public void setTypeRoom(String typeRoom) { this.typeRoom = typeRoom; }
    public void setCapaciterRoom(int capaciterRoom) { this.capaciterRoom = capaciterRoom; }
    public void setStatutRoom(String statutRoom) { this.statutRoom = statutRoom; }
    public void setLoyer(double loyer) { this.loyer = loyer; }

    @Override
    public String toString() {
        return "Room{" + "idRoom=" + idRoom + ", numeroRoom='" + numeroRoom + '\'' + '}';
    }
}