package com.github.jarnaud.republican;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RDateTest {

    @Test
    public void testOf1() {
        assertEquals(RMonth.Vendemiaire, RDate.of(1, RMonth.Vendemiaire, 1).getMonth());
        assertThrows(RuntimeException.class, () -> RDate.of(1, null, 1));
        assertThrows(RuntimeException.class, () -> RDate.of(-5, null, 1));
        assertThrows(RuntimeException.class, () -> RDate.of(12, RMonth.Sanculottide, 7));
    }

    @Test
    public void testOf2() {
        assertEquals(RMonth.Vendemiaire, RDate.of(1, 1, 1).getMonth());
        assertThrows(RuntimeException.class, () -> RDate.of(1, 0, 1));
        assertEquals(RDate.of(58, RMonth.Floreal, 1), RDate.of(58, 8, 1));
    }

    @Test
    public void testOf3() {
        assertThrows(RuntimeException.class, () -> RDate.of(LocalDate.of(1650, 1, 1)));
        // Normal cases are handled by the converter test.
    }

    @Test
    public void testDecade() {
        assertEquals(1, RDate.of(6, RMonth.Floreal, 1).getDecade());
        assertEquals(1, RDate.of(6, RMonth.Floreal, 4).getDecade());
        assertEquals(1, RDate.of(6, RMonth.Floreal, 10).getDecade());
        assertEquals(2, RDate.of(6, RMonth.Floreal, 11).getDecade());
        assertEquals(2, RDate.of(6, RMonth.Floreal, 14).getDecade());
        assertEquals(2, RDate.of(6, RMonth.Floreal, 20).getDecade());
        assertEquals(3, RDate.of(6, RMonth.Floreal, 21).getDecade());
        assertEquals(3, RDate.of(6, RMonth.Floreal, 24).getDecade());
        assertEquals(3, RDate.of(6, RMonth.Floreal, 30).getDecade());
    }

    @Test
    public void testIsBefore() {
        RDate d1 = RDate.of(6, RMonth.Floreal, 4);
        RDate d2 = RDate.of(6, RMonth.Floreal, 5);
        assertTrue(d1.isBefore(d2));
        assertFalse(d2.isBefore(d1));
        assertFalse(d1.isBefore(d1));
    }

    @Test
    public void testIsSextile() {
        assertFalse(RDate.of(1, RMonth.Floreal, 4).isSextile());
        assertFalse(RDate.of(2, RMonth.Floreal, 4).isSextile());
        assertTrue(RDate.of(3, RMonth.Floreal, 4).isSextile());
        assertFalse(RDate.of(4, RMonth.Floreal, 4).isSextile());
        assertFalse(RDate.of(5, RMonth.Floreal, 4).isSextile());
        assertTrue(RDate.of(20, RMonth.Floreal, 4).isSextile());
        assertFalse(RDate.of(100, RMonth.Floreal, 4).isSextile());
        assertFalse(RDate.of(200, RMonth.Floreal, 4).isSextile());
        assertFalse(RDate.of(300, RMonth.Floreal, 4).isSextile());
        assertTrue(RDate.of(400, RMonth.Floreal, 4).isSextile());
    }

    @Test
    public void testPlusDays_success() {
        assertEquals(RDate.of(1, 1, 1), RDate.of(1, 1, 1).plusDays(0));
        assertEquals(RDate.of(1, 1, 2), RDate.of(1, 1, 1).plusDays(1));
        assertEquals(RDate.of(1, 8, 1), RDate.of(1, 1, 1).plusDays(7 * 30));
        assertEquals(RDate.of(2, 1, 1), RDate.of(1, 1, 1).plusDays(365));
    }

    @Test
    public void testPlusDays_invalid() {
        assertThrows(RuntimeException.class, () -> RDate.of(1, 1, 1).plusDays(-1));
    }

    @Test
    public void testCompareTo() {
        RDate rd = RDate.of(12, RMonth.Brumaire, 18);
        assertEquals(-1, rd.compareTo(null));
        assertTrue(rd.compareTo(RDate.of(13, 2, 18)) < 0);
        assertTrue(rd.compareTo(RDate.of(11, 2, 18)) > 0);
        assertTrue(rd.compareTo(RDate.of(12, 3, 18)) < 0);
        assertTrue(rd.compareTo(RDate.of(12, 1, 18)) > 0);
        assertTrue(rd.compareTo(RDate.of(12, 2, 19)) < 0);
        assertTrue(rd.compareTo(RDate.of(12, 2, 17)) > 0);
        assertEquals(0, rd.compareTo(RDate.of(12, 2, 18)));
    }

    @Test
    public void testEquals() {
        RDate rd1 = RDate.of(12, RMonth.Brumaire, 18);
        RDate rd2 = RDate.of(12, 2, 18);
        assertEquals(rd1, rd2);
        assertNotEquals(rd1, new Object());
        assertNotEquals(rd1, null);
        assertEquals(rd2.hashCode(), rd1.hashCode());
    }
}
