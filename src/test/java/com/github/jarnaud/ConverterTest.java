package com.github.jarnaud;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests verifying conversion correctness and consistency by
 * performing conversion in both direction (G->R and R->G) on a set of verified dates,
 * including some historical dates.
 */
public class ConverterTest {

    private final GRConverter grConverter = new GRConverter();
    private final RGConverter rgConverter = new RGConverter();

    @Test
    public void testYearSimple() {
        compare(7, RMonth.Vendemiaire, 1, 1798, 9, 22);
        compare(7, RMonth.Vendemiaire, 2, 1798, 9, 23);
    }

    @Test
    public void testSextileYears() {
        compare(3, RMonth.Sanculottide, 6, 1795, 9, 22);
        compare(4, RMonth.Vendemiaire, 1, 1795, 9, 23);

        compare(7, RMonth.Sanculottide, 6, 1799, 9, 22);
        compare(8, RMonth.Vendemiaire, 1, 1799, 9, 23);
    }

    @Test
    public void testMultYearsPost1800() {
        compare(12, RMonth.Vendemiaire, 1, 1803, 9, 24);
        compare(16, RMonth.Vendemiaire, 1, 1807, 9, 24);
        compare(20, RMonth.Vendemiaire, 1, 1811, 9, 23);
    }

    @Test
    public void testDaySimple() {
        compare(7, RMonth.Thermidor, 12, 1799, 7, 30);
        compare(7, RMonth.Thermidor, 13, 1799, 7, 31);
        compare(7, RMonth.Thermidor, 14, 1799, 8, 1);
        compare(7, RMonth.Thermidor, 15, 1799, 8, 2);
        compare(7, RMonth.Thermidor, 16, 1799, 8, 3);
    }

    @Test
    public void testSpecialDays() {
        compare(6, RMonth.Sanculottide, 4, 1798, 9, 20);
        compare(6, RMonth.Sanculottide, 5, 1798, 9, 21);
        compare(7, RMonth.Vendemiaire, 1, 1798, 9, 22);
    }

    @Test
    public void testSpecialDaysAdvanced() {
        compare(11, RMonth.Sanculottide, 5, 1803, 9, 22);
        compare(11, RMonth.Sanculottide, 6, 1803, 9, 23);
        compare(12, RMonth.Vendemiaire, 1, 1803, 9, 24);
    }

    @Test
    public void testImportantAlgoDates() {
        // 1800-03-01 is an important pivot.
        compare(8, RMonth.Ventose, 9, 1800, 2, 28);
        compare(8, RMonth.Ventose, 10, 1800, 3, 1);
        compare(8, RMonth.Ventose, 11, 1800, 3, 2);

        compare(8, RMonth.Sanculottide, 5, 1800, 9, 22);
        compare(9, RMonth.Vendemiaire, 1, 1800, 9, 23);

        compare(12, RMonth.Vendemiaire, 1, 1803, 9, 24);
    }

    @Test
    public void testImportantCalendarDates() {
        // First day of Republican calendar.
        compare(1, RMonth.Vendemiaire, 1, 1792, 9, 22);
        // Official usage of Republican calendar.
        compare(2, RMonth.Vendemiaire, 15, 1793, 10, 6);
        // Republican calendar abolished by Napoleon.
        compare(13, RMonth.Fructidor, 22, 1805, 9, 9);
    }

    @Test
    public void testAn100() {
        // An C is a non-sextile century.
        compare(99, RMonth.Sanculottide, 5, 1891, 9, 22);
        compare(100, RMonth.Vendemiaire, 1, 1891, 9, 23);
        compare(100, RMonth.Vendemiaire, 2, 1891, 9, 24);
        compare(100, RMonth.Sanculottide, 5, 1892, 9, 21);
        compare(101, RMonth.Vendemiaire, 1, 1892, 9, 22);
        compare(101, RMonth.Vendemiaire, 2, 1892, 9, 23);
    }

