package com.example.bootcamp2024onclass.domain.model;

import com.example.bootcamp2024onclass.domain.exception.CapacityTechnologiesRepeatException;
import com.example.bootcamp2024onclass.domain.exception.MaxSizeTechnologiesException;
import com.example.bootcamp2024onclass.domain.exception.MinSizeTechnologiesException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class CapacityTest {

    @Test
    @DisplayName("Capacity Constructor With Valid Arguments - Success")
    void capacityConstructor_WithValidArguments_Success() {
        List<Technology> technologies = Arrays.asList(
                mock(Technology.class),
                mock(Technology.class),
                mock(Technology.class)
        );

        assertDoesNotThrow(() -> new Capacity(1L, "Capacity", "Description", technologies));
    }

    @Test
    @DisplayName("Capacity Constructor Null Name - Exception Thrown")
    void capacityConstructor_NullName_ExceptionThrown() {
        List<Technology> technologies = Arrays.asList(
                mock(Technology.class),
                mock(Technology.class),
                mock(Technology.class)
        );

        assertThrows(NullPointerException.class, () -> new Capacity(1L, null, "Description", technologies));
    }

    @Test
    @DisplayName("Capacity Constructor Null Description - Exception Thrown")
    void capacityConstructor_NullDescription_ExceptionThrown() {
        List<Technology> technologies = Arrays.asList(
                mock(Technology.class),
                mock(Technology.class),
                mock(Technology.class)
        );

        assertThrows(NullPointerException.class, () -> new Capacity(1L, "Capacity", null, technologies));
    }

    @Test
    @DisplayName("Capacity Constructor Less Than Three Technologies - Exception Thrown")
    void capacityConstructor_LessThanThreeTechnologies_ExceptionThrown() {
        List<Technology> technologies = Arrays.asList(
                mock(Technology.class),
                mock(Technology.class)
        );
        assertThrows(MinSizeTechnologiesException.class, () -> new Capacity(1L, "Capacity", "Description", technologies));
    }

    @Test
    @DisplayName("Capacity Constructor Duplicate Technologies - Exception Thrown")
    void capacityConstructor_DuplicateTechnologies_ExceptionThrown() {
        List<Technology> technologies = Arrays.asList(
                mock(Technology.class),
                mock(Technology.class),
                mock(Technology.class),
                mock(Technology.class)
        );

        assertThrows(CapacityTechnologiesRepeatException.class, () -> new Capacity(1L, "Capacity", "Description", technologies));
    }

    @Test
    @DisplayName("Capacity Constructor More Than Twenty Technologies - Exception Thrown")
    void capacityConstructor_MoreThanTwentyTechnologies_ExceptionThrown() {
        List<Technology> technologies = Arrays.asList(
                mock(Technology.class),
                mock(Technology.class),
                mock(Technology.class),
                mock(Technology.class),
                mock(Technology.class),
                mock(Technology.class),
                mock(Technology.class),
                mock(Technology.class),
                mock(Technology.class),
                mock(Technology.class),
                mock(Technology.class),
                mock(Technology.class),
                mock(Technology.class),
                mock(Technology.class),
                mock(Technology.class),
                mock(Technology.class),
                mock(Technology.class),
                mock(Technology.class),
                mock(Technology.class),
                mock(Technology.class),
                mock(Technology.class)
        );
        assertThrows(MaxSizeTechnologiesException.class, () -> new Capacity(1L, "Capacity", "Description", technologies));
    }
}

