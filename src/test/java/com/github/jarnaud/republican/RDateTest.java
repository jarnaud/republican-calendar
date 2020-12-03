package com.github.jarnaud.republican;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RDateTest {

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
    public void isBeforeTest() {
        RDate d1 = RDate.of(6, RMonth.Floreal, 4);
        RDate d2 = RDate.of(6, RMonth.Floreal, 5);
        assertTrue(d1.isBefore(d2));
        assertFalse(d2.isBefore(d1));
        assertFalse(d1.isBefore(d1));
    }

    @Test
    public void isSextileTest() {
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
}
