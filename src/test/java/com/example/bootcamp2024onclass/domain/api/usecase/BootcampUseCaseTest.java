package com.example.bootcamp2024onclass.domain.api.usecase;

import com.example.bootcamp2024onclass.domain.model.Bootcamp;
import com.example.bootcamp2024onclass.domain.model.Capacity;
import com.example.bootcamp2024onclass.domain.model.Technology;
import com.example.bootcamp2024onclass.domain.spi.IBootcampPersistencePort;
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
        Integer page = 1;
        Integer size = 10;
        boolean isOrderByName = true;
        boolean isAscending = true;

        List<Technology> technologies = Arrays.asList(
                new Technology(1L),
                new Technology(2L),
                new Technology(3L)
        );

        Capacity capacity1 = new Capacity(1L, "Capacity 1", "Description 1", technologies);
        Capacity capacity2 = new Capacity(2L, "Capacity 2", "Description 2", technologies);
        Capacity capacity3 = new Capacity(3L, "Capacity 3", "Description 3", technologies);

        Bootcamp bootcamp1 = new Bootcamp(1L, "Bootcamp 1", "Description 1", Arrays.asList(capacity1, capacity2, capacity3));
        Bootcamp bootcamp2 = new Bootcamp(2L, "Bootcamp 2", "Description 2", Arrays.asList(capacity2, capacity3, capacity1));

        List<Bootcamp> expectedBootcamps = Arrays.asList(bootcamp1, bootcamp2);

        when(bootcampPersistencePort.getAllBootcamps(page, size, isOrderByName, isAscending)).thenReturn(expectedBootcamps);

        List<Bootcamp> actualBootcamps = bootcampUseCase.getAllBootcamps(page, size, isOrderByName, isAscending);

        assertEquals(expectedBootcamps, actualBootcamps);
        verify(bootcampPersistencePort, times(1)).getAllBootcamps(page, size, isOrderByName, isAscending);
    }
}
