package com.example.bootcamp2024onclass.domain.api.usecase;

import com.example.bootcamp2024onclass.domain.model.Bootcamp;
import com.example.bootcamp2024onclass.domain.model.Capacity;
import com.example.bootcamp2024onclass.domain.spi.IBootcampPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

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
}
