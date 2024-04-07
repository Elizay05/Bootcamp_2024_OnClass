package com.example.bootcamp2024onclass.adapters.driving.http.dto.response;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.VersionBootcampResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class VersionBootcampResponseTest {

    @Test
    @DisplayName("When_ConstructorWithValidArguments_Expect_Success")
    void constructor_WithValidArguments_Success() {
        Long id = 1L;
        Long bootcampId = 2L;
        String bootcampName = "Bootcamp Name";
        Integer maximumQuota = 50;
        LocalDate startDate = LocalDate.of(2025, 1, 30);
        LocalDate endDate = LocalDate.of(2025, 12, 30);

        VersionBootcampResponse versionBootcampResponse = new VersionBootcampResponse(id, bootcampId, bootcampName, maximumQuota, startDate, endDate);

        assertNotNull(versionBootcampResponse);
        assertEquals(id, versionBootcampResponse.getId());
        assertEquals(bootcampId, versionBootcampResponse.getBootcampId());
        assertEquals(bootcampName, versionBootcampResponse.getBootcampName());
        assertEquals(maximumQuota, versionBootcampResponse.getMaximumQuota());
        assertEquals(startDate, versionBootcampResponse.getStartDate());
        assertEquals(endDate, versionBootcampResponse.getEndDate());
    }
}
