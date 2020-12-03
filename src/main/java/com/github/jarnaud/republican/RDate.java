package com.github.jarnaud.republican;

import java.util.Objects;

/**
 * A Republican local date.
 */
public final class RDate implements Comparable<RDate> {

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

    public static RDate of(int year, RMonth month, int day) {
        return new RDate(year, month, day);
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

    /**
     * Return a copy of this date shifted by the given number of days.
     * <p>
     * TODO used by converter for shifting by 1 or 2 days within a month,
     * TODO will need to handle month/year shift before being made public.
     *
     * @param daysToAdd the number of days to add.
     * @return the new date.
     */
    RDate plusDays(int daysToAdd) {
        return RDate.of(this.getYear(), this.getMonth(), this.getDay() + daysToAdd);
    }

    public boolean isBefore(RDate date) {
        if (year != date.getYear()) return year < date.getYear();
        if (month.ordinal() != date.getMonth().ordinal()) return month.ordinal() < date.getMonth().ordinal();
        return day < date.getDay();
    }
}
