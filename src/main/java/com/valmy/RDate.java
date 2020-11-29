package com.valmy;

import java.util.Objects;

/**
 * A Republican local date.
 */
public final class RDate implements Comparable<RDate> {

    private final int year;
    private final RMonth month;
    private final int decade;
    private final int day;

    public RDate(int year, RMonth month, int decade, int day) {
        this.year = year;
        this.month = month;
        this.decade = decade;
        this.day = day;
    }

    public static RDate of(int year, RMonth month, int decade, int day) {
        return new RDate(year, month, decade, day);
    }

    @Override
    public String toString() {
        return String.format("%d %s %d", year, month.getName(), day);
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


}
