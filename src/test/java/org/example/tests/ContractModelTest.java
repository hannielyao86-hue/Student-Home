package org.example.tests;

import org.example.model.Contract;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContractModelTest {

    @Test
    void contractFields() {
        Contract c = new Contract("CT01", "2026-05-10", "2027-05-10", 1500.0, "ACTIF", "PAY001", 1, 2);

        assertEquals("CT01", c.getIdContract());
        assertEquals("2026-05-10", c.getStartDate());
        assertEquals("2027-05-10", c.getEndDate());
        assertEquals(1500.0, c.getCaution(), 0.001);
        assertEquals("ACTIF", c.getStatutContract());
        assertEquals("PAY001", c.getIdPayment());
        assertEquals(1, c.getIdRoom());
        assertEquals(2, c.getIdStudent());
    }
}
