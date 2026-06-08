package org.example.tests;

import org.example.model.Incident;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class IncidentModelTest {

    @Test
    void incidentGettersAndIdHandling() {
        Incident inc = new Incident("Plomberie", "Fuite", "Moyen", "Ouvert", LocalDate.of(2026,5,20), 1);

        assertNull(inc.getIdIncident());
        assertEquals("Plomberie", inc.getTypeIncident());
        assertEquals("Fuite", inc.getDescription());
        assertEquals("Moyen", inc.getPriorite());
        assertEquals("Ouvert", inc.getStatutIncident());
        assertEquals(LocalDate.of(2026,5,20), inc.getDateSignalement());
        assertEquals(1, inc.getIdStudent());

        inc.setIdIncident("INC100");
        assertEquals("INC100", inc.getIdIncident());
    }
}
