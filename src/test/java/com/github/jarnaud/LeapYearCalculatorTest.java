package com.github.jarnaud;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeapYearCalculatorTest {

    private final LeapYearCalculator calculator = new LeapYearCalculator();

    @Test
    public void testSimple() {
        assertEquals(0, calculator.leapYears(1792));
        assertEquals(0, calculator.leapYears(1795));
        assertEquals(1, calculator.leapYears(1799));
        assertEquals(1, calculator.leapYears(1802));
    }

    @Test
    public void testCentury() {
        assertEquals(2, calculator.leapYears(1806));
        assertEquals(8, calculator.leapYears(1828));

        assertEquals(25, calculator.leapYears(1900));
        assertEquals(26, calculator.leapYears(1904));
    }

    @Test
    public void test2000() {
        assertEquals(50, calculator.leapYears(2000));
    }

    @Test
    public void testRepublicanStart() {
        assertEquals(0, calculator.leapRepublicanYears(1));
        assertEquals(0, calculator.leapRepublicanYears(2));
        assertEquals(1, calculator.leapRepublicanYears(3));
        assertEquals(1, calculator.leapRepublicanYears(4));
        assertEquals(1, calculator.leapRepublicanYears(5));
        assertEquals(2, calculator.leapRepublicanYears(7));
        assertEquals(3, calculator.leapRepublicanYears(11));
        assertEquals(3, calculator.leapRepublicanYears(13));
        assertEquals(4, calculator.leapRepublicanYears(15));
        assertEquals(5, calculator.leapRepublicanYears(20));
        assertEquals(5, calculator.leapRepublicanYears(21));
        assertEquals(6, calculator.leapRepublicanYears(24));
    }

    @Test
    public void testRepublicanCentury() {
        assertEquals(24, calculator.leapRepublicanYears(100));
        assertEquals(24, calculator.leapRepublicanYears(101));
        assertEquals(25, calculator.leapRepublicanYears(104));

        assertEquals(48, calculator.leapRepublicanYears(200));
        assertEquals(48, calculator.leapRepublicanYears(201));
        assertEquals(49, calculator.leapRepublicanYears(204));
    }

}
