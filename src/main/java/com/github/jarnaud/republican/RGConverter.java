package com.github.jarnaud.republican;

import java.time.LocalDate;
import java.time.Month;

/**
 * Converter from Republican to Gregorian dates.
 */
public class RGConverter {

    /**
     * Officially the Republican calendar started on 1792-09-22 (An I Vendemiaire 1).
     */
    private static final int START_YEAR = 1792;

    private final LeapYearCalculator leapYearCalculator = new LeapYearCalculator();

    /**
     * Convert a Republican date into a local date.
     *
     * @param rDate the Republican date.
     * @return the corresponding local date.
     */
    public LocalDate convert(RDate rDate) {
        final int deltaDays;
        RDate start = getGregorianYearStartDay(rDate.getYear());
        if (rDate.isBefore(start)) {
            deltaDays = -getDaysBetween(rDate, start);
        } else {
            deltaDays = getDaysBetween(start, rDate);
        }
        return LocalDate.of(START_YEAR + rDate.getYear(), Month.JANUARY, 1).plusDays(deltaDays);
    }

    /**
     * Simplified day difference (does not handle different years and assume rd1<rd2).
     * Used for delta within the same year.
     *
     * @param rd1 the first Republican date.
     * @param rd2 the second Republican year.
     * @return the days difference.
     */
    private int getDaysBetween(RDate rd1, RDate rd2) {
        return getDaysSinceRYearStart(rd2) - getDaysSinceRYearStart(rd1);
    }

    private int getDaysSinceRYearStart(RDate rDate) {
        return 30 * rDate.getMonth().ordinal() + rDate.getDay();
    }

    /**
     * Return Jan 1st for the year of the provided Republican date.
     * Will return 10, 11 or 12 Nivose depending on the year.
     *
     * @param rYear the Republican year.
     * @return the first day of the Gregorian year as a Republican date.
     */
    private RDate getGregorianYearStartDay(int rYear) {
        RDate start = RDate.of(rYear, RMonth.Nivose, 12);
        return start.plusDays(processShifts(rYear - 1));
    }

    /**
     * Process the leap years difference between Gregorian and Republican on a given year.
     *
     * @param ryear the Republican year.
     * @return the leap delta.
     */
    private int processShifts(int ryear) {
        int leapG = leapYearCalculator.leapYears(ryear + START_YEAR);
        int leapR = leapYearCalculator.leapRepublicanYears(ryear);
        return leapG - leapR;
    }

}
