package com.github.jarnaud;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RDateTest {

    @Test
    public void testDecade() {
        assertEquals(1,RDate.of(1795, RMonth.Floreal, 1).getDecade());
        assertEquals(1,RDate.of(1795, RMonth.Floreal, 4).getDecade());
        assertEquals(1,RDate.of(1795, RMonth.Floreal, 10).getDecade());
        assertEquals(2,RDate.of(1795, RMonth.Floreal, 11).getDecade());
        assertEquals(2,RDate.of(1795, RMonth.Floreal, 14).getDecade());
        assertEquals(2,RDate.of(1795, RMonth.Floreal, 20).getDecade());
        assertEquals(3,RDate.of(1795, RMonth.Floreal, 21).getDecade());
        assertEquals(3,RDate.of(1795, RMonth.Floreal, 24).getDecade());
        assertEquals(3,RDate.of(1795, RMonth.Floreal, 30).getDecade());
    }

    @Test
    public void isBeforeTest() {
        RDate d1 = RDate.of(1795, RMonth.Floreal, 4);
        RDate d2 = RDate.of(1795, RMonth.Floreal, 5);
        assertTrue(d1.isBefore(d2));
        assertFalse(d2.isBefore(d1));
        assertFalse(d1.isBefore(d1));
    }
}
