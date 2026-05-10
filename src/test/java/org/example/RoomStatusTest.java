package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomStatusTest {

    @Test
    void testRoomStatusValues() {
        assertEquals("Occupé", RoomStatus.OCCUPIED.getDescription());
        assertEquals("#DC3545", RoomStatus.OCCUPIED.getColor());

        assertEquals("Libre", RoomStatus.FREE.getDescription());
        assertEquals("#28A745", RoomStatus.FREE.getColor());

        assertEquals("Réservé", RoomStatus.RESERVED.getDescription());
        assertEquals("#FFC107", RoomStatus.RESERVED.getColor());
    }

    @Test
    void testAllStatusesPresent() {
        RoomStatus[] statuses = RoomStatus.values();

        assertArrayEquals(new RoomStatus[]{RoomStatus.OCCUPIED, RoomStatus.FREE, RoomStatus.RESERVED}, statuses);
        for (RoomStatus status : statuses) {
            assertNotNull(status.getDescription());
            assertNotNull(status.getColor());
            assertFalse(status.getDescription().isEmpty());
            assertFalse(status.getColor().isEmpty());
        }
    }
}
