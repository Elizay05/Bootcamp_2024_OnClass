package com.example.bootcamp2024onclass.domain.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SortDirectionTest {

    @Test
    @DisplayName("Test equality of enum constants")
    void testEquality() {
        assertEquals(SortDirection.ASC, SortDirection.ASC);
        assertEquals(SortDirection.DESC, SortDirection.DESC);
        assertNotEquals(SortDirection.ASC, SortDirection.DESC);
    }

    @Test
    @DisplayName("Test toString() method of enum constants")
    void testToString() {
        assertEquals("ASC", SortDirection.ASC.toString());
        assertEquals("DESC", SortDirection.DESC.toString());
    }
}
