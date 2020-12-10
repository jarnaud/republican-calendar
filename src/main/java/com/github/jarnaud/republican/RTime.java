package com.github.jarnaud.republican;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.temporal.*;
import java.util.Objects;

/**
 * A local time in the Republican calendar format.
 * The Republican calendar is using the decimal time:
 * a day is composed of 10 hours, each composed of 100 minutes, each composed of 100 seconds.
 * <p>
 * To facilitate conversion with regular time we still use the Greenwich meantime as reference
 * for the Republican time (instead of historically used Paris meantime).
 */
public final class RTime implements Comparable<RTime>, TemporalAccessor {

    private static final Logger logger = LoggerFactory.getLogger(RTime.class);

    static final int HOURS_PER_DAY = 10;
    static final int MINUTES_PER_HOUR = 100;
    static final int SECONDS_PER_MINUTE = 100;
    static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
    static final int SECONDS_PER_DAY = SECONDS_PER_HOUR * HOURS_PER_DAY;
    static final long NANOS_PER_SECOND = 1_000_000_000; // same as normal time, but nanoseconds are shorter.
    static final long NANOS_PER_MINUTE = NANOS_PER_SECOND * SECONDS_PER_MINUTE;
    static final long NANOS_PER_HOUR = NANOS_PER_SECOND * SECONDS_PER_HOUR;
    static final long NANOS_PER_DAY = NANOS_PER_SECOND * SECONDS_PER_DAY;

    public static final RTime MIN = ofNanoOfDay(0);
    public static final RTime MAX = ofNanoOfDay(NANOS_PER_DAY - 1);

    /**
     * One Republican second is 13.6% shorter than a Gregorian second.
     */
    static final double RG_SECOND_RATIO = 0.864;
    static final double RG_MINUTE_RATIO = 1.44;
    static final double RG_HOUR_RATIO = 2.4;

    private final byte hour;
    private final byte minute;
    private final byte second;
    private final int nano;

    public static RTime now() {
        return now(Clock.systemDefaultZone());
    }

    public static RTime now(ZoneId zone) {
        return now(Clock.system(zone));
    }

    public static RTime now(Clock clock) {
        Objects.requireNonNull(clock, "clock");
        final Instant now = clock.instant();
        return ofInstant(now, clock.getZone());
    }

    private static RTime ofInstant(Instant instant, ZoneId zoneId) {
        Objects.requireNonNull(instant, "instant");
        Objects.requireNonNull(zoneId, "zoneId");
        ZoneOffset offset = zoneId.getRules().getOffset(instant);

        long localSecond = instant.getEpochSecond() + offset.getTotalSeconds();
        long gSecOfDay = localSecond % 86_400L; // Seconds per day in Gregorian calendar.
        long gNanoOfDay = gSecOfDay * NANOS_PER_SECOND + instant.getNano();
        long rNanoOfDay = (long) (gNanoOfDay / RG_SECOND_RATIO);
        return ofNanoOfDay(rNanoOfDay);
    }

    /**
     * Construct a Republican time based on the nanosecond of the day.
     *
     * @param nanoOfDay the Republican nanosecond of the day.
     * @return the Republican time.
     * @throws RuntimeException if parameter is not in valid range.
     */
    public static RTime ofNanoOfDay(long nanoOfDay) {
        if (nanoOfDay < 0 || nanoOfDay >= NANOS_PER_DAY) {
            throw new RuntimeException("Invalid nano");
        }
        int hours = (int) (nanoOfDay / NANOS_PER_HOUR);
        nanoOfDay -= hours * NANOS_PER_HOUR;
        int minutes = (int) (nanoOfDay / NANOS_PER_MINUTE);
        nanoOfDay -= minutes * NANOS_PER_MINUTE;
        int seconds = (int) (nanoOfDay / NANOS_PER_SECOND);
        nanoOfDay -= seconds * NANOS_PER_SECOND;
        return of(hours, minutes, seconds, (int) nanoOfDay);
    }

    public static RTime of(LocalTime localTime) {
        long gnano = localTime.toNanoOfDay();
        long rnano = (long) (gnano / RG_SECOND_RATIO);
        return ofNanoOfDay(rnano);
    }

    public static RTime of(int hour, int minute) {
        return of(hour, minute, 0, 0);
    }

    public static RTime of(int hour, int minute, int second) {
        return of(hour, minute, second, 0);
    }

    public static RTime of(int hour, int minute, int second, int nano) {
        if (hour < 0 || hour >= HOURS_PER_DAY) {
            throw new RuntimeException("Invalid hour");
        }
        if (minute < 0 || minute >= MINUTES_PER_HOUR) {
            throw new RuntimeException("Invalid minute");
        }
        if (second < 0 || second >= SECONDS_PER_MINUTE) {
            throw new RuntimeException("Invalid second");
        }
        if (nano < 0 || nano >= NANOS_PER_SECOND) {
            throw new RuntimeException("Invalid nano");
        }
        return new RTime(hour, minute, second, nano);
    }

