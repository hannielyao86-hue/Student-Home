package org.example.model;

/**
 * Classe Contract
 */
public class Contract {

    private String idContract;
    private String startDate;
    private String endDate;
    private double caution;
    private String statutContract;

    private String idPayment;
    private int idRoom;
    private int idStudent;

    /**
     * Constructeur
     */
    public Contract(String idContract,
                    String startDate,
                    String endDate,
                    double caution,
                    String statutContract,
                    String idPayment,
                    int idRoom,
                    int idStudent) {

        this.idContract = idContract;
        this.startDate = startDate;
        this.endDate = endDate;
        this.caution = caution;
        this.statutContract = statutContract;
        this.idPayment = idPayment;
        this.idRoom = idRoom;
        this.idStudent = idStudent;
    }

    // ===== GETTERS =====

    public String getIdContract() {
        return idContract;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public double getCaution() {
        return caution;
    }

    public String getStatutContract() {
        return statutContract;
    }

    public String getIdPayment() {
        return idPayment;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public int getIdStudent() {
        return idStudent;
    }

    // ===== SETTERS =====

    public void setIdContract(String idContract) {
        this.idContract = idContract;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setCaution(double caution) {
        this.caution = caution;
    }

    public void setStatutContract(String statutContract) {
        this.statutContract = statutContract;
    }

    public void setIdPayment(String idPayment) {
        this.idPayment = idPayment;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    // ===== toString =====

    @Override
    public String toString() {
        return "Contract{" +
                "idContract='" + idContract + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", caution=" + caution +
                ", statutContract='" + statutContract + '\'' +
                ", idPayment='" + idPayment + '\'' +
                ", idRoom=" + idRoom +
                ", idStudent=" + idStudent +
                '}';
    }
}