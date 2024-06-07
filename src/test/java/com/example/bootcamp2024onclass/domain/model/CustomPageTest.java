package com.example.bootcamp2024onclass.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomPageTest {

    private CustomPage<String> customPage;

    @BeforeEach
    void setUp() {
        List<String> content = Arrays.asList("Item1", "Item2", "Item3");
        customPage = new CustomPage<>(content, 1, 10, 30, 3);
    }

    @Test
    @DisplayName("Test getContent()")
    void testGetContent() {
        List<String> content = customPage.getContent();
        assertNotNull(content);
        assertEquals(3, content.size());
        assertEquals("Item1", content.get(0));
        assertEquals("Item2", content.get(1));
        assertEquals("Item3", content.get(2));
    }

    @Test
    @DisplayName("Test getPageNumber()")
    void testGetPageNumber() {
        assertEquals(1, customPage.getPageNumber());
    }

    @Test
    @DisplayName("Test getPageSize()")
    void testGetPageSize() {
        assertEquals(10, customPage.getPageSize());
    }

    @Test
    @DisplayName("Test getTotalElements()")
    void testGetTotalElements() {
        assertEquals(30, customPage.getTotalElements());
    }

    @Test
    @DisplayName("Test getTotalPages()")
    void testGetTotalPages() {
        assertEquals(3, customPage.getTotalPages());
    }

    @Test
    @DisplayName("Test setContent()")
    void testSetContent() {
        List<String> newContent = Arrays.asList("New Item1", "New Item2");
        customPage.setContent(newContent);
        assertEquals(newContent, customPage.getContent());
    }

    @Test
    @DisplayName("Test setPageNumber()")
    void testSetPageNumber() {
        customPage.setPageNumber(2);
        assertEquals(2, customPage.getPageNumber());
    }

    @Test
    @DisplayName("Test setPageSize()")
    void testSetPageSize() {
        customPage.setPageSize(20);
        assertEquals(20, customPage.getPageSize());
    }

    @Test
    @DisplayName("Test setTotalElements()")
    void testSetTotalElements() {
        customPage.setTotalElements(50);
        assertEquals(50, customPage.getTotalElements());
    }

    @Test
    @DisplayName("Test setTotalPages()")
    void testSetTotalPages() {
        customPage.setTotalPages(5);
        assertEquals(5, customPage.getTotalPages());
    }
}
