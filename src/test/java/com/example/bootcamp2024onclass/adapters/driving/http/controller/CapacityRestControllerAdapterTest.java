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
    @DisplayName("When_AddCapacity_Expect_CorrectResponse")
    void testAddCapacity() {
        List<Long> technologyIds = Arrays.asList(1L, 2L, 3L);

        List<Technology> technologies = Arrays.asList(
                new Technology(1L),
                new Technology(2L),
                new Technology(3L)
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
}

