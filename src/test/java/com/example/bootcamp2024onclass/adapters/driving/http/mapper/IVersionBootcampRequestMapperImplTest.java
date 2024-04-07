package com.example.bootcamp2024onclass.adapters.driving.http.mapper;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddVersionBootcampRequest;
import com.example.bootcamp2024onclass.domain.model.VersionBootcamp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class IVersionBootcampRequestMapperImplTest {
    private final IVersionBootcampRequestMapperImpl mapper = new IVersionBootcampRequestMapperImpl();

    @Test
    @DisplayName("When_AddVersionBootcampRequestMappedToVersionBootcamp_Expect_SuccessfulMapping")
    void testAddRequestToVersionBootcamp() {
        Long bootcampId = 1L;
        Integer maximumQuota = 50;
        LocalDate startDate = LocalDate.of(2025, 1, 30);
        LocalDate endDate = LocalDate.of(2025, 12, 30);
        AddVersionBootcampRequest request = new AddVersionBootcampRequest(bootcampId, maximumQuota, startDate, endDate);

        VersionBootcamp result = mapper.addRequestToVersionBootcamp(request);

        assertEquals(bootcampId, result.getBootcampId());
        assertEquals(maximumQuota, result.getMaximumQuota());
        assertEquals(startDate, result.getStartDate());
        assertEquals(endDate, result.getEndDate());
    }

    @Test
    @DisplayName("When_AddRequestToVersionBootcamp_WithNullRequest_Expect_Null")
    void testAddRequestToVersionBootcampWithNullRequest() {
        VersionBootcamp result = mapper.addRequestToVersionBootcamp(null);

        assertNull(result);
    }
}
