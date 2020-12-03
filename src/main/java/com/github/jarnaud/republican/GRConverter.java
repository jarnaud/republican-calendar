package com.github.jarnaud.republican;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Converter Gregorian -> Republican.
 */
public class GRConverter {

    private static final Logger logger = LoggerFactory.getLogger(GRConverter.class);

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
        logger.debug("=== CONVERTING {} ===", date);

        // YEAR.
        int ryear = date.getYear() - START_YEAR;
        // Default starting date of the Republican year.
        LocalDate start = getRepublicanYearStartDay(date);
        if (date.isBefore(start)) {
            // Compute start on previous Gregorian year.
            logger.debug("{} is before start {}", date, start);
            start = getRepublicanYearStartDay(start.minusYears(1));
        } else {
            logger.debug("{} is after start {}", date, start);
            ryear++;
        }

        int daysSinceStartYear = (int) DAYS.between(start, date);

        // Create Republican date.
        int months = daysSinceStartYear / 30; // each Republican month last 30 days.
        RMonth rmonth = RMonth.values()[months];
        int rday = 1 + daysSinceStartYear % 30; // Add one since we count days from 1, not 0.
        return RDate.of(ryear, rmonth, rday);
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
