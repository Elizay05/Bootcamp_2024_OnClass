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

import java.util.Arrays;
import java.util.List;

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


    @Test
    void testGetAllTechnologies_Success() {
        Integer page = 1;
        Integer size = 10;
        Boolean isAscending = true;
        List<Technology> technologies = Arrays.asList(
                new Technology(1L, "Java", "Programming language"),
                new Technology(2L, "Python", "Programming language")
        );
        List<TechnologyResponse> expectedResponses = Arrays.asList(
                new TechnologyResponse(1L, "Java", "Programming language"),
                new TechnologyResponse(2L, "Python", "Programming language")
        );
        when(technologyServicePort.getAllTechnologies(page, size, isAscending)).thenReturn(technologies);
        when(technologyResponseMapper.toTechnologyResponseList(technologies)).thenReturn(expectedResponses);

        ResponseEntity<List<TechnologyResponse>> responseEntity = technologyRestControllerAdapter.getAllTechnologies(page, size, isAscending);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponses, responseEntity.getBody());
        verify(technologyServicePort, times(1)).getAllTechnologies(page, size, isAscending);
        verify(technologyResponseMapper, times(1)).toTechnologyResponseList(technologies);
    }

}

