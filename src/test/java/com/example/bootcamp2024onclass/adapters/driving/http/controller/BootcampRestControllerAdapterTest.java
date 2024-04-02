package com.example.bootcamp2024onclass.adapters.driving.http.controller;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.BootcampCapacityResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.BootcampResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddBootcampRequest;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.IBootcampRequestMapper;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.IBootcampResponseMapper;
import com.example.bootcamp2024onclass.domain.api.IBootcampServicePort;
import com.example.bootcamp2024onclass.domain.model.Bootcamp;
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

class BootcampRestControllerAdapterTest {

    @Mock
    private IBootcampServicePort bootcampServicePort;

    @Mock
    private IBootcampRequestMapper bootcampRequestMapper;

    @Mock
    private IBootcampResponseMapper bootcampResponseMapper;

    @InjectMocks
    private BootcampRestControllerAdapter bootcampRestControllerAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("When_AddBootcamp_Expect_CorrectResponse")
    void testAddBootcamp() {
        List<Long> capacityIds = Arrays.asList(1L, 2L, 3L);

        List<Capacity> capacities = Arrays.asList(
                new Capacity(1L),
                new Capacity(2L),
                new Capacity(3L)
        );

        List<BootcampCapacityResponse> bootcampCapacityResponses = Arrays.asList(
                mock(BootcampCapacityResponse.class),
                mock(BootcampCapacityResponse.class),
                mock(BootcampCapacityResponse.class)
        );
        AddBootcampRequest request = new AddBootcampRequest("Bootcamp Name", "Bootcamp Description", capacityIds);
        Bootcamp expectedBootcamp = new Bootcamp(1L, "Bootcamp Name", "Bootcamp Description", capacities);
        BootcampResponse expectedResponse = new BootcampResponse(1L, "Bootcamp Name", "Bootcamp Description", bootcampCapacityResponses);

        when(bootcampRequestMapper.addRequestToBootcamp(request)).thenReturn(expectedBootcamp);
        when(bootcampServicePort.saveBootcamp(expectedBootcamp)).thenReturn(expectedBootcamp);
        when(bootcampResponseMapper.toBootcampResponse(expectedBootcamp)).thenReturn(expectedResponse);

        ResponseEntity<BootcampResponse> responseEntity = bootcampRestControllerAdapter.addBootcamp(request);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
        verify(bootcampRequestMapper, times(1)).addRequestToBootcamp(request);
        verify(bootcampServicePort, times(1)).saveBootcamp(expectedBootcamp);
        verify(bootcampResponseMapper, times(1)).toBootcampResponse(expectedBootcamp);
    }

    @Test
    @DisplayName("When_GetAllBootcamps_Expect_SuccessfulResponse")
    void shouldReturnAllBootcampsSuccessfully() {
        Integer page = 1;
        Integer size = 10;
        boolean isOrderByName = true;
        boolean isAscending = true;

        List<Technology> technologies = Arrays.asList(
                new Technology(1L),
                new Technology(2L),
                new Technology(3L)
        );

        Capacity capacity1 = new Capacity(1L, "capacity1", "Description 1", technologies);
        Capacity capacity2 = new Capacity(2L, "capacity2", "Description 2", technologies);
        Capacity capacity3 = new Capacity(3L, "capacity3", "Description 3", technologies);

        List<Bootcamp> bootcamps = Arrays.asList(
                new Bootcamp(1L, "Bootcamp 1", "Description 1", Arrays.asList(capacity1, capacity2, capacity3)),
                new Bootcamp(2L, "Bootcamp 2", "Description 2", Arrays.asList(capacity3, capacity2, capacity1))
        );

        List<BootcampResponse> expectedResponses = Arrays.asList(
                new BootcampResponse(1L, "Bootcamp 1", "Description 1", Collections.emptyList()),
                new BootcampResponse(2L, "Bootcamp 2", "Description 2", Collections.emptyList())
        );

        when(bootcampServicePort.getAllBootcamps(page, size, isOrderByName, isAscending)).thenReturn(bootcamps);
        when(bootcampResponseMapper.toBootcampResponseList(bootcamps)).thenReturn(expectedResponses);

        ResponseEntity<List<BootcampResponse>> responseEntity = bootcampRestControllerAdapter.getAllBootcamps(page, size, isOrderByName, isAscending);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponses, responseEntity.getBody());
        verify(bootcampServicePort, times(1)).getAllBootcamps(page, size, isOrderByName, isAscending);
        verify(bootcampResponseMapper, times(1)).toBootcampResponseList(bootcamps);
    }
}
