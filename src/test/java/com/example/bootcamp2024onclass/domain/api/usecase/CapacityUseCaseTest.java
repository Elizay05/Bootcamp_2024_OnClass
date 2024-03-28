package com.example.bootcamp2024onclass.domain.api.usecase;

import com.example.bootcamp2024onclass.domain.model.Capacity;
import com.example.bootcamp2024onclass.domain.model.Technology;
import com.example.bootcamp2024onclass.domain.spi.ICapacityPersistencePort;
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

class CapacityUseCaseTest {
    @Mock
    private ICapacityPersistencePort capacityPersistencePort;

    @InjectMocks
    private CapacityUseCase capacityUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("When_SaveCapacity_Expect_Success")
    void testSaveCapacitySuccess() {
        Long id = 1L;
        String name = "Capacity";
        String description = "Description";
        List<Technology> technologies = Arrays.asList(
                new Technology(1L),
                new Technology(2L),
                new Technology(3L)
        );

        Capacity capacity = new Capacity(id, name, description, technologies);

        capacityUseCase.saveCapacity(capacity);

        verify(capacityPersistencePort, times(1)).saveCapacity(capacity);
    }

    @Test
    @DisplayName("When_SaveCapacity_Expect_Failed")
    void testSaveCapacityFailed() {
        Long id = 1L;
        String name = "Capacity";
        String description = "Description";
        List<Technology> technologies = Arrays.asList(
                new Technology(1L),
                new Technology(2L),
                new Technology(3L)
        );

        Capacity capacity = new Capacity(id, name, description, technologies);
            doThrow(new RuntimeException("Could not save capacity")).when(capacityPersistencePort).saveCapacity(capacity);

        assertThrows(RuntimeException.class, () -> {
            capacityUseCase.saveCapacity(capacity);
        });
    }
}
