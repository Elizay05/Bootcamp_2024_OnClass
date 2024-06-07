package com.example.bootcamp2024onclass.domain.api.usecase;

import com.example.bootcamp2024onclass.domain.model.CustomPage;
import com.example.bootcamp2024onclass.domain.model.PaginationCriteria;
import com.example.bootcamp2024onclass.domain.model.Technology;
import com.example.bootcamp2024onclass.domain.spi.ITechnologyPersistencePort;
import com.example.bootcamp2024onclass.domain.util.SortDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TechnologyUseCaseTest {
    @Mock
    private ITechnologyPersistencePort technologyPersistencePort;

    private TechnologyUseCase technologyUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        technologyUseCase = new TechnologyUseCase(technologyPersistencePort);
    }

    @Test
    @DisplayName("When_SaveTechnology_Expect_Success")
    void testSaveTechnologySuccess() {
        Long id = 2L;
        String name = "Java";
        String description = "Hola mundo";

        Technology technology = new Technology(id, name, description);

        technologyUseCase.saveTechnology(technology);

        verify(technologyPersistencePort, times(1)).saveTechnology(technology);
    }

    @Test
    @DisplayName("When_SaveTechnology_Expect_Failed")
    void testSaveTechnologyFailed() {
        Long id = 2L;
        String name = "";
        String description = "Hola mundo";

        Technology technology = new Technology(id, name, description);
        doThrow(new RuntimeException("Could not save technology")).when(technologyPersistencePort).saveTechnology(technology);

        assertThrows(RuntimeException.class, () -> {
            technologyUseCase.saveTechnology(technology);
        });
    }


    @Test
    @DisplayName("When_GetAllTechnologies_Expect_SuccessfulRetrieval")
    void testGetAllTechnologies_Success() {
        PaginationCriteria criteria = new PaginationCriteria(0, 10, SortDirection.ASC, "name");
        List<Technology> mockTechnologies = Arrays.asList(
                new Technology(1L, "Java", "Programming language"),
                new Technology(2L, "Python", "Programming language")
        );
        CustomPage<Technology> mockCustomPage = new CustomPage<>(mockTechnologies, 0, 10, 2, 1);

        when(technologyPersistencePort.getAllTechnologies(criteria)).thenReturn(mockCustomPage);

        CustomPage<Technology> result = technologyUseCase.getAllTechnologies(criteria);

        verify(technologyPersistencePort).getAllTechnologies(criteria);

        assertEquals(mockCustomPage, result, "CustomPage should match the mock data");
    }

    @Test
    @DisplayName("When_GetAllTotalTechnologies_Expect_SuccessfulRetrieval")
    void testGetTotalBodyTechnologies_Success() {
        List<Technology> mockTechnologies = Arrays.asList(
                new Technology(1L, "Java", "Programming language"),
                new Technology(2L, "Python", "Programming language")
        );

        when(technologyPersistencePort.getTotalBodyTechnologies()).thenReturn(mockTechnologies);

        List<Technology> result = technologyUseCase.getTotalBodyTechnologies();

        assertEquals(mockTechnologies, result);

        verify(technologyPersistencePort, times(1)).getTotalBodyTechnologies();
    }
}
