package com.example.bootcamp2024onclass.adapters.driving.http.controller;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.CapacityResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.CapacityTechnologyResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddCapacityRequest;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.ICapacityRequestMapper;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.ICapacityResponseMapper;
import com.example.bootcamp2024onclass.domain.api.ICapacityServicePort;
import com.example.bootcamp2024onclass.domain.model.Capacity;
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
import java.util.Collections;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
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

    @Test
    @DisplayName("When_GetAllCapacities_Expect_CorrectResponse")
    void shouldReturnAllCapacitiesSuccessfully() {
        PaginationCriteria criteria = new PaginationCriteria(0, 10, SortDirection.ASC, "name");
        List<Capacity> mockCapacities = Arrays.asList(
                new Capacity(1L, "Capacity A", "Description A", Arrays.asList(new Technology(1L), new Technology(2L), new Technology(3L))),
                new Capacity(2L, "Capacity B", "Description B", Arrays.asList(new Technology(4L), new Technology(5L), new Technology(6L)))
        );
        CustomPage<Capacity> mockCustomPage = new CustomPage<>(mockCapacities, 0, 10, 2, 1);

        when(capacityServicePort.getAllCapacities(any(PaginationCriteria.class))).thenReturn(mockCustomPage);

        when(capacityResponseMapper.toCapacityResponse(any(Capacity.class)))
                .thenAnswer(invocation -> {
                    Capacity capacity = invocation.getArgument(0);
                    return new CapacityResponse(capacity.getId(), capacity.getName(), capacity.getDescription(), Collections.emptyList());
                });

        ResponseEntity<CustomPage<CapacityResponse>> responseEntity = capacityRestControllerAdapter.getAllCapacities(0, 10, true, true);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(mockCapacities.size(), responseEntity.getBody().getContent().size());
        assertEquals(mockCapacities.get(0).getName(), responseEntity.getBody().getContent().get(0).getName());
        assertEquals(mockCapacities.get(1).getName(), responseEntity.getBody().getContent().get(1).getName());
    }


    @Test
    @DisplayName("When_GetTotalBodyCapacities_Expect_CorrectResponse")
    void testGetTotalBodyCapacities() {
        List<Capacity> mockCapacities = Arrays.asList(
                new Capacity(1L, "Java", "Programming language", Arrays.asList(new Technology(1L), new Technology(2L), new Technology(3L))),
                new Capacity(2L, "Python", "Programming language", Arrays.asList(new Technology(1L), new Technology(2L), new Technology(3L)))
        );

        List<CapacityResponse> mockCapacityResponses = Arrays.asList(
                new CapacityResponse(1L, "Java", "Programming language", Collections.emptyList()),
                new CapacityResponse(2L, "Python", "Programming language", Collections.emptyList())
        );

        when(capacityServicePort.getTotalBodyCapacities()).thenReturn(mockCapacities);

        when(capacityResponseMapper.toCapacityResponseList(mockCapacities)).thenReturn(mockCapacityResponses);

        ResponseEntity<List<CapacityResponse>> responseEntity = capacityRestControllerAdapter.getTotalBodyCapacities();

        assertEquals(200, responseEntity.getStatusCodeValue());

        assertEquals(mockCapacityResponses, responseEntity.getBody());

        verify(capacityServicePort, times(1)).getTotalBodyCapacities();
        verify(capacityResponseMapper, times(1)).toCapacityResponseList(mockCapacities);
    }
}

