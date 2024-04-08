package com.example.bootcamp2024onclass.adapters.driving.http.controller;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.VersionBootcampResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddVersionBootcampRequest;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.IVersionBootcampRequestMapper;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.IVersionBootcampResponseMapper;
import com.example.bootcamp2024onclass.domain.api.IVersionBootcampServicePort;
import com.example.bootcamp2024onclass.domain.model.VersionBootcamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

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
    @DisplayName("When_GetAllVersionBootcamps_Expect_SuccessfulResponse")
    void shouldReturnAllVersionBootcampsSuccessfully() {
        ResponseEntity<List<VersionBootcampResponse>> response = versionBootcampRestControllerAdapter.getAllVersionBootcamps(0, 10, null, true, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
