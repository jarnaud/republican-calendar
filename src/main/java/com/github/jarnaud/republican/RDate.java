package com.github.jarnaud.republican;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Objects;

/**
 * A Republican local date.
 */
public final class RDate implements Comparable<RDate>, TemporalAccessor {

    /**
     * The first day in the Republican calendar (corresponding Republican day would be An 1 Vendemiaire 1).
     * Dates before this date are undefined in the Republican calendar.
     */
    public static final LocalDate FIRST_DAY = LocalDate.of(1792, 9, 22);

    private final int year;
    private final RMonth month;
    private final int decade;
    private final int day;

    public RDate(int year, RMonth month, int day) {
        this.year = year;
        this.month = month;
        this.decade = processDecade(day);
        this.day = day;
    }

    private int processDecade(int day) {
        if (day < 11) return 1;
        if (day < 21) return 2;
        return 3;
    }

    /**
     * Construct a new Republican date.
     *
     * @param year  the Republican year (eg. An XII).
     * @param month the Republican month.
     * @param day   the Republican day in the month, must be:
     *              - between 1 and 30 for normal months.
     *              - between 1 and 5 for Sanculottide on normal years.
     *              - between 1 and 6 for Sanculottide on sextile years.
     * @return the Republican date.
     */
    public static RDate of(int year, RMonth month, int day) {
        if (year < 1 || month == null || day < 1 || day > 30 || (month == RMonth.Sanculottide && day > 6)) {
            throw new RuntimeException("Invalid parameters for building Republican date");
        }
        return new RDate(year, month, day);
    }

    /**
     * Construct a new Republican date.
     *
     * @param year  the Republican year (eg. An XII).
     * @param month the Republican month number, between 1 (for Vendemiaire) and 13 (for Sanculottide).
     * @param day   the Republican day in the month, must be:
     *              - between 1 and 30 for normal months.
     *              - between 1 and 5 for Sanculottide on normal years.
     *              - between 1 and 6 for Sanculottide on sextile years.
     * @return the Republican date.
     */
    public static RDate of(int year, int month, int day) {
        if (year < 1 || month < 1 || month > 13 || day < 1 || day > 30 || (month == 13 && day > 6)) {
            throw new RuntimeException("Invalid parameters for building Republican date");
        }
        return new RDate(year, RMonth.values()[month - 1], day);
    }

    public int getYear() {
        return year;
    }

    public RMonth getMonth() {
        return month;
    }

    public int getDecade() {
        return decade;
    }

    public int getDay() {
        return day;
    }

    @Override
    public String toString() {
        return String.format("An %d %s %d", year, month.getName(), day);
    }

    @Override
    public int compareTo(RDate date) {
        if (date == null) {
            return -1;
        }
        if (this.year != date.year) {
            return this.year - date.year;
        }
        if (this.month != date.month) {
            return this.month.ordinal() - date.month.ordinal();
        }
        if (this.day != date.day) {
            return this.day - date.day;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RDate that = (RDate) o;
        return year == that.year &&
                decade == that.decade &&
                day == that.day &&
                month == that.month;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, decade, day);
    }

    public boolean isBefore(RDate date) {
        if (year != date.getYear()) return year < date.getYear();
        if (month.ordinal() != date.getMonth().ordinal()) return month.ordinal() < date.getMonth().ordinal();
        return day < date.getDay();
    }

    /**
     * Return true if the given year is sextile, false otherwise.
     *
     * @return true if year is sextile, false otherwise.
     */
    public boolean isSextile() {
        if (year == 3 || year == 7 || year == 11 || year == 15) {
            return true;
        }
        return year >= 20 && year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    /**
     * Return a copy of this date shifted by the given number of days.
     * Cannot go back before the first day of the Republican calendar (1792-09-22).
     * <p>
     * Uses converters to go through LocalDate implementation of plusDays.
     *
     * @param daysToAdd the number of days to add.
     * @return the new date.
     */
    public RDate plusDays(int daysToAdd) {
        LocalDate gregorianDate = new RGConverter().convert(this);
        gregorianDate = gregorianDate.plusDays(daysToAdd);
        if (gregorianDate.isBefore(FIRST_DAY)) {
            throw new RuntimeException("Date " + gregorianDate + " is undefined in the Republican calendar.");
        }
        return new GRConverter().convert(gregorianDate);
    }


    // Temporal accessor implementation.

    @Override
    public boolean isSupported(TemporalField field) {
        if (field instanceof ChronoField) {
            switch ((ChronoField) field) {
                case DAY_OF_MONTH:
                case MONTH_OF_YEAR:
                case YEAR:
                    return true;
            }
        }
        return false;
    }

    @Override
    public long getLong(TemporalField field) {
        if (field instanceof ChronoField) {
            switch ((ChronoField) field) {
                case DAY_OF_MONTH:
                    return getDay();
                case MONTH_OF_YEAR:
                    return getMonth().ordinal() + 1;
                case YEAR:
                    return getYear();
            }
        }
        throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
    }


}
