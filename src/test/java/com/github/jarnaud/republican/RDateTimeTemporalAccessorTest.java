package com.github.jarnaud.republican;

import org.junit.jupiter.api.Test;

import java.time.Year;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;

import static java.time.temporal.ChronoField.*;
import static org.junit.jupiter.api.Assertions.*;

public class RDateTimeTemporalAccessorTest {

    @Test
    public void testIsSupported() {
        RDateTime rdt = RDateTime.of(6, RMonth.Floreal, 1, 5, 75, 80);
        assertTrue(rdt.isSupported(DAY_OF_MONTH));
        assertTrue(rdt.isSupported(SECOND_OF_MINUTE));
        assertFalse(rdt.isSupported(ALIGNED_WEEK_OF_YEAR));
    }

    @Test
    public void testRange_supported() {
        RDateTime rdt = RDateTime.of(6, RMonth.Floreal, 1, 5, 75, 80);
        assertEquals(ValueRange.of(1, 30), rdt.range(DAY_OF_MONTH));
        assertEquals(ValueRange.of(1, 13), rdt.range(MONTH_OF_YEAR));
        assertEquals(ValueRange.of(1, Year.MAX_VALUE), rdt.range(YEAR));
        assertEquals(ValueRange.of(0, RTime.NANOS_PER_DAY - 1), rdt.range(NANO_OF_DAY));
        assertEquals(ValueRange.of(0, RTime.NANOS_PER_SECOND - 1), rdt.range(NANO_OF_SECOND));
        assertEquals(ValueRange.of(0, 99), rdt.range(SECOND_OF_MINUTE));
        assertEquals(ValueRange.of(0, 99), rdt.range(MINUTE_OF_HOUR));
        assertEquals(ValueRange.of(0, 9), rdt.range(HOUR_OF_DAY));
    }

    @Test
    public void testRange_notSupported() {
        RDateTime rdt = RDateTime.of(6, RMonth.Floreal, 1, 5, 75, 80);
        assertThrows(UnsupportedTemporalTypeException.class, () -> rdt.range(CLOCK_HOUR_OF_DAY));
        assertThrows(UnsupportedTemporalTypeException.class, () -> rdt.range(ALIGNED_WEEK_OF_YEAR));
        assertThrows(UnsupportedTemporalTypeException.class, () -> rdt.range(new FakeTemporalField()));
    }

    @Test
    public void testGetLong_supported() {
        RDateTime rdt = RDateTime.of(6, RMonth.Floreal, 1, 5, 80, 20, 1087);
        assertEquals(1, rdt.getLong(DAY_OF_MONTH));
        assertEquals(8, rdt.getLong(MONTH_OF_YEAR));
        assertEquals(6, rdt.getLong(YEAR));
        assertEquals(5 * RTime.NANOS_PER_HOUR + 80 * RTime.NANOS_PER_MINUTE + 20 * RTime.NANOS_PER_SECOND + 1087, rdt.getLong(NANO_OF_DAY));
        assertEquals(1087, rdt.getLong(NANO_OF_SECOND));
        assertEquals(20, rdt.getLong(SECOND_OF_MINUTE));
        assertEquals(80, rdt.getLong(MINUTE_OF_HOUR));
        assertEquals(5, rdt.getLong(HOUR_OF_DAY));
    }

    @Test
    public void testGetLong_notSupported() {
        RDateTime rdt = RDateTime.of(6, RMonth.Floreal, 1, 5, 75, 80);
        assertThrows(UnsupportedTemporalTypeException.class, () -> rdt.getLong(CLOCK_HOUR_OF_DAY));
        assertThrows(UnsupportedTemporalTypeException.class, () -> rdt.getLong(ALIGNED_WEEK_OF_YEAR));
        assertThrows(UnsupportedTemporalTypeException.class, () -> rdt.getLong(new FakeTemporalField()));
    }

}
