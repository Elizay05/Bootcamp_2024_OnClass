package com.example.bootcamp2024onclass.domain.model;

import com.example.bootcamp2024onclass.domain.exception.CapacityTechnologiesRepeatException;
import com.example.bootcamp2024onclass.domain.exception.MaxSizeTechnologiesException;
import com.example.bootcamp2024onclass.domain.exception.MinSizeTechnologiesException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CapacityTest {

    @Test
    @DisplayName("When_CapacityConstructorWithId_Expect_Success")
    void capacityConstructor_WithId_Success() {
        Long id = 1L;
        Capacity capacity = new Capacity(id);
        assertNotNull(capacity);
        assertEquals(id, capacity.getId());
        assertNull(capacity.getName());
        assertNull(capacity.getDescription());
    }

    @Test
    @DisplayName("When_GetId_Expect_Success")
    void getId_Success() {
        Long expectedId = 1L;
        List<Technology> technologies = Arrays.asList(
                new Technology(1L),
                new Technology(2L),
                new Technology(3L)
        );
        Capacity capacity = new Capacity(expectedId, "Capacity", "Description", technologies);
        assertEquals(expectedId, capacity.getId());
    }

    @Test
    @DisplayName("When_GetName_Expect_Success")
    void getName_Success() {
        String expectedName = "Name";
        List<Technology> technologies = Arrays.asList(
                new Technology(1L),
                new Technology(2L),
                new Technology(3L)
        );
        Capacity capacity = new Capacity(1L, expectedName, "Description", technologies);
        assertEquals(expectedName, capacity.getName());
    }

    @Test
    @DisplayName("When_GetDescription_Expect_Success")
    void getDescription_Success() {
        String expectedDescription = "Description";
        List<Technology> technologies = Arrays.asList(
                new Technology(1L),
                new Technology(2L),
                new Technology(3L)
        );
        Capacity capacity = new Capacity(1L, "Capacity", expectedDescription, technologies);
        assertEquals(expectedDescription, capacity.getDescription());
    }

    @Test
    @DisplayName("When_GetTechnologies_Expect_Success")
    void getTechnologies_Success() {
        List<Technology> expectedTechnologies = Arrays.asList(
                new Technology(1L),
                new Technology(2L),
                new Technology(3L)
        );
        Capacity capacity = new Capacity(1L, "Capacity", "Description", expectedTechnologies);
        assertEquals(expectedTechnologies, capacity.getTechnologies());
    }

    @Test
    @DisplayName("When_SetName_Expect_Success")
    void setName_ValidName_Success() {
        Capacity capacity = new Capacity();
        String name = "Java";
        capacity.setName(name);
        assertEquals(name, capacity.getName());
    }

    @Test
    @DisplayName("When_SetDescription_Expect_Success")
    void setDescription_ValidDescription_Success() {
        Capacity capacity = new Capacity();
        String description = "Java programming language";
        capacity.setDescription(description);
        assertEquals(description, capacity.getDescription());
    }

    @Test
    @DisplayName("When_SetTechnologies_Expect_Success")
    void setTechnologies_ValidDescription_Success() {
        Capacity capacity = new Capacity();
        List<Technology> technologies = Arrays.asList(
                new Technology(1L),
                new Technology(2L),
                new Technology(3L)
        );
        capacity.setTechnologies(technologies);
        assertEquals(technologies, capacity.getTechnologies());
    }

    @Test
    @DisplayName("When_CapacityConstructorWithValidArguments_Expect_Success")
    void capacityConstructor_WithValidArguments_Success() {
        List<Technology> technologies = Arrays.asList(
                new Technology(1L),
                new Technology(2L),
                new Technology(3L)
        );

        assertDoesNotThrow(() -> new Capacity(1L, "Capacity", "Description", technologies));
    }

    @Test
    @DisplayName("Expect_Exception_When_NullNamePassedToCapacityConstructor")
    void capacityConstructor_NullName_ExceptionThrown() {
        List<Technology> technologies = Arrays.asList(
                new Technology(1L),
                new Technology(2L),
                new Technology(3L)
        );

        assertThrows(NullPointerException.class, () -> new Capacity(1L, null, "Description", technologies));
    }

    @Test
    @DisplayName("Expect_Exception_When_NullDescriptionPassedToCapacityConstructor")
    void capacityConstructor_NullDescription_ExceptionThrown() {
        List<Technology> technologies = Arrays.asList(
                new Technology(1L),
                new Technology(2L),
                new Technology(3L)
        );

        assertThrows(NullPointerException.class, () -> new Capacity(1L, "Capacity", null, technologies));
    }

    @Test
    @DisplayName("Expect_Exception_When_LessThanThreeTechnologiesPassedToCapacityConstructor")
    void capacityConstructor_LessThanThreeTechnologies_ExceptionThrown() {
        List<Technology> technologies = Arrays.asList(
                new Technology(1L),
                new Technology(2L)
        );
        assertThrows(MinSizeTechnologiesException.class, () -> new Capacity(1L, "Capacity", "Description", technologies));
    }

    @Test
    @DisplayName("Expect_Exception_When_DuplicateTechnologiesPassedToCapacityConstructor")
    void capacityConstructor_DuplicateTechnologies_ExceptionThrown() {
        List<Technology> technologies = Arrays.asList(
                new Technology(1L),
                new Technology(2L),
                new Technology(3L),
                new Technology(3L)
        );

        assertThrows(CapacityTechnologiesRepeatException.class, () -> new Capacity(1L, "Capacity", "Description", technologies));
    }

    @Test
    @DisplayName("Expect_Exception_When_MoreThanTwentyTechnologiesPassedToCapacityConstructor")
    void capacityConstructor_MoreThanTwentyTechnologies_ExceptionThrown() {
        List<Technology> technologies = Arrays.asList(
                new Technology(1L),
                new Technology(2L),
                new Technology(3L),
                new Technology(4L),
                new Technology(5L),
                new Technology(6L),
                new Technology(7L),
                new Technology(8L),
                new Technology(9L),
                new Technology(10L),
                new Technology(11L),
                new Technology(12L),
                new Technology(13L),
                new Technology(14L),
                new Technology(15L),
                new Technology(16L),
                new Technology(17L),
                new Technology(18L),
                new Technology(19L),
                new Technology(20L),
                new Technology(21L)
        );
        assertThrows(MaxSizeTechnologiesException.class, () -> new Capacity(1L, "Capacity", "Description", technologies));
    }
}

