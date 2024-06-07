package com.example.bootcamp2024onclass.adapters.driving.http.controller;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.TechnologyResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddTechnologyRequest;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.ITechnologyRequestMapper;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.ITechnologyResponseMapper;
import com.example.bootcamp2024onclass.domain.api.ITechnologyServicePort;
import com.example.bootcamp2024onclass.domain.model.CustomPage;
import com.example.bootcamp2024onclass.domain.model.PaginationCriteria;
import com.example.bootcamp2024onclass.domain.model.Technology;
import com.example.bootcamp2024onclass.domain.util.SortDirection;
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

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
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
    @DisplayName("When_GetAllTechnologies_Expect_CorrectResponse")
    void shouldReturnAllTechnologiesSuccessfully() {
        PaginationCriteria criteria = new PaginationCriteria(0, 10, SortDirection.ASC, "name");
        List<Technology> mockTechnologies = Arrays.asList(
                new Technology(1L, "Java", "Programming language"),
                new Technology(2L, "Python", "Programming language")
        );
        CustomPage<Technology> mockCustomPage = new CustomPage<>(mockTechnologies, 0, 10, 2, 1);

        when(technologyServicePort.getAllTechnologies(any(PaginationCriteria.class))).thenReturn(mockCustomPage);

        when(technologyResponseMapper.toTechnologyResponse(any(Technology.class)))
                .thenAnswer(invocation -> {
                    Technology tech = invocation.getArgument(0);
                    return new TechnologyResponse(tech.getId(), tech.getName(), tech.getDescription());
                });

        ResponseEntity<CustomPage<TechnologyResponse>> responseEntity = technologyRestControllerAdapter.getAllTechnologies(0, 10, true);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(mockTechnologies.size(), responseEntity.getBody().getContent().size());
        assertEquals(mockTechnologies.get(0).getName(), responseEntity.getBody().getContent().get(0).getName());
        assertEquals(mockTechnologies.get(1).getName(), responseEntity.getBody().getContent().get(1).getName());
    }



    @Test
    @DisplayName("When_GetTotalBodyTechnologies_Expect_CorrectResponse")
    void testGetTotalBodyTechnologies() {
        List<Technology> mockTechnologies = Arrays.asList(
                new Technology(1L, "Java", "Programming language"),
                new Technology(2L, "Python", "Programming language")
        );

        List<TechnologyResponse> mockTechnologyResponses = Arrays.asList(
                new TechnologyResponse(1L, "Java", "Programming language"),
                new TechnologyResponse(2L, "Python", "Programming language")
        );

        when(technologyServicePort.getTotalBodyTechnologies()).thenReturn(mockTechnologies);

        when(technologyResponseMapper.toTechnologyResponseList(mockTechnologies)).thenReturn(mockTechnologyResponses);

        ResponseEntity<List<TechnologyResponse>> responseEntity = technologyRestControllerAdapter.getTotalBodyTechnologies();

        assertEquals(200, responseEntity.getStatusCodeValue());

        assertEquals(mockTechnologyResponses, responseEntity.getBody());

        verify(technologyServicePort, times(1)).getTotalBodyTechnologies();
        verify(technologyResponseMapper, times(1)).toTechnologyResponseList(mockTechnologies);
    }
}

