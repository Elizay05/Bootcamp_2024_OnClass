package com.example.bootcamp2024onclass.adapters.driving.http.controller;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.CapacityResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.CapacityTechnologyResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddCapacityRequest;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.ICapacityRequestMapper;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.ICapacityResponseMapper;
import com.example.bootcamp2024onclass.domain.api.ICapacityServicePort;
import com.example.bootcamp2024onclass.domain.model.Capacity;
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
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CapacityRestControllerAdapterTest {

    @Mock
    private ICapacityServicePort capacityServicePort;

    @Mock
    private ICapacityRequestMapper capacityRequestMapper;

    @Mock
    private ICapacityResponseMapper capacityResponseMapper;

    @InjectMocks
    private CapacityRestControllerAdapter capacityRestControllerAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Add Capacity Should Return Correct Response")
    void testAddCapacity() {
        List<Long> technologyIds = Arrays.asList(1L, 2L, 3L);

        List<Technology> technologies = Arrays.asList(
                mock(Technology.class),
                mock(Technology.class),
                mock(Technology.class)
        );

        List<CapacityTechnologyResponse> capacityTechnologyResponses = Arrays.asList(
                mock(CapacityTechnologyResponse.class),
                mock(CapacityTechnologyResponse.class),
                mock(CapacityTechnologyResponse.class)
        );
        AddCapacityRequest request = new AddCapacityRequest("Capacity Name", "Capacity Description", technologyIds);
        Capacity expectedCapacity = new Capacity(1L, "Capacity Name", "Capacity Description", technologies);
        CapacityResponse expectedResponse = new CapacityResponse(1L, "Capacity Name", "Capacity Description", capacityTechnologyResponses);

        when(capacityRequestMapper.addRequestToCapacity(request)).thenReturn(expectedCapacity);
        when(capacityServicePort.saveCapacity(expectedCapacity)).thenReturn(expectedCapacity);
        when(capacityResponseMapper.toCapacityResponse(expectedCapacity)).thenReturn(expectedResponse);

        ResponseEntity<CapacityResponse> responseEntity = capacityRestControllerAdapter.addCapacity(request);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
        verify(capacityRequestMapper, times(1)).addRequestToCapacity(request);
        verify(capacityServicePort, times(1)).saveCapacity(expectedCapacity);
        verify(capacityResponseMapper, times(1)).toCapacityResponse(expectedCapacity);
    }

    @Test
    @DisplayName("Should Return All Capacities Successfully")
    void shouldReturnAllCapacitiesSuccessfully() {
        Integer page = 1;
        Integer size = 10;
        boolean isOrderByName = true;
        boolean isAscending = true;

        Technology javaTech = new Technology(1L, "Java", "Programming language");
        Technology pythonTech = new Technology(2L, "Python", "Programming language");
        Technology jsTech = new Technology(3L, "JavaScript", "Programming language");

        List<Capacity> capacities = Arrays.asList(
                new Capacity(1L, "Capacity 1", "Description 1", Arrays.asList(javaTech, pythonTech, jsTech)),
                new Capacity(2L, "Capacity 2", "Description 2", Arrays.asList(pythonTech, jsTech, javaTech))
        );

        List<CapacityResponse> expectedResponses = Arrays.asList(
                new CapacityResponse(1L, "Capacity 1", "Description 1", Collections.emptyList()),
                new CapacityResponse(2L, "Capacity 2", "Description 2", Collections.emptyList())
        );

        when(capacityServicePort.getAllCapacities(page, size, isOrderByName, isAscending)).thenReturn(capacities);
        when(capacityResponseMapper.toCapacityResponseList(capacities)).thenReturn(expectedResponses);

        ResponseEntity<List<CapacityResponse>> responseEntity = capacityRestControllerAdapter.getAllCapacities(page, size, isOrderByName, isAscending);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponses, responseEntity.getBody());
        verify(capacityServicePort, times(1)).getAllCapacities(page, size, isOrderByName, isAscending);
        verify(capacityResponseMapper, times(1)).toCapacityResponseList(capacities);
    }
}

