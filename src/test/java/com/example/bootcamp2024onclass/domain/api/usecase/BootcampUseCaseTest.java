package com.example.bootcamp2024onclass.domain.api.usecase;

import com.example.bootcamp2024onclass.domain.model.*;
import com.example.bootcamp2024onclass.domain.spi.IBootcampPersistencePort;
import com.example.bootcamp2024onclass.domain.util.SortDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BootcampUseCaseTest {

    @Mock
    private IBootcampPersistencePort bootcampPersistencePort;

    @InjectMocks
    private BootcampUseCase bootcampUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("When_SaveBootcamp_Expect_Success")
    void testSaveBootcampSuccess() {
        Long id = 1L;
        String name = "Bootcamp";
        String description = "Description";
        List<Capacity> capacities = Arrays.asList(
                new Capacity(1L),
                new Capacity(2L),
                new Capacity(3L)
        );

        Bootcamp bootcamp = new Bootcamp(id, name, description, capacities);

        bootcampUseCase.saveBootcamp(bootcamp);

        verify(bootcampPersistencePort, times(1)).saveBootcamp(bootcamp);
    }

    @Test
    @DisplayName("When_SaveBootcamp_Expect_Failed")
    void testSaveBootcampFailed() {
        Long id = 1L;
        String name = "Bootcamp";
        String description = "Description";
        List<Capacity> capacities = Arrays.asList(
                new Capacity(1L),
                new Capacity(2L),
                new Capacity(3L)
        );

        Bootcamp bootcamp = new Bootcamp(id, name, description, capacities);
        doThrow(new RuntimeException("Could not save bootcamp")).when(bootcampPersistencePort).saveBootcamp(bootcamp);

        assertThrows(RuntimeException.class, () -> {
            bootcampUseCase.saveBootcamp(bootcamp);
        });
    }

    @Test
    @DisplayName("When_GetAllBootcamps_Expect_SuccessfulRetrieval")
    void testGetAllBootcamps_Success() {
        PaginationCriteria criteria = new PaginationCriteria(0, 10, SortDirection.ASC, "name");
        List<Bootcamp> mockBootcamps = Arrays.asList(
                new Bootcamp(1L, "Capacity A", "Description A", Arrays.asList(new Capacity(1L), new Capacity(2L), new Capacity(3L))),
                new Bootcamp(2L, "Capacity B", "Description B", Arrays.asList(new Capacity(2L), new Capacity(3L), new Capacity(4L)))
        );
        CustomPage<Bootcamp> mockCustomPage = new CustomPage<>(mockBootcamps, 0, 10, 2, 1);

        when(bootcampPersistencePort.getAllBootcamps(criteria)).thenReturn(mockCustomPage);

        CustomPage<Bootcamp> result = bootcampUseCase.getAllBootcamps(criteria);

        verify(bootcampPersistencePort, times(1)).getAllBootcamps(criteria);

        assertEquals(mockCustomPage.getPageNumber(), result.getPageNumber());
        assertEquals(mockCustomPage.getPageSize(), result.getPageSize());
        assertEquals(mockCustomPage.getTotalElements(), result.getTotalElements());
        assertEquals(mockCustomPage.getTotalPages(), result.getTotalPages());
        assertEquals(mockBootcamps.size(), result.getContent().size());
        assertEquals(mockBootcamps.get(0).getId(), result.getContent().get(0).getId());
        assertEquals(mockBootcamps.get(0).getName(), result.getContent().get(0).getName());
        assertEquals(mockBootcamps.get(1).getId(), result.getContent().get(1).getId());
        assertEquals(mockBootcamps.get(1).getName(), result.getContent().get(1).getName());
    }
}
