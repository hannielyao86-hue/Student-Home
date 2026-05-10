package org.example;

/**
 * Énumération représentant les différents états possibles d'une chambre
 */
public enum RoomStatus {
    // Chambre occupée (un client y habite)
    OCCUPIED("Occupé", "#DC3545"), // Rouge
    
    // Chambre libre (disponible pour la location)
    FREE("Libre", "#28A745"),      // Vert
    
    // Chambre réservée (client a une réservation)
    RESERVED("Réservé", "#FFC107"); // Orange

    // Description de l'état
    private final String description;
    
    // Couleur associée pour l'affichage graphique
    private final String color;

    /**
     * Constructeur de l'énumération RoomStatus
     * @param description Description textuelle de l'état
     * @param color Code couleur hexadécimal pour l'interface graphique
     */
    RoomStatus(String description, String color) {
        this.description = description;
        this.color = color;
    }

    /**
     * Obtient la description de l'état
     * @return Description textuelle
     */
    public String getDescription() {
        return description;
    }

    /**
     * Obtient la couleur associée à l'état
     * @return Code couleur hexadécimal
     */
    public String getColor() {
        return color;
    }
}
