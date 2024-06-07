package com.example.bootcamp2024onclass.adapters.driving.http.controller;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.BootcampCapacityResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.BootcampResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddBootcampRequest;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.IBootcampRequestMapper;
import com.example.bootcamp2024onclass.adapters.driving.http.mapper.IBootcampResponseMapper;
import com.example.bootcamp2024onclass.domain.api.IBootcampServicePort;
import com.example.bootcamp2024onclass.domain.model.Bootcamp;
import com.example.bootcamp2024onclass.domain.model.Capacity;
import com.example.bootcamp2024onclass.domain.model.CustomPage;
import com.example.bootcamp2024onclass.domain.model.PaginationCriteria;
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
    @DisplayName("When_GetAllBootcamps_Expect_CorrectResponse")
    void shouldReturnAllBootcampsSuccessfully() {
        PaginationCriteria criteria = new PaginationCriteria(0, 10, SortDirection.ASC, "name");
        List<Bootcamp> mockBootcamps = Arrays.asList(
                new Bootcamp(1L, "Bootcamp A", "Description A", Arrays.asList(new Capacity(1L), new Capacity(2L), new Capacity(3L))),
                new Bootcamp(2L, "Bootcamp B", "Description B", Arrays.asList(new Capacity(4L), new Capacity(5L), new Capacity(6L)))
        );
        CustomPage<Bootcamp> mockCustomPage = new CustomPage<>(mockBootcamps, 0, 10, 2, 1);

        when(bootcampServicePort.getAllBootcamps(any(PaginationCriteria.class))).thenReturn(mockCustomPage);

        when(bootcampResponseMapper.toBootcampResponse(any(Bootcamp.class)))
                .thenAnswer(invocation -> {
                    Bootcamp bootcamp = invocation.getArgument(0);
                    return new BootcampResponse(bootcamp.getId(), bootcamp.getName(), bootcamp.getDescription(), Collections.emptyList());
                });

        ResponseEntity<CustomPage<BootcampResponse>> responseEntity = bootcampRestControllerAdapter.getAllBootcamps(0, 10, true, true);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(mockBootcamps.size(), responseEntity.getBody().getContent().size());
        assertEquals(mockBootcamps.get(0).getName(), responseEntity.getBody().getContent().get(0).getName());
        assertEquals(mockBootcamps.get(1).getName(), responseEntity.getBody().getContent().get(1).getName());
    }
}
