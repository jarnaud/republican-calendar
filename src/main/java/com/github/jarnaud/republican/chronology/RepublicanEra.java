package com.github.jarnaud.republican.chronology;

import java.time.DateTimeException;
import java.time.chrono.Era;

/**
 * The Republican era started on 1792-09-22,
 * It was officially called "Era of the French".
 */
public enum RepublicanEra implements Era {

    /**
     * The old era.
     */
    OLD,

    /**
     * The Republican era.
     */
    FRENCH;

    /**
     * Obtains an instance of RepublicanEra from an int value.
     * RepublicanEra is an enum representing the Republican eras of OLD/FRENCH.
     * This factory allows the enum to be obtained from the int value.
     *
     * @param republicanEra the OLD/FRENCH value to represent, from 0 (OLD) to 1 (FRENCH).
     * @return the era singleton, not null.
     * @throws DateTimeException if the value is invalid.
     */
    public static RepublicanEra of(int republicanEra) {
        switch (republicanEra) {
            case 0:
                return OLD;
            case 1:
                return FRENCH;
            default:
                throw new DateTimeException("Invalid era: " + republicanEra);
        }
    }

    @Override
    public int getValue() {
        return ordinal();
    }
}
