package com.example.bootcamp2024onclass.adapters.driving.http.controller;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.BootcampResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.VersionBootcampResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddVersionBootcampRequest;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.IVersionBootcampRequestMapper;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.IVersionBootcampResponseMapper;
import com.example.bootcamp2024onclass.domain.api.IVersionBootcampServicePort;
import com.example.bootcamp2024onclass.domain.model.*;
import com.example.bootcamp2024onclass.domain.util.SortDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class VersionBootcampRestControllerAdapterTest {

    @Mock
    private IVersionBootcampServicePort versionBootcampServicePort;

    @Mock
    private IVersionBootcampRequestMapper versionBootcampRequestMapper;

    @Mock
    private IVersionBootcampResponseMapper versionBootcampResponseMapper;

    @InjectMocks
    private VersionBootcampRestControllerAdapter versionBootcampRestControllerAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("When_AddVersionBootcamp_Expect_CorrectResponse")
    void testAddVersionBootcamp() {
        LocalDate startDate = LocalDate.of(2025, 1, 30);
        LocalDate endDate = LocalDate.of(2025, 12, 30);
        AddVersionBootcampRequest request = new AddVersionBootcampRequest(1L, 50, startDate, endDate);
        VersionBootcamp expectedVersionBootcamp = new VersionBootcamp(1L, 1L, 50, startDate, endDate);
        VersionBootcampResponse expectedResponse = new VersionBootcampResponse(1L, 1L, "", 50, startDate, endDate);

        when(versionBootcampRequestMapper.addRequestToVersionBootcamp(request)).thenReturn(expectedVersionBootcamp);
        when(versionBootcampServicePort.saveVersionBootcamp(expectedVersionBootcamp)).thenReturn(expectedVersionBootcamp);
        when(versionBootcampResponseMapper.toVersionBootcampResponse(expectedVersionBootcamp)).thenReturn(expectedResponse);

        ResponseEntity<VersionBootcampResponse> responseEntity = versionBootcampRestControllerAdapter.addVersionBootcamp(request);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
        verify(versionBootcampRequestMapper, times(1)).addRequestToVersionBootcamp(request);
        verify(versionBootcampServicePort, times(1)).saveVersionBootcamp(expectedVersionBootcamp);
        verify(versionBootcampResponseMapper, times(1)).toVersionBootcampResponse(expectedVersionBootcamp);
    }

    @Test
    @DisplayName("When_GetAllVersionBootcamps_Expect_CorrectResponse")
    void testGetAllVersionBootcampsSuccessfully() {
        PaginationCriteria criteria = new PaginationCriteria(0, 10, SortDirection.ASC, "startDate");
        List<VersionBootcamp> mockVersionBootcamps = Arrays.asList(
                new VersionBootcamp(1L, 1L, 50, LocalDate.of(2025, 1, 30), LocalDate.of(2025, 12, 30)),
                new VersionBootcamp(2L, 1L, 50, LocalDate.of(2025, 1, 30), LocalDate.of(2025, 12, 30))
        );
        CustomPage<VersionBootcamp> mockCustomPage = new CustomPage<>(mockVersionBootcamps, 0, 10, 2, 1);

        when(versionBootcampServicePort.getAllVersionBootcamps(any(PaginationCriteria.class), eq(null)))
                .thenReturn(mockCustomPage);

        when(versionBootcampResponseMapper.toVersionBootcampResponse(any(VersionBootcamp.class)))
                .thenAnswer(invocation -> {
                    VersionBootcamp versionBootcamp = invocation.getArgument(0);
                    return new VersionBootcampResponse(
                            versionBootcamp.getId(),
                            versionBootcamp.getBootcampId(),
                            "Mock Bootcamp Name",
                            versionBootcamp.getMaximumQuota(),
                            versionBootcamp.getStartDate(),
                            versionBootcamp.getEndDate()
                    );
                });

        ResponseEntity<CustomPage<VersionBootcampResponse>> responseEntity = versionBootcampRestControllerAdapter.getAllVersionBootcamps(0, 10, true, true, null);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(mockVersionBootcamps.size(), responseEntity.getBody().getContent().size());
        assertEquals("Mock Bootcamp Name", responseEntity.getBody().getContent().get(0).getBootcampName());
        assertEquals("Mock Bootcamp Name", responseEntity.getBody().getContent().get(1).getBootcampName());

        verify(versionBootcampServicePort, times(1)).getAllVersionBootcamps(any(PaginationCriteria.class), eq(null));
    }
}
