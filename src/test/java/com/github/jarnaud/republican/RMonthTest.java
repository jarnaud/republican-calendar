package com.github.jarnaud.republican;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RMonthTest {

    @Test
    public void testGetMonth() {
        assertEquals(1, RMonth.Vendemiaire.getMonth());
        assertEquals(9, RMonth.Prairial.getMonth());
        assertEquals(12, RMonth.Fructidor.getMonth());
        assertEquals(13, RMonth.Sanculottide.getMonth());
    }

    @Test
    public void testGetName() {
        assertEquals("Vendemiaire", RMonth.Vendemiaire.getName());
        assertEquals("Prairial", RMonth.Prairial.getName());
    }
}
