package com.github.jarnaud.republican;

import com.github.jarnaud.republican.exception.RepublicanCalendarException;

import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.*;
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
    public static final RDate MIN = of(FIRST_DAY);

    static final int DAYS_PER_MONTH = 30;

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
        return of(year, month.getMonth(), day);
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
        if (year < 1 || month < 1 || month > 13 || day < 1 || day > DAYS_PER_MONTH || (month == 13 && day > 6)) {
            throw new RepublicanCalendarException("Invalid parameters for building Republican date");
        }
        return new RDate(year, RMonth.values()[month - 1], day);
    }

    /**
     * Construct a new Republican date from a Gregorian date.
     * Throws a RuntimeException if the given date is invalid (ie. before first day of Republican calendar).
     *
     * @param date the Gregorian date.
     * @return the Republican date.
     */
    public static RDate of(LocalDate date) {
        return new GRConverter().convert(date);
    }

    /**
     * Return the Republican year for this date.
     *
     * @return the year.
     */
    public int getYear() {
        return year;
    }

    /**
     * Return the Republican month for this date.
     *
     * @return the month.
     */
    public RMonth getMonth() {
        return month;
    }

    /**
     * Return the decade of this date.
     * In the Republican calendar, a decade is a 10 days period within a month (each month being composed of 3 decades).
     * <p>
     * This is NOT a 10 year period!
     *
     * @return the decade (1, 2 or 3).
     */
    public int getDecade() {
        return decade;
    }

    /**
     * Return the day of the month of this date.
     *
     * @return the day of the month.
     */
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

    /**
     * Convert this Republican date into a Gregorian local date.
     *
     * @return the local date.
     */
    public LocalDate toLocalDate() {
        return new RGConverter().convert(this);
    }

    /**
     * Return true if the given date is strictly before this date.
     *
     * @param date the date.
     * @return true if date is before this date, false otherwise.
     */
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
            throw new RepublicanCalendarException("Date " + gregorianDate + " is undefined in the Republican calendar.");
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
    public ValueRange range(TemporalField field) {
        if (field instanceof ChronoField) {
            if (isSupported(field)) {
                switch ((ChronoField) field) {
                    case DAY_OF_MONTH:
                        return ValueRange.of(1, 30);
                    case MONTH_OF_YEAR:
                        return ValueRange.of(1, 13);
                    case YEAR:
                        return ValueRange.of(1, Year.MAX_VALUE);
                }
            }
        }
        throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
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