    @Test
    public void testAn200() {
        // An CC is a non-sextile century.
        compare(199, RMonth.Sanculottide, 5, 1991, 9, 22);
        compare(200, RMonth.Vendemiaire, 1, 1991, 9, 23);
        compare(200, RMonth.Vendemiaire, 2, 1991, 9, 24);
        compare(200, RMonth.Sanculottide, 5, 1992, 9, 21);
        compare(201, RMonth.Vendemiaire, 1, 1992, 9, 22);
        compare(201, RMonth.Vendemiaire, 2, 1992, 9, 23);
    }

    @Test
    public void test1800() {
        compare(8, RMonth.Sanculottide, 5, 1800, 9, 22);
        compare(9, RMonth.Vendemiaire, 1, 1800, 9, 23);
        compare(9, RMonth.Vendemiaire, 2, 1800, 9, 24);
    }

    @Test
    public void test1900() {
        compare(108, RMonth.Sanculottide, 6, 1900, 9, 22);
        compare(109, RMonth.Vendemiaire, 1, 1900, 9, 23);
        compare(109, RMonth.Vendemiaire, 2, 1900, 9, 24);
    }

    @Test
    public void test2000() {
        compare(208, RMonth.Sanculottide, 6, 2000, 9, 21);
        compare(209, RMonth.Vendemiaire, 1, 2000, 9, 22);
        compare(209, RMonth.Vendemiaire, 2, 2000, 9, 23);
    }

    @Test
    public void testHistoricalDates() {
        //https://en.wikipedia.org/wiki/Execution_of_Louis_XVI
        compare(1, RMonth.Pluviose, 2, 1793, 1, 21);
        //https://en.wikipedia.org/wiki/Insurrection_of_12_Germinal,_Year_III
        compare(3, RMonth.Germinal, 12, 1795, 4, 1);
        //https://en.wikipedia.org/wiki/Revolt_of_1_Prairial_Year_III
        compare(3, RMonth.Prairial, 1, 1795, 5, 20);
        //https://en.wikipedia.org/wiki/13_Vend%C3%A9miaire
        compare(4, RMonth.Vendemiaire, 13, 1795, 10, 5);
        //https://en.wikipedia.org/wiki/Coup_of_18_Fructidor
        compare(5, RMonth.Fructidor, 18, 1797, 9, 4);
        //https://en.wikipedia.org/wiki/Coup_of_30_Prairial_VII
        compare(7, RMonth.Prairial, 30, 1799, 6, 18);
        //https://en.wikipedia.org/wiki/Coup_of_18_Brumaire
        compare(8, RMonth.Brumaire, 18, 1799, 11, 9);
        //https://fr.wikipedia.org/wiki/Premier_Empire
        compare(12, RMonth.Floreal, 28, 1804, 5, 18);
    }

    @Test
    public void testParisCommuneDates() {
        //https://fr.wikipedia.org/wiki/An_LXXIX
        compare(79, RMonth.Ventose, 7, 1871, 2, 26);
        // Commune de Paris.
        compare(79, RMonth.Ventose, 27, 1871, 3, 18);
        compare(79, RMonth.Germinal, 5, 1871, 3, 26);
    }

    @Test
    public void testModernDates() {
        // https://fr.wikipedia.org/wiki/Calendrier_r%C3%A9publicain#Date_r%C3%A9publicaine_actuelle_du_XXIe_si%C3%A8cle_gr%C3%A9gorien
        compare(229, RMonth.Frimaire, 10, 2020, 11, 30);
        compare(229, RMonth.Frimaire, 11, 2020, 12, 1);
    }

    /**
     * Test the conversion of a Gregorian date into a Republican date, and vice-versa.
     *
     * @param rYear  the Republican date year.
     * @param rMonth the Republican date month.
     * @param rDay   the Republican date day of month.
     * @param gYear  the Gregorian date year.
     * @param gMonth the Gregorian date month.
     * @param gDay   the Gregorian date day.
     */
    private void compare(int rYear, RMonth rMonth, int rDay, int gYear, int gMonth, int gDay) {
        assertEquals(
                RDate.of(rYear, rMonth, rDay),
                grConverter.convert(LocalDate.of(gYear, gMonth, gDay))
        );

        assertEquals(
                LocalDate.of(gYear, gMonth, gDay),
                rgConverter.convert(RDate.of(rYear, rMonth, rDay))
        );
    }

}
