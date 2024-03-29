package com.example.bootcamp2024onclass.adapters.driving.http.controller;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.BootcampCapacityResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.BootcampResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddBootcampRequest;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.IBootcampRequestMapper;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.IBootcampResponseMapper;
import com.example.bootcamp2024onclass.domain.api.IBootcampServicePort;
import com.example.bootcamp2024onclass.domain.model.Bootcamp;
import com.example.bootcamp2024onclass.domain.model.Capacity;
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

}
