package com.github.jarnaud.republican;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class RDateTimeTest {

    @Test
    public void testOf1() {
        RDateTime rdt = RDateTime.of(LocalDateTime.of(1804, 5, 18, 12, 0));
        assertEquals(RDateTime.of(12, RMonth.Floreal, 28, 5, 0), rdt);
    }

    @Test
    public void testOf7() {
        RDateTime rdt = RDateTime.of(1, RMonth.Brumaire, 3, 4, 5, 6, 700);
        assertNotNull(rdt);
        assertEquals(1, rdt.getYear());
        assertEquals(RMonth.Brumaire, rdt.getMonth());
        assertEquals(3, rdt.getDay());
        assertEquals(4, rdt.getHour());
        assertEquals(5, rdt.getMinute());
        assertEquals(6, rdt.getSecond());
        assertEquals(700, rdt.getNano());
    }

    @Test
    public void testOf2() {
        assertThrows(RuntimeException.class, () -> RDateTime.of(null, null));
        assertThrows(RuntimeException.class, () -> RDateTime.of(null, RTime.MAX));
        assertThrows(RuntimeException.class, () -> RDateTime.of(RDate.MIN, null));
        RDateTime rdt = RDateTime.of(RDate.MIN, RTime.MIN);
        assertEquals(RDateTime.of(LocalDateTime.of(1792, 9, 22, 0, 0)), rdt);
    }

    @Test
    public void testToLocalTime() {
        RDateTime rdt = RDateTime.of(27, RMonth.Frimaire, 21, 4, 63, 87);
        LocalDateTime dateTime = rdt.toLocalDateTime();
        assertEquals(1818, dateTime.getYear());
        assertEquals(12, dateTime.getMonthValue());
        assertEquals(12, dateTime.getDayOfMonth());
        assertEquals(11, dateTime.getHour());
        assertEquals(7, dateTime.getMinute());
        assertEquals(58, dateTime.getSecond());

        // Test reverse conversion.
        RDateTime rdt2 = RDateTime.of(LocalDateTime.of(1818, 12, 12, 11, 7, 58));
        assertEquals(rdt, rdt2.roundSSecond());
    }

    @Test
    public void testToString() {
        RDateTime rdt = RDateTime.of(27, RMonth.Frimaire, 21, 4, 63, 87);
        String s = rdt.toString();
        assertNotNull(s);
        assertFalse(s.isEmpty());
    }

    @Test
    public void testHashcode() {
        RDateTime rdt1 = RDateTime.of(27, RMonth.Frimaire, 21, 4, 63, 87);
        RDateTime rdt2 = RDateTime.of(27, RMonth.Frimaire, 21, 4, 63, 87);
        RDateTime rdt3 = RDateTime.of(27, RMonth.Frimaire, 21, 4, 63, 88);
        assertEquals(rdt1.hashCode(), rdt2.hashCode());
        assertNotEquals(rdt1.hashCode(), rdt3.hashCode());
    }

    @Test
    public void testCompareTo() {
        RDateTime rdt = RDateTime.of(27, RMonth.Frimaire, 21, 4, 63, 87);
        assertEquals(-1, rdt.compareTo(null));
        assertEquals(0, rdt.compareTo(RDateTime.of(27, RMonth.Frimaire, 21, 4, 63, 87)));
        assertTrue(rdt.compareTo(RDateTime.of(27, RMonth.Frimaire, 20, 4, 63, 87)) > 0);
        assertTrue(rdt.compareTo(RDateTime.of(27, RMonth.Frimaire, 22, 4, 63, 87)) < 0);
        assertTrue(rdt.compareTo(RDateTime.of(27, RMonth.Frimaire, 21, 4, 63, 84)) > 0);
        assertTrue(rdt.compareTo(RDateTime.of(27, RMonth.Frimaire, 21, 4, 63, 93)) < 0);
    }

    @Test
    public void testEquals() {
        RDateTime rdt = RDateTime.of(27, RMonth.Frimaire, 21, 4, 63, 87, 2300);
        assertEquals(rdt, rdt);
        assertNotEquals(rdt, null);
        assertNotEquals(rdt, new Object());
        assertEquals(rdt, RDateTime.of(27, RMonth.Frimaire, 21, 4, 63, 87, 2300));
        assertNotEquals(rdt, RDateTime.of(26, RMonth.Frimaire, 21, 4, 63, 87, 2300));
        assertNotEquals(rdt, RDateTime.of(27, RMonth.Brumaire, 21, 4, 63, 87, 2300));
        assertNotEquals(rdt, RDateTime.of(27, RMonth.Frimaire, 22, 4, 63, 87, 2300));
        assertNotEquals(rdt, RDateTime.of(27, RMonth.Frimaire, 21, 5, 63, 87, 2300));
        assertNotEquals(rdt, RDateTime.of(27, RMonth.Frimaire, 21, 4, 64, 87, 2300));
        assertNotEquals(rdt, RDateTime.of(27, RMonth.Frimaire, 21, 4, 63, 88, 2300));
        assertNotEquals(rdt, RDateTime.of(27, RMonth.Frimaire, 21, 4, 63, 87, 2301));
    }
}
