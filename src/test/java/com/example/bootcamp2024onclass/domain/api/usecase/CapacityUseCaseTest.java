package com.example.bootcamp2024onclass.domain.api.usecase;

import com.example.bootcamp2024onclass.domain.model.Capacity;
import com.example.bootcamp2024onclass.domain.model.CustomPage;
import com.example.bootcamp2024onclass.domain.model.PaginationCriteria;
import com.example.bootcamp2024onclass.domain.model.Technology;
import com.example.bootcamp2024onclass.domain.spi.ICapacityPersistencePort;
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

    @Test
    @DisplayName("When_GetAllCapacities_Expect_SuccessfulRetrieval")
    void testGetAllCapacities_Success() {
        PaginationCriteria criteria = new PaginationCriteria(0, 10, SortDirection.ASC, "name");
        List<Capacity> mockCapacities = Arrays.asList(
                new Capacity(1L, "Capacity A", "Description A", Arrays.asList(new Technology(1L), new Technology(2L), new Technology(3L))),
                new Capacity(2L, "Capacity B", "Description B", Arrays.asList(new Technology(2L), new Technology(3L), new Technology(4L)))
        );
        CustomPage<Capacity> mockCustomPage = new CustomPage<>(mockCapacities, 0, 10, 2, 1);

        when(capacityPersistencePort.getAllCapacities(criteria)).thenReturn(mockCustomPage);

        CustomPage<Capacity> result = capacityUseCase.getAllCapacities(criteria);

        verify(capacityPersistencePort, times(1)).getAllCapacities(criteria);

        assertEquals(mockCustomPage.getPageNumber(), result.getPageNumber());
        assertEquals(mockCustomPage.getPageSize(), result.getPageSize());
        assertEquals(mockCustomPage.getTotalElements(), result.getTotalElements());
        assertEquals(mockCustomPage.getTotalPages(), result.getTotalPages());
        assertEquals(mockCapacities.size(), result.getContent().size());
        assertEquals(mockCapacities.get(0).getId(), result.getContent().get(0).getId());
        assertEquals(mockCapacities.get(0).getName(), result.getContent().get(0).getName());
        assertEquals(mockCapacities.get(1).getId(), result.getContent().get(1).getId());
        assertEquals(mockCapacities.get(1).getName(), result.getContent().get(1).getName());
    }

    @Test
    @DisplayName("When_GetAllTotalCapacities_Expect_SuccessfulRetrieval")
    void testGetTotalBodyCapacities_Success() {
        List<Technology> mockTechnologies = Arrays.asList(
                new Technology(1L, "Java", "Programming language"),
                new Technology(2L, "Python", "Programming language"),
                new Technology(3L, "C++", "Programming language")
        );

        List<Capacity> mockCapacities = Arrays.asList(
                new Capacity(1L, "Java", "Programming language", mockTechnologies),
                new Capacity(2L, "Python", "Programming language", mockTechnologies)
        );

        when(capacityPersistencePort.getTotalBodyCapacities()).thenReturn(mockCapacities);

        List<Capacity> result = capacityUseCase.getTotalBodyCapacities();

        assertEquals(mockCapacities, result);

        verify(capacityPersistencePort, times(1)).getTotalBodyCapacities();
    }
}
