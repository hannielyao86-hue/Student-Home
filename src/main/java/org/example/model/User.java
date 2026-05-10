package org.example.model;

import java.time.LocalDateTime;

/**
 * Classe User liée à la table Users de la base de données
 */
public class User {

    private int idUsers;
    private String nomUsers;
    private String prenomUser;
    private String emailUser;
    private String motPasseHash;
    private String statut;
    private LocalDateTime dateCreation;

    // Relation avec Role (option simple)
    private int roleId;

    public User() {
    }

    public User(int idUsers, String nomUsers, String prenomUser,
                String emailUser, String motPasseHash,
                String statut, LocalDateTime dateCreation, int roleId) {

        this.idUsers = idUsers;
        this.nomUsers = nomUsers;
        this.prenomUser = prenomUser;
        this.emailUser = emailUser;
        this.motPasseHash = motPasseHash;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.roleId = roleId;
    }

    // GETTERS & SETTERS

    public int getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(int idUsers) {
        this.idUsers = idUsers;
    }

    public String getNomUsers() {
        return nomUsers;
    }

    public void setNomUsers(String nomUsers) {
        this.nomUsers = nomUsers;
    }

    public String getPrenomUser() {
        return prenomUser;
    }

    public void setPrenomUser(String prenomUser) {
        this.prenomUser = prenomUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getMotPasseHash() {
        return motPasseHash;
    }

    public void setMotPasseHash(String motPasseHash) {
        this.motPasseHash = motPasseHash;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}