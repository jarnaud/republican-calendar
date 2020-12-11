package com.github.jarnaud.republican;

import com.github.jarnaud.republican.exception.RepublicanCalendarException;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;
import java.util.Objects;

/**
 * A Republican local date and time.
 */
public class RDateTime implements Comparable<RDateTime>, TemporalAccessor {

    /**
     * The date part.
     */
    private final RDate date;

    /**
     * The time part.
     */
    private final RTime time;

    /**
     * Obtains an instance of RDateTime by converting a {@link LocalDateTime} into the Republican
     * calendar.
     *
     * @param dateTime the local date and time (in Gregorian calendar).
     * @return the Republican date and time.
     */
    public static RDateTime of(LocalDateTime dateTime) {
        // We can construct each part separately since Republican days are synchronized with normal days.
        return of(RDate.of(dateTime.toLocalDate()), RTime.of(dateTime.toLocalTime()));
    }

    /**
     * Obtains an instance of RDateTime from year, month, day, hour, and minute.
     *
     * @param year   the year.
     * @param month  the Republican month.
     * @param day    the day.
     * @param hour   the hour.
     * @param minute the minute.
     * @return the Republican date time.
     */
    public static RDateTime of(int year, RMonth month, int day, int hour, int minute) {
        return of(year, month, day, hour, minute, 0, 0);
    }

    /**
     * Obtains an instance of RDateTime from year, month, day, hour, minute, and second.
     *
     * @param year   the year.
     * @param month  the Republican month.
     * @param day    the day.
     * @param hour   the hour.
     * @param minute the minute.
     * @param second the second.
     * @return the Republican date time.
     */
    public static RDateTime of(int year, RMonth month, int day, int hour, int minute, int second) {
        return of(year, month, day, hour, minute, second, 0);
    }

    /**
     * Obtains an instance of RDateTime from year, month, day, hour, minute, second and nanosecond.
     *
     * @param year   the year.
     * @param month  the Republican month.
     * @param day    the day.
     * @param hour   the hour.
     * @param minute the minute.
     * @param second the second.
     * @param nano   the nanosecond.
     * @return the Republican date time.
     */
    public static RDateTime of(int year, RMonth month, int day, int hour, int minute, int second, int nano) {
        // Validation for each part.
        return of(RDate.of(year, month, day), RTime.of(hour, minute, second, nano));
    }

    /**
     * Obtains an instance of RDateTime from a Republican date and a Republican time.
     *
     * @param rDate the Republican date.
     * @param rTime the Republican time.
     * @return the Republican date time.
     */
    public static RDateTime of(RDate rDate, RTime rTime) {
        if (rDate == null || rTime == null) {
            throw new RepublicanCalendarException("Missing date and/or time component.");
        }
        return new RDateTime(rDate, rTime);
    }

    /**
     * Constructor.
     *
     * @param date the Republican date.
     * @param time the Republican (decimal) time.
     */
    private RDateTime(RDate date, RTime time) {
        this.date = date;
        this.time = time;
    }

    /**
     * Convert this Republican date and time into a Gregorian date and time.
     *
     * @return the date and time.
     */
    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.of(date.toLocalDate(), time.toLocalTime());
    }

    /**
     * Return a new instance of this date and time with the time rounded to the nearest second.
     *
     * @return a Republican date and time.
     */
    public RDateTime roundSSecond() {
        return of(date, time.roundSecond());
    }

    /**
     * Return the Republican year.
     *
     * @return the year.
     */
    public int getYear() {
        return date.getYear();
    }

    /**
     * Return the Republican month.
     *
     * @return the month, between 1 and 13.
     */
    public RMonth getMonth() {
        return date.getMonth();
    }

    /**
     * Return the day of month.
     *
     * @return the day, between 1 and 30.
     */
    public int getDay() {
        return date.getDay();
    }

    /**
     * Return the hour of day.
     *
     * @return the hour, between 0 and 9.
     */
    public int getHour() {
        return time.getHour();
    }

    /**
     * Return the minute of the hour.
     *
     * @return the minute, between 0 and 99.
     */
    public int getMinute() {
        return time.getMinute();
    }

    /**
     * Return the second of the minute.
     *
     * @return the second, between 0 and 99.
     */
    public int getSecond() {
        return time.getSecond();
    }

    /**
     * The nanosecond of the second.
     *
     * @return the nanosecond, between 0 and 999,999,999.
     */
    public int getNano() {
        return time.getNano();
    }

    @Override
    public String toString() {
        return "RDateTime{" +
                "date=" + date +
                ", time=" + time +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RDateTime rDateTime = (RDateTime) o;
        return date.equals(rDateTime.date) && time.equals(rDateTime.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time);
    }

    @Override
    public int compareTo(RDateTime dt) {
        if (dt == null) {
            return -1;
        }
        int cmp = date.compareTo(dt.date);
        if (cmp != 0) {
            return cmp;
        }
        return time.compareTo(dt.time);
    }

    // Temporal accessor implementation.

    @Override
    public boolean isSupported(TemporalField field) {
        return date.isSupported(field) || time.isSupported(field);
    }

    @Override
    public ValueRange range(TemporalField field) {
        if (date.isSupported(field)) {
            return date.range(field);
        }
        return time.range(field);
    }

    @Override
    public long getLong(TemporalField field) {
        if (date.isSupported(field)) {
            return date.getLong(field);
        }
        return time.getLong(field);
    }
}
