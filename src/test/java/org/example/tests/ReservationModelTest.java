package org.example.tests;

import org.example.model.Reservation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationModelTest {

    @Test
    void reservationSettersAndGetters() {
        Reservation r = new Reservation();
        r.setDateReservation("2026-07-20");
        r.setHeureDebut("14:00:00");
        r.setHeureFin("16:00:00");
        r.setStatutReservation("CONFIRMEE");
        r.setIdCommonSpeace(1);
        r.setIdStudent(2);

        assertEquals("2026-07-20", r.getDateReservation());
        assertEquals("14:00:00", r.getHeureDebut());
        assertEquals("16:00:00", r.getHeureFin());
        assertEquals("CONFIRMEE", r.getStatutReservation());
        assertEquals(1, r.getIdCommonSpeace());
        assertEquals(2, r.getIdStudent());
    }
}
