package com.github.jarnaud.republican;

import com.github.jarnaud.republican.exception.RepublicanCalendarException;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A Republican local date and time.
 */
public class RDateTime implements Comparable<RDateTime> {

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

    public static RDateTime of(int year, RMonth month, int day, int hour, int minute) {
        return of(year, month, day, hour, minute, 0, 0);
    }

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
    public RDateTime(RDate date, RTime time) {
        this.date = date;
        this.time = time;
    }

    public int getYear() {
        return date.getYear();
    }

    public RMonth getMonth() {
        return date.getMonth();
    }

    public int getDay() {
        return date.getDay();
    }

    public int getHour() {
        return time.getHour();
    }

    public int getMinute() {
        return time.getMinute();
    }

    public int getSecond() {
        return time.getSecond();
    }

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
}
