package com.github.jarnaud.republican;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.temporal.ChronoUnit;

import static com.github.jarnaud.republican.RTime.NANOS_PER_SECOND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RTimeConversionTest {

    private static final Logger logger = LoggerFactory.getLogger(RTimeConversionTest.class);

    @Test
    public void testNow1() {
        RTime rTime = RTime.now();
        assertNotNull(rTime);
    }

    @Test
    public void testNow2() {
        RTime rTime = RTime.now(ZoneId.of("Atlantic/St_Helena"));
        assertNotNull(rTime);
    }

    @Test
    public void testNow3() {
        LocalDateTime dateTime = LocalDateTime.of(2020, 12, 8,
                19, 40, 29);
        Instant instant = dateTime.atZone(ZoneId.of("GMT")).toInstant();
        logger.info("GMT instant: {}", instant.toString());
        Clock clock = Clock.fixed(instant, ZoneId.of("Europe/Paris"));
        logger.info("Paris clock: {}", clock.instant().toString());
        logger.info("Paris clock: {}", clock);
        RTime rTime = RTime.now(clock);
        assertNotNull(rTime);
        assertEquals(RTime.of(8, 61, 44), rTime.withNano(0));
    }

    @Test
    public void testRTime_decimalHours() {
        compare(RTime.of(0, 0), LocalTime.of(0, 0));
        compare(RTime.of(1, 0), LocalTime.of(2, 24));
        compare(RTime.of(2, 0), LocalTime.of(4, 48));
        compare(RTime.of(3, 0), LocalTime.of(7, 12));
        compare(RTime.of(4, 0), LocalTime.of(9, 36));
        compare(RTime.of(5, 0), LocalTime.of(12, 0));
        compare(RTime.of(6, 0), LocalTime.of(14, 24));
        compare(RTime.of(7, 0), LocalTime.of(16, 48));
        compare(RTime.of(8, 0), LocalTime.of(19, 12));
        compare(RTime.of(9, 0), LocalTime.of(21, 36));
    }

    @Test
    public void testRTime_normalHours() {
        compare(RTime.of(0, 62, 50), LocalTime.of(1, 30)); //5400s=6250rs
        compare(RTime.of(3, 33, 33), LocalTime.of(8, 0));
        compare(RTime.of(6, 25, 0), LocalTime.of(15, 0));
        compare(RTime.of(7, 50, 0), LocalTime.of(18, 0));
        compare(RTime.of(8, 33, 33), LocalTime.of(20, 0));
    }

    @Test
    public void testRTime_minutes() {
        compare(RTime.of(7, 0, 0), LocalTime.of(16, 48, 0));
        compare(RTime.of(7, 0, 0), LocalTime.of(16, 48, 0));
    }

    @Test
    public void testRTime_samples() {
        compare(5, 45, 1, 13, 4, 49);
//        testToLocalTime(8, 94, 16, 21, 27, 36);
    }

//    // https://en.wikipedia.org/wiki/Decimal_time
//    @Test
//    public void testWikipediaConverter() {
//        testOfLocalTime(8, 61, 44,20, 40, 29);
//    }

    @Test
    public void testSecondRounding() {
        compare(0, 0, 1, 0, 0, 1);
        compare(0, 0, 2, 0, 0, 2);
        compare(0, 0, 3, 0, 0, 3);
        testToLocalTime(0, 0, 4, 0, 0, 3);
        compare(0, 0, 5, 0, 0, 4);
        compare(0, 0, 6, 0, 0, 5);
    }

    /**
     * Compare conversion of times in both direction.
     * The nanosecond component will be rounded to the closest second.
     *
     * NB: cannot always compare both directions due to second rounding!
     *
     * @param rTime     the expected Republican (decimal) time.
     * @param localTime the local time to convert.
     */
    private void compare(RTime rTime, LocalTime localTime) {
        compare(rTime.getHour(), rTime.getMinute(), rTime.getSecond(), localTime.getHour(), localTime.getMinute(), localTime.getSecond());
    }

    private void compare(int rHour, int rMin, int rSec, int gHour, int gMin, int gSec) {
        testOfLocalTime(rHour, rMin, rSec, gHour, gMin, gSec);
        testToLocalTime(rHour, rMin, rSec, gHour, gMin, gSec);
    }

    // Test GR conversion.
    private void testOfLocalTime(int rHour, int rMin, int rSec, int gHour, int gMin, int gSec) {
        RTime rTime = RTime.of(rHour, rMin, rSec);
        // Check G to R conversion.
        LocalTime localTime = LocalTime.of(gHour, gMin, gSec);
        // NB: round the second (since test input doesn't provide nano).
        assertEquals(rTime, RTime.of(localTime).roundSecond());
    }

    // Test RG conversion.
    private void testToLocalTime(int rHour, int rMin, int rSec, int gHour, int gMin, int gSec) {
        RTime rTime = RTime.of(rHour, rMin, rSec);
        // Check R to G conversion.
        LocalTime localTime = LocalTime.of(gHour, gMin, gSec);
        assertEquals(localTime, roundSecond(rTime.toLocalTime()));
    }

    private LocalTime roundSecond(LocalTime time) {
        if (time.getNano() == 0) {
            return time;
        } else if (time.getNano() >= NANOS_PER_SECOND / 2) {
            return time.plus(NANOS_PER_SECOND - time.getNano(), ChronoUnit.NANOS);
        } else {
            return time.minus(time.getNano(), ChronoUnit.NANOS);
        }
    }
}
