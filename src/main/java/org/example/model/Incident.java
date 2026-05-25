package org.example.model;

import java.time.LocalDate;

public class Incident {

    private String idIncident;
    private String typeIncident;
    private String description;
    private String priorite;
    private String statutIncident;
    private LocalDate dateSignalement;
    private int idStudent;

    // Constructeur
    public Incident(String typeIncident, String description,
                    String priorite, String statutIncident,
                    LocalDate dateSignalement, int idStudent) {
        this.typeIncident    = typeIncident;
        this.description     = description;
        this.priorite        = priorite;
        this.statutIncident  = statutIncident;
        this.dateSignalement = dateSignalement;
        this.idStudent       = idStudent;
    }

    // Getters
    public String getIdIncident()        { return idIncident; }
    public String getTypeIncident()      { return typeIncident; }
    public String getDescription()       { return description; }
    public String getPriorite()          { return priorite; }
    public String getStatutIncident()    { return statutIncident; }
    public LocalDate getDateSignalement(){ return dateSignalement; }
    public int getIdStudent()            { return idStudent; }

    // Setter id (après insertion)
    public void setIdIncident(String idIncident) { this.idIncident = idIncident; }
}