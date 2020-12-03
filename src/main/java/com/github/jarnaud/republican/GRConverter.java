package com.github.jarnaud.republican;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Converter from Gregorian to Republican dates.
 */
public class GRConverter {

    /**
     * Officially the Republican calendar started on 1792-09-22 (An I Vendemiaire 1).
     */
    private static final int START_YEAR = 1792;
    private static final int START_MONTH = 9;
    private static final int START_DAY = 22;

    private final LeapYearCalculator leapYearCalculator = new LeapYearCalculator();

    /**
     * Convert a local date into a Republican date.
     *
     * @param date the date.
     * @return the corresponding Republican date.
     */
    public RDate convert(LocalDate date) {
        int rYear = date.getYear() - START_YEAR;
        // Default starting date of the Republican year.
        LocalDate start = getRepublicanYearStartDay(date);
        if (date.isBefore(start)) {
            // Compute start on previous Gregorian year.
            start = getRepublicanYearStartDay(start.minusYears(1));
        } else {
            rYear++;
        }

        int daysSinceStartYear = (int) DAYS.between(start, date);

        // Create Republican date.
        int months = daysSinceStartYear / 30; // each Republican month last 30 days.
        RMonth rMonth = RMonth.values()[months];
        int rDay = 1 + daysSinceStartYear % 30; // Add one since we count days from 1, not 0.
        return RDate.of(rYear, rMonth, rDay);
    }

    /**
     * Return the first day of the new Republican year for the year of the provided date.
     *
     * @param date the date.
     * @return the first day of the Republican year.
     */
    private LocalDate getRepublicanYearStartDay(LocalDate date) {
        LocalDate start = LocalDate.of(date.getYear(), START_MONTH, START_DAY);
        return start.plusDays(processShifts(date));
    }

    /**
     * Process the leap years difference between Republican and Gregorian on a given year.
     *
     * @param date the date (Gregorian).
     * @return the leap delta.
     */
    private int processShifts(LocalDate date) {
        int leapR = leapYearCalculator.leapRepublicanYears(date.getYear() - START_YEAR);
        int leapG = leapYearCalculator.leapYears(date.getYear());
        return leapR - leapG;
    }

}
