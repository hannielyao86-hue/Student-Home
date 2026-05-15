package org.example.tests;

import org.example.dao.ContractDAO;
import org.example.model.Contract;

public class TestContract {

    public static void main(String[] args) {

        Contract contract = new Contract(
                "CT007",
                "2026-05-10",
                "2027-05-10",
                1500.00,
                "ACTIF",
                "PAY001",
                1,
                1
        );

        ContractDAO dao = new ContractDAO();

        try {
            dao.createContract(contract);
            System.out.println("✔ TEST CONTRACT RÉUSSI");
        } catch (Exception e) {
            System.out.println("❌ TEST CONTRACT ÉCHOUÉ");
            e.printStackTrace();
        }
    }
}