package com.github.jarnaud.republican;

/**
 * A Republican month.
 */
public enum RMonth {

    // Autumn.
    Vendemiaire(1, "Vendemiaire"),
    Brumaire(2, "Brumaire"),
    Frimaire(3, "Frimaire"),

    // Winter.
    Nivose(4, "Nivose"),
    Pluviose(5, "Pluviose"),
    Ventose(6, "Ventose"),

    //Spring.
    Germinal(7, "Germinal"),
    Floreal(8, "Floreal"),
    Prairial(9, "Prairial"),

    // Summer.
    Messidor(10, "Messidor"),
    Thermidor(11, "Thermidor"),
    Fructidor(12, "Fructidor"),

    // Special (complementary) days.
    Sanculottide(13, "Sanculottide");

    private final int month;
    private final String name;

    RMonth(int month, String name) {
        this.month = month;
        this.name = name;
    }

    /**
     * Return the month number (eg. 1 for Vendemiaire, 12 for Fructidor, 13 for Sanculottide...)
     *
     * @return the month number.
     */
    public int getMonth() {
        return month;
    }

    /**
     * The month name (eg. Fructidor, Prairial...)
     *
     * @return the month name.
     */
    public String getName() {
        return name;
    }
}
