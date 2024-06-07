package com.example.bootcamp2024onclass.domain.model;

import com.example.bootcamp2024onclass.domain.util.SortDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaginationCriteriaTest {

    @Test
    void testPaginationCriteriaConstructor() {
        // Arrange
        int expectedPage = 1;
        int expectedSize = 20;
        SortDirection expectedSortDirection = SortDirection.ASC;
        String expectedSortBy = "name";

        // Act
        PaginationCriteria paginationCriteria = new PaginationCriteria(expectedPage, expectedSize, expectedSortDirection, expectedSortBy);

        // Assert
        assertEquals(expectedPage, paginationCriteria.getPage(), "Page should be initialized correctly");
        assertEquals(expectedSize, paginationCriteria.getSize(), "Size should be initialized correctly");
        assertEquals(expectedSortDirection, paginationCriteria.getSortDirection(), "Sort direction should be initialized correctly");
        assertEquals(expectedSortBy, paginationCriteria.getSortBy(), "Sort by should be initialized correctly");
    }

    @Test
    void testPaginationCriteriaSetters() {
        // Arrange
        PaginationCriteria paginationCriteria = new PaginationCriteria(0, 10, SortDirection.DESC, "date");

        // Act
        paginationCriteria.setPage(2);
        paginationCriteria.setSize(15);
        paginationCriteria.setSortDirection(SortDirection.ASC);
        paginationCriteria.setSortBy("id");

        // Assert
        assertEquals(2, paginationCriteria.getPage(), "Page should be updated correctly");
        assertEquals(15, paginationCriteria.getSize(), "Size should be updated correctly");
        assertEquals(SortDirection.ASC, paginationCriteria.getSortDirection(), "Sort direction should be updated correctly");
        assertEquals("id", paginationCriteria.getSortBy(), "Sort by should be updated correctly");
    }
}
