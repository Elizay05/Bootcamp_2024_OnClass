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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @DisplayName("Save Capacity Success")
    void testSaveCapacitySuccess() {
        Long id = 1L;
        String name = "Capacity";
        String description = "Description";
        List<Technology> technologies = Arrays.asList(
                mock(Technology.class),
                mock(Technology.class),
                mock(Technology.class)
        );

        Capacity capacity = new Capacity(id, name, description, technologies);

        capacityUseCase.saveCapacity(capacity);

        verify(capacityPersistencePort, times(1)).saveCapacity(capacity);
    }

    @Test
    @DisplayName("Save Capacity Failed")
    void testSaveCapacityFailed() {
        Long id = 1L;
        String name = "Capacity";
        String description = "Description";
        List<Technology> technologies = Arrays.asList(
                mock(Technology.class),
                mock(Technology.class),
                mock(Technology.class)
        );

        Capacity capacity = new Capacity(id, name, description, technologies);
            doThrow(new RuntimeException("Could not save capacity")).when(capacityPersistencePort).saveCapacity(capacity);

        assertThrows(RuntimeException.class, () -> {
            capacityUseCase.saveCapacity(capacity);
        });
    }

    @Test
    @DisplayName("When_GetAllCapacities_Expect_SuccessfulRetrieval")
    void testGetAllCapacities_Success() {
        Integer page = 1;
        Integer size = 10;
        boolean isOrderByName = true;
        boolean isAscending = true;

        Technology tech1 = new Technology(1L, "Java", "Programming language");
        Technology tech2 = new Technology(2L, "Python", "Programming language");
        Technology tech3 = new Technology(3L, "JavaScript", "Programming language");

        Capacity capacity1 = new Capacity(1L, "Capacity 1", "Description 1", Arrays.asList(tech1, tech2, tech3));
        Capacity capacity2 = new Capacity(2L, "Capacity 2", "Description 2", Arrays.asList(tech2, tech3, tech1));

        List<Capacity> expectedCapacities = Arrays.asList(capacity1, capacity2);

        when(capacityPersistencePort.getAllCapacities(page, size, isOrderByName, isAscending)).thenReturn(expectedCapacities);

        List<Capacity> actualCapacities = capacityUseCase.getAllCapacities(page, size, isOrderByName, isAscending);

        assertEquals(expectedCapacities, actualCapacities);
        verify(capacityPersistencePort, times(1)).getAllCapacities(page, size, isOrderByName, isAscending);
    }
}
