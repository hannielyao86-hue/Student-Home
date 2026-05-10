package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    @Test
    void testDefaultConstructorAndSetters() {
        Room room = new Room();
        room.setRoomNumber("A101");
        room.setStatus(RoomStatus.FREE);
        room.setType("Single");
        room.setOccupants(0);
        room.setClientName("Alice");
        room.setContact("0102030405");
        room.setStartDate("01/01/2025");
        room.setEndDate("31/12/2025");
        room.setLeaseAmount(1200.50);

        assertEquals("A101", room.getRoomNumber());
        assertEquals(RoomStatus.FREE, room.getStatus());
        assertEquals("Single", room.getType());
        assertEquals(0, room.getOccupants());
        assertEquals("Alice", room.getClientName());
        assertEquals("0102030405", room.getContact());
        assertEquals("01/01/2025", room.getStartDate());
        assertEquals("31/12/2025", room.getEndDate());
        assertEquals(1200.50, room.getLeaseAmount());
    }

    @Test
    void testParameterizedConstructor() {
        Room room = new Room("B202", RoomStatus.OCCUPIED, "Double", 2);

        assertEquals("B202", room.getRoomNumber());
        assertEquals(RoomStatus.OCCUPIED, room.getStatus());
        assertEquals("Double", room.getType());
        assertEquals(2, room.getOccupants());
        assertNull(room.getClientName());
        assertNull(room.getContact());
        assertNull(room.getStartDate());
        assertNull(room.getEndDate());
        assertEquals(0.0, room.getLeaseAmount());
    }

    @Test
    void testToStringContainsKeyFields() {
        Room room = new Room("C303", RoomStatus.RESERVED, "Single", 1);
        String text = room.toString();

        assertTrue(text.contains("C303"));
        assertTrue(text.contains("RESERVED"));
        assertTrue(text.contains("Single"));
        assertTrue(text.contains("occupants=1"));
    }
}
