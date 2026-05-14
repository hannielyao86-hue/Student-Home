package org.example.dao;

import org.example.config.DatabaseConnection;
import org.example.model.Contract;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
}