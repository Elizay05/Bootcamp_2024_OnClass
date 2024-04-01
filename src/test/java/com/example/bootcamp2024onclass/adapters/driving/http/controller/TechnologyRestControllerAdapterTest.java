package com.example.bootcamp2024onclass.adapters.driving.http.controller;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.TechnologyResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddTechnologyRequest;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.ITechnologyRequestMapper;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.ITechnologyResponseMapper;
import com.example.bootcamp2024onclass.domain.api.ITechnologyServicePort;
import com.example.bootcamp2024onclass.domain.model.Technology;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TechnologyRestControllerAdapterTest {

    @Mock
    private ITechnologyServicePort technologyServicePort;

    @Mock
    private ITechnologyRequestMapper technologyRequestMapper;

    @Mock
    private ITechnologyResponseMapper technologyResponseMapper;

    @InjectMocks
    private TechnologyRestControllerAdapter technologyRestControllerAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("When_AddTechnology_Expect_CorrectResponse")
    void testAddTechnology() {

        AddTechnologyRequest request = new AddTechnologyRequest("Java", "Programming language");
        Technology expectedTechnology = new Technology(1L, "Java", "Programming language");
        TechnologyResponse expectedResponse = new TechnologyResponse(1L, "Java", "Programming language");

        when(technologyRequestMapper.addRequestToTechnology(request)).thenReturn(expectedTechnology);
        when(technologyServicePort.saveTechnology(expectedTechnology)).thenReturn(expectedTechnology);
        when(technologyResponseMapper.toTechnologyResponse(expectedTechnology)).thenReturn(expectedResponse);

        ResponseEntity<TechnologyResponse> response = technologyRestControllerAdapter.addTechnology(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());

        verify(technologyRequestMapper, times(1)).addRequestToTechnology(request);
        verify(technologyServicePort, times(1)).saveTechnology(expectedTechnology);
        verify(technologyResponseMapper, times(1)).toTechnologyResponse(expectedTechnology);
    }
}

