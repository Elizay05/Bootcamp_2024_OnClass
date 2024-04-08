package com.example.bootcamp2024onclass.adapters.driving.http.mapper;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.VersionBootcampResponse;
import com.example.bootcamp2024onclass.domain.model.VersionBootcamp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    @Test
    @DisplayName("When_VersionBootcampListConvertedToVersionBootcampResponseList_Expect_SuccessfulConversion")
    void testToVersionBootcampResponseList() {
        List<VersionBootcamp> versionBootcamps = Arrays.asList(
                new VersionBootcamp(1L, 3L, 50, LocalDate.of(2025, 1, 30), LocalDate.of(2025, 12, 30)),
                new VersionBootcamp(2L, 4L, 50, LocalDate.of(2025, 1, 30), LocalDate.of(2025, 12, 30))
        );

        List<VersionBootcampResponse> result = mapper.toVersionBootcampResponseList(versionBootcamps);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("When_VersionBootcampList_WithNullList_ConvertedToVersionBootcampResponseList_Expect_Failed")
    void testToVersionBootcampResponseListWithNullList() {
        List<VersionBootcampResponse> result = mapper.toVersionBootcampResponseList(null);
        assertNull(result);
    }
}
