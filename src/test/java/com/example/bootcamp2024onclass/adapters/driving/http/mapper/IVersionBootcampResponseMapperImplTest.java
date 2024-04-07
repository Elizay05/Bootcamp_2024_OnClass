package com.example.bootcamp2024onclass.adapters.driving.http.mapper;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.VersionBootcampResponse;
import com.example.bootcamp2024onclass.domain.model.VersionBootcamp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class IVersionBootcampResponseMapperImplTest {
    private final IVersionBootcampResponseMapperImpl mapper = new IVersionBootcampResponseMapperImpl();
    @Test
    @DisplayName("When_VersionBootcampConvertedToVersionBootcampResponse_Expect_SuccessfulConversion")
    void testToVersionBootcampResponse() {
        Long id = 1L;
        Long bootcampId = 2L;
        String bootcampName = "";
        Integer maximumQuota = 50;
        LocalDate startDate = LocalDate.of(2025, 1, 30);
        LocalDate endDate = LocalDate.of(2025, 12, 30);

        VersionBootcamp versionBootcamp = new VersionBootcamp(id, bootcampId, maximumQuota, startDate, endDate);

        VersionBootcampResponse result = mapper.toVersionBootcampResponse(versionBootcamp);

        assertEquals(id, result.getId());
        assertEquals(bootcampId, result.getBootcampId());
        assertEquals(bootcampName, result.getBootcampName());
        assertEquals(maximumQuota, result.getMaximumQuota());
        assertEquals(startDate, result.getStartDate());
        assertEquals(endDate, result.getEndDate());
    }

    @Test
    @DisplayName("When_ToVersionBootcampResponse_WithNullVersionBootcamp_Expect_Null")
    void testToVersionBootcampResponseWithNullVersionBootcamp() {
        VersionBootcampResponse result = mapper.toVersionBootcampResponse(null);

        assertNull(result);
    }
}
