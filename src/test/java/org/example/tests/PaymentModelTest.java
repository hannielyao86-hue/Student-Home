package org.example.tests;

import org.example.model.Payment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentModelTest {

    @Test
    void paymentStatusAndTotals() {
        Payment p = new Payment("PAY100", 500.0, "2026-05-01", Payment.STATUT_RETARD, 50.0);

        assertEquals("PAY100", p.getIdPayment());
        assertTrue(p.isEnRetard());
        assertFalse(p.isPaye());
        assertEquals(550.0, p.getMontantTotal(), 0.0001);

        p.setStatutPaiement(Payment.STATUT_PAYE);
        assertTrue(p.isPaye());
    }
}