    /**
     * Constructor with previously validated input.
     *
     * @param hour   the hour of the day, between 0 and 10.
     * @param minute the minute of the hour, between 0 and 99.
     * @param second the second of the minute, between 0 and 99.
     * @param nano   the nano of the second, between 0 and 999,999,999.
     */
    private RTime(int hour, int minute, int second, int nano) {
        this.hour = (byte) hour;
        this.minute = (byte) minute;
        this.second = (byte) second;
        this.nano = nano;
        logger.info("Created {}", toString());
    }

    public RTime withNano(int nano) {
        if (this.nano == nano) {
            return this;
        }
        if (nano < 0 || nano >= NANOS_PER_SECOND) {
            throw new RuntimeException("Invalid nano");
        }
        return new RTime(hour, minute, second, nano);
    }

    public RTime roundSecond() {
        if (nano == 0) {
            return this;
        }
        long diff = nano >= NANOS_PER_SECOND / 2 ? NANOS_PER_SECOND - nano : -nano;
        long roundedNanoOfDay = getNanoOfDay() + diff;
        return RTime.ofNanoOfDay(roundedNanoOfDay);
    }

    /**
     * Return the Republican nanosecond of the day for this date.
     *
     * @return the nanosecond of the day.
     */
    public long getNanoOfDay() {
        return hour * NANOS_PER_HOUR + minute * NANOS_PER_MINUTE + second * NANOS_PER_SECOND + nano;
    }

    /**
     * Convert this decimal time into a normal local time.
     *
     * @return the local time.
     */
    public LocalTime toLocalTime() {
        long rnano = getNanoOfDay();
        long gnano = (long) (rnano * RG_SECOND_RATIO);
        return LocalTime.ofNanoOfDay(gnano);
    }

    @Override
    public String toString() {
        return String.format("%dh%dm%ds%d", hour, minute, second, nano);
    }

    /**
     * Return the hour of the day, between 0 and 9.
     *
     * @return the hour of the day.
     */
    public int getHour() {
        return hour;
    }

    /**
     * Return the minute of the hour, between 0 and 99.
     *
     * @return the minute of the hour.
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Return the second of the minute, between 0 and 99.
     *
     * @return the second of the minute.
     */
    public int getSecond() {
        return second;
    }

    /**
     * Return the nanosecond of the second, betweem 0 and 999,999,999.
     *
     * @return the nanosecond of the second.
     */
    public int getNano() {
        return nano;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RTime rTime = (RTime) o;
        return hour == rTime.hour && minute == rTime.minute && second == rTime.second && nano == rTime.nano;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hour, minute, second, nano);
    }


    @Override
    public int compareTo(RTime time) {
        if (time == null) {
            return -1;
        }
        if (this.hour != time.hour) {
            return this.hour - time.hour;
        }
        if (this.minute != time.minute) {
            return this.minute - time.minute;
        }
        if (this.second != time.second) {
            return this.second - time.second;
        }
        if (this.nano != time.nano) {
            return this.nano - time.nano;
        }
        return 0;
    }

    // Temporal accessor implementation.

    @Override
    public boolean isSupported(TemporalField field) {
        if (field instanceof ChronoField) {
            switch ((ChronoField) field) {
                case NANO_OF_DAY:
                case NANO_OF_SECOND:
                case SECOND_OF_MINUTE:
                case MINUTE_OF_HOUR:
                case HOUR_OF_DAY:
                    return true;
            }
        }
        return false;
    }

    @Override
    public ValueRange range(TemporalField field) {
        if (field instanceof ChronoField) {
            if (isSupported(field)) {
                switch ((ChronoField) field) {
                    case NANO_OF_DAY:
                        return ValueRange.of(0, NANOS_PER_DAY - 1);
                    case NANO_OF_SECOND:
                        return ValueRange.of(0, NANOS_PER_SECOND - 1);
                    case SECOND_OF_MINUTE:
                        return ValueRange.of(0, SECONDS_PER_MINUTE - 1);
                    case MINUTE_OF_HOUR:
                        return ValueRange.of(0, MINUTES_PER_HOUR - 1);
                    case HOUR_OF_DAY:
                        return ValueRange.of(0, HOURS_PER_DAY - 1);
                }
            }
        }
        throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
    }

    @Override
    public long getLong(TemporalField field) {
        if (field instanceof ChronoField) {
            switch ((ChronoField) field) {
                case NANO_OF_DAY:
                    return getNanoOfDay();
                case NANO_OF_SECOND:
                    return getNano();
                case SECOND_OF_MINUTE:
                    return getSecond();
                case MINUTE_OF_HOUR:
                    return getMinute();
                case HOUR_OF_DAY:
                    return getHour();
            }
        }
        throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
    }
}
