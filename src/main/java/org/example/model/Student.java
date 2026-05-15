package org.example.model;

/**
 * ════════════════════════════════════════════════════════════
 * Student — Modèle étudiant
 * ════════════════════════════════════════════════════════════
 *
 * Représente un étudiant dans l'application.
 * Correspond exactement à la table "students" en base de données.
 *
 * Colonnes BDD :
 *   id_student       INT AUTO_INCREMENT (PK)
 *   numero_etudiant  VARCHAR
 *   ecole            VARCHAR
 *   telephone        VARCHAR
 *   date_entree      DATE
 *   date_sortie      DATE
 *   id_users         INT (FK → users)
 *
 * @author Smart Student Residence Team
 * @version 1.0
 * ════════════════════════════════════════════════════════════
 */
public class Student {

    // ════════════════════════════════════════════════════════════════════════
    // ATTRIBUTS — correspondent exactement aux colonnes de la table students
    // ════════════════════════════════════════════════════════════════════════

    // Identifiant unique (AUTO_INCREMENT en BDD, géré par MySQL)
    private int idStudent;

    // Numéro étudiant (ex: ET001, ET002...)
    private String numeroEtudiant;

    // École ou université de l'étudiant (ex: ESPRIT, ISI...)
    private String ecole;

    // Numéro de téléphone
    private String telephone;

    // Date d'entrée dans la résidence (format : YYYY-MM-DD)
    private String dateEntree;

    // Date de sortie de la résidence (format : YYYY-MM-DD)
    private String dateSortie;

    // Référence vers l'utilisateur lié (FK → users.id_users)
    // Chaque étudiant est lié à un compte utilisateur pour la connexion
    private int idUsers;

    // ════════════════════════════════════════════════════════════════════════
    // CONSTRUCTEUR COMPLET
    //
    // Utilisé partout dans le projet (DAO, Controller, Tests).
    // Les 7 paramètres correspondent aux 7 colonnes de la table.
    //
    // IMPORTANT : idStudent peut être 0 lors d'un INSERT
    // car MySQL génère l'ID automatiquement (AUTO_INCREMENT).
    // ════════════════════════════════════════════════════════════════════════
    public Student(int idStudent,
                   String numeroEtudiant,
                   String ecole,
                   String telephone,
                   String dateEntree,
                   String dateSortie,
                   int idUsers) {

        this.idStudent      = idStudent;
        this.numeroEtudiant = numeroEtudiant;
        this.ecole          = ecole;
        this.telephone      = telephone;
        this.dateEntree     = dateEntree;
        this.dateSortie     = dateSortie;
        this.idUsers        = idUsers;
    }

    // ════════════════════════════════════════════════════════════════════════
    // GETTERS — utilisés par JavaFX TableView pour afficher les données
    //
    // ATTENTION : les noms de ces méthodes doivent être EXACTS.
    // JavaFX les appelle automatiquement via PropertyValueFactory.
    // ════════════════════════════════════════════════════════════════════════

    /** Retourne l'identifiant de l'étudiant */
    public int getIdStudent()           { return idStudent; }

    /** Retourne le numéro étudiant (ex: ET001) */
    public String getNumeroEtudiant()   { return numeroEtudiant; }

    /** Retourne l'école */
    public String getEcole()            { return ecole; }

    /** Retourne le téléphone */
    public String getTelephone()        { return telephone; }

    /** Retourne la date d'entrée */
    public String getDateEntree()       { return dateEntree; }

    /** Retourne la date de sortie */
    public String getDateSortie()       { return dateSortie; }

    /** Retourne l'ID de l'utilisateur lié */
    public int getIdUsers()             { return idUsers; }

    // ════════════════════════════════════════════════════════════════════════
    // SETTERS — permettent de modifier les attributs après création
    // ════════════════════════════════════════════════════════════════════════

    public void setIdStudent(int idStudent)             { this.idStudent = idStudent; }
    public void setNumeroEtudiant(String numeroEtudiant){ this.numeroEtudiant = numeroEtudiant; }
    public void setEcole(String ecole)                  { this.ecole = ecole; }
    public void setTelephone(String telephone)          { this.telephone = telephone; }
    public void setDateEntree(String dateEntree)        { this.dateEntree = dateEntree; }
    public void setDateSortie(String dateSortie)        { this.dateSortie = dateSortie; }
    public void setIdUsers(int idUsers)                 { this.idUsers = idUsers; }

    // ════════════════════════════════════════════════════════════════════════
    // toString() — représentation textuelle de l'objet
    //
    // Utile pour les logs et le débogage.
    // ════════════════════════════════════════════════════════════════════════
    @Override
    public String toString() {
        return "Student{" +
                "id=" + idStudent +
                ", numero='" + numeroEtudiant + "'" +
                ", ecole='" + ecole + "'" +
                ", telephone='" + telephone + "'" +
                ", dateEntree='" + dateEntree + "'" +
                ", dateSortie='" + dateSortie + "'" +
                ", idUsers=" + idUsers +
                "}";
    }
}