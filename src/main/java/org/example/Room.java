package org.example;

import javafx.beans.property.*;

/**
 * Classe modèle représentant une chambre d'hôtel
 * Utilise les propriétés JavaFX pour une liaison dynamique avec l'interface graphique
 */
public class Room {
    // Identifiant unique de la chambre (ex: A101, B202)
    private final StringProperty roomNumber = new SimpleStringProperty();
    
    // État actuel de la chambre (OCCUPIED, FREE, RESERVED)
    private final ObjectProperty<RoomStatus> status = new SimpleObjectProperty<>();
    
    // Type de chambre: "Single" ou "Double"
    private final StringProperty type = new SimpleStringProperty();
    
    // Nombre de personnes actuellement dans la chambre
    private final IntegerProperty occupants = new SimpleIntegerProperty();
    
    // Nom du client occupant ou ayant réservé la chambre
    private final StringProperty clientName = new SimpleStringProperty();
    
    // Numéro de contact du client
    private final StringProperty contact = new SimpleStringProperty();
    
    // Date de début du contrat (format: JJ/MM/YYYY)
    private final StringProperty startDate = new SimpleStringProperty();
    
    // Date de fin du contrat (format: JJ/MM/YYYY)
    private final StringProperty endDate = new SimpleStringProperty();
    
    // Montant du loyer en euros
    private final DoubleProperty leaseAmount = new SimpleDoubleProperty();

    /**
     * Constructeur par défaut
     */
    public Room() {
    }

    /**
     * Constructeur avec paramètres principaux
     * @param roomNumber Numéro de la chambre
     * @param status État de la chambre
     * @param type Type de chambre (Single ou Double)
     * @param occupants Nombre d'occupants
     */
    public Room(String roomNumber, RoomStatus status, String type, int occupants) {
        this.roomNumber.set(roomNumber);
        this.status.set(status);
        this.type.set(type);
        this.occupants.set(occupants);
    }

    // ============= GETTERS POUR LES PROPRIÉTÉS =============

    public StringProperty roomNumberProperty() {
        return roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber.get();
    }

    public ObjectProperty<RoomStatus> statusProperty() {
        return status;
    }

    public RoomStatus getStatus() {
        return status.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public String getType() {
        return type.get();
    }

    public IntegerProperty occupantsProperty() {
        return occupants;
    }

    public int getOccupants() {
        return occupants.get();
    }

    public StringProperty clientNameProperty() {
        return clientName;
    }

    public String getClientName() {
        return clientName.get();
    }

    public StringProperty contactProperty() {
        return contact;
    }

    public String getContact() {
        return contact.get();
    }

    public StringProperty startDateProperty() {
        return startDate;
    }

    public String getStartDate() {
        return startDate.get();
    }

    public StringProperty endDateProperty() {
        return endDate;
    }

    public String getEndDate() {
        return endDate.get();
    }

    public DoubleProperty leaseAmountProperty() {
        return leaseAmount;
    }

    public double getLeaseAmount() {
        return leaseAmount.get();
    }

    // ============= SETTERS POUR LES PROPRIÉTÉS =============

    public void setRoomNumber(String roomNumber) {
        this.roomNumber.set(roomNumber);
    }

    public void setStatus(RoomStatus status) {
        this.status.set(status);
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public void setOccupants(int occupants) {
        this.occupants.set(occupants);
    }

    public void setClientName(String clientName) {
        this.clientName.set(clientName);
    }

    public void setContact(String contact) {
        this.contact.set(contact);
    }

    public void setStartDate(String startDate) {
        this.startDate.set(startDate);
    }

    public void setEndDate(String endDate) {
        this.endDate.set(endDate);
    }

    public void setLeaseAmount(double leaseAmount) {
        this.leaseAmount.set(leaseAmount);
    }

    /**
     * Retourne une représentation textuelle de la chambre
     * @return String descriptif de la chambre
     */
    @Override
    public String toString() {
        return "Room{" +
                "roomNumber='" + getRoomNumber() + '\'' +
                ", status=" + getStatus() +
                ", type='" + getType() + '\'' +
                ", occupants=" + getOccupants() +
                '}';
    }
}
