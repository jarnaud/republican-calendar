package com.github.jarnaud.republican;

/**
 * Process the number of leap years between the start of the Republican calendar and a given year.
 */
public class LeapYearCalculator {

    private static final int START_REPUBLICAN_YEAR = 1792;

    /**
     * Process the number of leap years in the Republican calendar ("sextile" years) since An I.
     *
     * @param year the Republican year.
     * @return the number of leap (sextile) years.
     */
    public int leapRepublicanYears(int year) {
        int nb = 0;
        if (year >= 3) nb++;
        if (year >= 7) nb++;
        if (year >= 11) nb++;
        if (year >= 15) nb++;
        if (year >= 20) {
            nb++;
            // After An XX, we process leap years like in the Gregorian calendar.
            nb += leapYearsSince(year, 20);
        }
        return nb;
    }

    public int leapYears(int year) {
        return leapYearsSince(year, START_REPUBLICAN_YEAR);
    }

    private int leapYearsSince(int year, int start) {
        // One leap year every 4 years.
        int nb = (year - start) / 4;
        // Centuries are not leap years, unless div by 400.
        nb -= processCenturyNonLeapYears(year, start);
        return nb;
    }

    /**
     * Process the number of non-leap century years between two years (included).
     *
     * @param year  the year.
     * @param start the starting year.
     * @return the number of non-leap years.
     */
    private int processCenturyNonLeapYears(int year, int start) {
        int nb = 0;
        int century = roundNextCentury(start);
        while (century <= year) {
            if (century % 400 != 0) {
                // Non-leap century years (eg. 1800, 1900 but not 2000).
                nb++;
            }
            century += 100;
        }
        return nb;
    }

    private int roundNextCentury(int year) {
        return ((year + 99) / 100) * 100;
    }
}
