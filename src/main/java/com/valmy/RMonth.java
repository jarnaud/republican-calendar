package com.valmy;

public enum RMonth {

    // Automn
    Vendemiaire(1, "Vendemiaire"),
    Brumaire(2, "Brumaire"),
    Frimaire(3, "Frimaire"),

    // Winter
    Nivose(4, "Nivose"),
    Pluviose(5, "Pluviose"),
    Ventose(6, "Ventose"),

    //Spring
    Germinal(7, "Germinal"),
    Floreal(8, "Floreal"),
    Prairial(9, "Prairial"),

    // Summer
    Messidor(10, "Messidor"),
    Thermidor(11, "Thermidor"),
    Fructidor(12, "Fructidor"),

    // Special (complementary) days
    Sanculottide(13, "Sanculottide");

    private final int month;
    private final String name;

    RMonth(int month, String name) {
        this.month = month;
        this.name = name;
    }

    public int getMonth() {
        return month;
    }

    public String getName() {
        return name;
    }
}
