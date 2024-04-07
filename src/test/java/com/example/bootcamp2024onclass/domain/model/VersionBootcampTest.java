package com.example.bootcamp2024onclass.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class VersionBootcampTest {

    @Test
    @DisplayName("When_Getters_Expect_Success")
    void getters_Success() {
        Long versionBootcampId = 1L;
        Long bootcampId = 2L;
        Integer maximumQuota = 50;
        LocalDate startDate = LocalDate.of(2025, 1, 30);
        LocalDate endDate = LocalDate.of(2025, 12, 30);
        VersionBootcamp versionBootcamp = new VersionBootcamp(versionBootcampId, bootcampId, maximumQuota, startDate, endDate);
        assertEquals(versionBootcampId, versionBootcamp.getId());
        assertEquals(bootcampId, versionBootcamp.getBootcampId());
        assertEquals("", versionBootcamp.getBootcampName());
        assertEquals(maximumQuota, versionBootcamp.getMaximumQuota());
        assertEquals(startDate, versionBootcamp.getStartDate());
        assertEquals(endDate, versionBootcamp.getEndDate());
    }
    @Test
    @DisplayName("When_VersionBootcampConstructorWithValidArguments_Expect_Success")
    void versionBootcampConstructor_WithValidArguments_Success() {
        LocalDate startDate = LocalDate.of(2025, 1, 30);
        LocalDate endDate = LocalDate.of(2025, 12, 30);
        assertDoesNotThrow(() -> new VersionBootcamp(1L, 2L, 50, startDate, endDate));
    }

    @Test
    @DisplayName("When_SetBootcampName_Expect_Success")
    void setBootcampName_ValidName_Success() {
        LocalDate startDate = LocalDate.of(2025, 1, 30);
        LocalDate endDate = LocalDate.of(2025, 12, 30);
        VersionBootcamp versionBootcamp = new VersionBootcamp(1L, 2L, 50, startDate, endDate);
        String bootcampName = "Java";
        versionBootcamp.setBootcampName(bootcampName);
        assertEquals(bootcampName, versionBootcamp.getBootcampName());
    }

}
