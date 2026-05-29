package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.example.config.DatabaseConnection;
import org.example.model.Contract;

/**
 * DAO Contract
 */
public class ContractDAO {

    /**
     * Ajouter un contrat
     */
    public void createContract(Contract contract) {

        String sql = "INSERT INTO contract " +
                "(id_contract, _date_debut, date_fin, caution, " +
                "statut_contract, id_payment, id_room, id_student) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {

            Connection connection =
                    DatabaseConnection.getInstance().getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setString(1, contract.getIdContract());
            statement.setString(2, contract.getStartDate());
            statement.setString(3, contract.getEndDate());
            statement.setDouble(4, contract.getCaution());
            statement.setString(5, contract.getStatutContract());
            statement.setString(6, contract.getIdPayment());
            statement.setInt(7, contract.getIdRoom());
            statement.setInt(8, contract.getIdStudent());

            statement.executeUpdate();

            System.out.println("✔ Contrat ajouté avec succès !");

        } catch (Exception e) {

            System.out.println("❌ Erreur lors de l'ajout du contrat");

            e.printStackTrace();
        }
    }

    /**
     * Retourne le dernier contrat (par date_debut) pour une chambre donnée
     */
    public Contract getLatestContractByRoom(int roomId) {
        String sql = "SELECT * FROM contract WHERE id_room = ? ORDER BY _date_debut DESC LIMIT 1";
        try (
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Contract(
                            rs.getString("id_contract"),
                            rs.getDate("_date_debut").toString(),
                            rs.getDate("date_fin").toString(),
                            rs.getDouble("caution"),
                            rs.getString("statut_contract"),
                            rs.getString("id_payment"),
                            rs.getInt("id_room"),
                            rs.getInt("id_student")
                    );
                }
            }
        } catch (Exception e) {
            System.err.println("ContractDAO getLatestContractByRoom error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}