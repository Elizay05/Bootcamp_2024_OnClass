package com.example.bootcamp2024onclass.domain.model;

import com.example.bootcamp2024onclass.domain.exception.BootcampCapacitiesRepeatException;
import com.example.bootcamp2024onclass.domain.exception.MaxSizeCapacitiesException;
import com.example.bootcamp2024onclass.domain.exception.MinSizeCapacitesException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BootcampTest {

    @Test
    @DisplayName("When_GetId_Expect_Success")
    void getId_Success() {
        Long expectedId = 1L;
        List<Capacity> capacities = Arrays.asList(
                new Capacity(1L),
                new Capacity(2L),
                new Capacity(3L)
        );
        Bootcamp bootcamp = new Bootcamp(expectedId, "Capacity", "Description", capacities);
        assertEquals(expectedId, bootcamp.getId());
    }

    @Test
    @DisplayName("When_GetName_Expect_Success")
    void getName_Success() {
        String expectedName = "Name";
        List<Capacity> capacities = Arrays.asList(
                new Capacity(1L),
                new Capacity(2L),
                new Capacity(3L)
        );
        Bootcamp bootcamp = new Bootcamp(1L, expectedName, "Description", capacities);
        assertEquals(expectedName, bootcamp.getName());
    }

    @Test
    @DisplayName("When_GetDescription_Expect_Success")
    void getDescription_Success() {
        String expectedDescription = "Description";
        List<Capacity> capacities = Arrays.asList(
                new Capacity(1L),
                new Capacity(2L),
                new Capacity(3L)
        );
        Bootcamp bootcamp = new Bootcamp(1L, "Capacity", expectedDescription, capacities);
        assertEquals(expectedDescription, bootcamp.getDescription());
    }

    @Test
    @DisplayName("When_GetCapacities_Expect_Success")
    void getCapacities_Success() {
        List<Capacity> expectedCapacities = Arrays.asList(
                new Capacity(1L),
                new Capacity(2L),
                new Capacity(3L)
        );
        Bootcamp bootcamp = new Bootcamp(1L, "Capacity", "Description", expectedCapacities);
        assertEquals(expectedCapacities, bootcamp.getCapacities());
    }

    @Test
    @DisplayName("When_BootcampConstructorWithValidArguments_Expect_Success")
    void bootcampConstructor_WithValidArguments_Success() {
        List<Capacity> capacities = Arrays.asList(
                new Capacity(1L),
                new Capacity(2L),
                new Capacity(3L)
        );

        assertDoesNotThrow(() -> new Bootcamp(1L, "Capacity", "Description", capacities));
    }

    @Test
    @DisplayName("Expect_Exception_When_NullNamePassedToBootcampConstructor")
    void bootcampConstructor_NullName_ExceptionThrown() {
        List<Capacity> capacities = Arrays.asList(
                new Capacity(1L),
                new Capacity(2L),
                new Capacity(3L)
        );

        assertThrows(NullPointerException.class, () -> new Bootcamp(1L, null, "Description", capacities));
    }

    @Test
    @DisplayName("Expect_Exception_When_NullDescriptionPassedToBootcampConstructor")
    void bootcampConstructor_NullDescription_ExceptionThrown() {
        List<Capacity> capacities = Arrays.asList(
                new Capacity(1L),
                new Capacity(2L),
                new Capacity(3L)
        );

        assertThrows(NullPointerException.class, () -> new Bootcamp(1L, "Capacity", null, capacities));
    }

    @Test
    @DisplayName("Expect_Exception_When_LessThanOneCapacityPassedToBootcampConstructor")
    void bootcampConstructor_LessThanOneCapacity_ExceptionThrown() {
        List<Capacity> capacities = Collections.emptyList();
        assertThrows(MinSizeCapacitesException.class, () -> new Bootcamp(1L, "Capacity", "Description", capacities));
    }

    @Test
    @DisplayName("Expect_Exception_When_DuplicateCapacitiesPassedToBootcampConstructor")
    void bootcampConstructor_DuplicateCapacities_ExceptionThrown() {
        List<Capacity> capacities = Arrays.asList(
                new Capacity(1L),
                new Capacity(2L),
                new Capacity(3L),
                new Capacity(3L)
        );

        assertThrows(BootcampCapacitiesRepeatException.class, () -> new Bootcamp(1L, "Capacity", "Description", capacities));
    }

    @Test
    @DisplayName("Expect_Exception_When_MoreThanFourCapacitiesPassedToBootcampConstructor")
    void bootcampConstructor_MoreThanFourCapacities_ExceptionThrown() {
        List<Capacity> capacities = Arrays.asList(
                new Capacity(1L),
                new Capacity(2L),
                new Capacity(3L),
                new Capacity(4L),
                new Capacity(5L)
        );
        assertThrows(MaxSizeCapacitiesException.class, () -> new Bootcamp(1L, "Capacity", "Description", capacities));
    }
}
