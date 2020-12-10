package com.github.jarnaud.republican;

import org.junit.jupiter.api.Test;

import java.time.Year;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;

import static java.time.temporal.ChronoField.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test the behaviour of RDate in regard to its implementation of TemporalAccessor.
 */
public class RDateTemporalAccessorTest {

    @Test
    public void testRange_supported() {
        assertEquals(ValueRange.of(1, 30), RDate.of(6, RMonth.Floreal, 1).range(DAY_OF_MONTH));
        assertEquals(ValueRange.of(1, 13), RDate.of(6, RMonth.Floreal, 1).range(MONTH_OF_YEAR));
        assertEquals(ValueRange.of(1, Year.MAX_VALUE), RDate.of(6, RMonth.Floreal, 1).range(YEAR));
    }

    @Test
    public void testRange_notSupported() {
        RDate rDate = RDate.of(6, RMonth.Floreal, 1);
        assertThrows(UnsupportedTemporalTypeException.class, () -> rDate.range(CLOCK_HOUR_OF_DAY));
        assertThrows(UnsupportedTemporalTypeException.class, () -> rDate.range(ALIGNED_WEEK_OF_YEAR));
        assertThrows(UnsupportedTemporalTypeException.class, () -> rDate.range(new FakeTemporalField()));
    }

    @Test
    public void testGetLong_supported() {
        assertEquals(1, RDate.of(6, RMonth.Floreal, 1).getLong(DAY_OF_MONTH));
        assertEquals(8, RDate.of(6, RMonth.Floreal, 1).getLong(MONTH_OF_YEAR));
        assertEquals(6, RDate.of(6, RMonth.Floreal, 1).getLong(YEAR));
    }

    @Test
    public void testGetLong_notSupported() {
        RDate rDate = RDate.of(6, RMonth.Floreal, 1);
        assertThrows(UnsupportedTemporalTypeException.class, () -> rDate.getLong(CLOCK_HOUR_OF_DAY));
        assertThrows(UnsupportedTemporalTypeException.class, () -> rDate.getLong(ALIGNED_WEEK_OF_YEAR));
        assertThrows(UnsupportedTemporalTypeException.class, () -> rDate.getLong(new FakeTemporalField()));
    }
}
