package com.github.jarnaud.republican;

import com.github.jarnaud.republican.exception.RepublicanCalendarException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RTimeTest {

    @Test
    public void testOfNanoDay_range() {
        assertThrows(RepublicanCalendarException.class, () -> RTime.ofNanoOfDay(-1));
        assertNotNull(RTime.ofNanoOfDay(0));
        assertNotNull(RTime.ofNanoOfDay(RTime.NANOS_PER_DAY - 1));
        assertThrows(RepublicanCalendarException.class, () -> RTime.ofNanoOfDay(RTime.NANOS_PER_DAY));
    }

    @Test
    public void testOf_range() {
        assertThrows(RepublicanCalendarException.class, () -> RTime.of(-1, 0, 0));
        assertThrows(RepublicanCalendarException.class, () -> RTime.of(11, 0, 0));
        assertThrows(RepublicanCalendarException.class, () -> RTime.of(0, -1, 0));
        assertThrows(RepublicanCalendarException.class, () -> RTime.of(0, 100, 0));
        assertThrows(RepublicanCalendarException.class, () -> RTime.of(0, 0, -1));
        assertThrows(RepublicanCalendarException.class, () -> RTime.of(0, 0, 100));
        assertThrows(RepublicanCalendarException.class, () -> RTime.of(0, 0, 0, -1));
        assertThrows(RepublicanCalendarException.class, () -> RTime.of(0, 0, 0, (int) RTime.NANOS_PER_SECOND));
    }

    @Test
    public void testWithNano() {
        RTime rTime = RTime.of(3, 73, 83, 27);
        assertEquals(RTime.of(3, 73, 83, 27), rTime.withNano(27));
        assertEquals(RTime.of(3, 73, 83), rTime.withNano(0));
        assertEquals(RTime.of(3, 73, 83, 89), rTime.withNano(89));
        assertEquals(RTime.of(3, 73, 83, (int) (RTime.NANOS_PER_SECOND - 1)), rTime.withNano((int) (RTime.NANOS_PER_SECOND - 1)));
        assertThrows(RepublicanCalendarException.class, () -> rTime.withNano(-1));
        assertThrows(RepublicanCalendarException.class, () -> rTime.withNano((int) RTime.NANOS_PER_SECOND));
    }

    @Test
    public void testGetters() {
        RTime rTime = RTime.of(3, 73, 21, 27);
        assertEquals(rTime, RTime.of(rTime.getHour(), rTime.getMinute(), rTime.getSecond(), rTime.getNano()));
    }

    @Test
    public void testRoundSecond_up() {
        RTime rTime = RTime.of(4, 70, 30, 0);
        assertEquals(RTime.of(4, 70, 30), rTime.roundSecond());

        rTime = RTime.of(4, 70, 30, 800_000_000);
        assertEquals(RTime.of(4, 70, 31), rTime.roundSecond());

        rTime = RTime.of(4, 70, 30, 500_000_000);
        assertEquals(RTime.of(4, 70, 31), rTime.roundSecond());

        rTime = RTime.of(4, 99, 99, 500_000_000);
        assertEquals(RTime.of(5, 0, 0), rTime.roundSecond());
    }

    @Test
    public void testRoundSecond_down() {
        RTime rTime = RTime.of(4, 70, 30, 100_000_000);
        assertEquals(RTime.of(4, 70, 30), rTime.roundSecond());

        rTime = RTime.of(4, 70, 30, 499_999_999);
        assertEquals(RTime.of(4, 70, 30), rTime.roundSecond());
    }

    @Test
    public void testHashcode() {
        RTime rt1 = RTime.of(8, 64, 80);
        RTime rt2 = RTime.of(8, 64, 80);
        RTime rt3 = RTime.of(8, 64, 81);
        assertEquals(rt1.hashCode(), rt2.hashCode());
        assertNotEquals(rt1.hashCode(), rt3.hashCode());
    }

    @Test
    public void testCompareTo() {
        RTime rt = RTime.of(8, 64, 80, 1500);
        assertEquals(-1, rt.compareTo(null));
        assertTrue(rt.compareTo(RTime.of(9, 63, 87)) < 0);
        assertTrue(rt.compareTo(RTime.of(5, 63, 87)) > 0);
        assertTrue(rt.compareTo(RTime.of(8, 98, 87)) < 0);
        assertTrue(rt.compareTo(RTime.of(8, 13, 87)) > 0);
        assertTrue(rt.compareTo(RTime.of(8, 64, 87)) < 0);
        assertTrue(rt.compareTo(RTime.of(8, 64, 77)) > 0);
        assertTrue(rt.compareTo(RTime.of(8, 64, 80, 1501)) < 0);
        assertTrue(rt.compareTo(RTime.of(8, 64, 80, 1234)) > 0);
        assertEquals(0, rt.compareTo(RTime.of(8, 64, 80, 1500)));
    }

    @Test
    public void testEquals() {
        RTime rt = RTime.of(8, 64, 80, 1500);
        assertEquals(rt, rt);
        assertNotEquals(rt, null);
        assertNotEquals(rt, new Object());
        assertEquals(rt, RTime.of(8, 64, 80, 1500));
        assertNotEquals(rt, RTime.of(7, 64, 80, 1500));
        assertNotEquals(rt, RTime.of(8, 63, 80, 1500));
        assertNotEquals(rt, RTime.of(8, 64, 81, 1500));
        assertNotEquals(rt, RTime.of(8, 64, 80, 1501));
    }
}
