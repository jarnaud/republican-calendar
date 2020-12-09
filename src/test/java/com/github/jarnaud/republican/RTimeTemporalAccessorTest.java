package com.github.jarnaud.republican;

import org.junit.jupiter.api.Test;

import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;

import static java.time.temporal.ChronoField.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test the behaviour of RTime in regard to its implementation of TemporalAccessor.
 */
public class RTimeTemporalAccessorTest {

    @Test
    public void testRange_supported() {
        RTime rt = RTime.of(5, 80, 20);
        assertEquals(ValueRange.of(0, RTime.NANOS_PER_DAY - 1), rt.range(NANO_OF_DAY));
        assertEquals(ValueRange.of(0, RTime.NANOS_PER_SECOND - 1), rt.range(NANO_OF_SECOND));
        assertEquals(ValueRange.of(0, 99), rt.range(SECOND_OF_MINUTE));
        assertEquals(ValueRange.of(0, 99), rt.range(MINUTE_OF_HOUR));
        assertEquals(ValueRange.of(0, 9), rt.range(HOUR_OF_DAY));
    }

    @Test
    public void testRange_notSupported() {
        RTime rt = RTime.of(5, 80, 20);
        assertThrows(UnsupportedTemporalTypeException.class, () -> rt.range(CLOCK_HOUR_OF_DAY));
        assertThrows(UnsupportedTemporalTypeException.class, () -> rt.range(ALIGNED_WEEK_OF_YEAR));
    }

    @Test
    public void testGetLong_supported() {
        RTime rt = RTime.of(5, 80, 20, 1087);
        assertEquals(5 * RTime.NANOS_PER_HOUR + 80 * RTime.NANOS_PER_MINUTE + 20 * RTime.NANOS_PER_SECOND + 1087, rt.getLong(NANO_OF_DAY));
        assertEquals(1087, rt.getLong(NANO_OF_SECOND));
        assertEquals(20, rt.getLong(SECOND_OF_MINUTE));
        assertEquals(80, rt.getLong(MINUTE_OF_HOUR));
        assertEquals(5, rt.getLong(HOUR_OF_DAY));
    }

    @Test
    public void testGetLong_notSupported() {
        RTime rt = RTime.of(5, 80, 20, 1087);
        assertThrows(UnsupportedTemporalTypeException.class, () -> rt.getLong(CLOCK_HOUR_OF_DAY));
        assertThrows(UnsupportedTemporalTypeException.class, () -> rt.getLong(ALIGNED_WEEK_OF_YEAR));
    }
}
