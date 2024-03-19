package com.example.bootcamp2024onclass.domain.api.usecase;

import com.example.bootcamp2024onclass.domain.model.Technology;
import com.example.bootcamp2024onclass.domain.spi.ITechnologyPersistencePort;
import org.junit.jupiter.api.BeforeEach;
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
    void testSaveTechnologySuccess() {
        Long id = 2L;
        String name = "Java";
        String description = "Hola mundo";

        Technology technology = new Technology(id, name, description);

        technologyUseCase.saveTechnology(technology);

        verify(technologyPersistencePort, times(1)).saveTechnology(technology);
    }

    @Test
    void testSaveTechnologyFailed() {
        Long id = 2L;
        String name = "";
        String description = "Hola mundo";

        Technology technology = new Technology(id, name, description);
        doThrow(new RuntimeException("No se pudo guardar la tecnología")).when(technologyPersistencePort).saveTechnology(technology);

        assertThrows(RuntimeException.class, () -> {
            technologyUseCase.saveTechnology(technology);
        });
    }


    @Test
    void testGetAllTechnologies_Success() {
        Integer page = 1;
        Integer size = 10;
        Boolean isAscending = true;
        List<Technology> expectedTechnologies = Arrays.asList(
                new Technology(1L, "Java", "Programming language"),
                new Technology(2L, "Python", "Programming language")
        );
        when(technologyPersistencePort.getAllTechnologies(page, size, isAscending)).thenReturn(expectedTechnologies);

        List<Technology> actualTechnologies = technologyUseCase.getAllTechnologies(page, size, isAscending);

        assertEquals(expectedTechnologies, actualTechnologies);
        verify(technologyPersistencePort, times(1)).getAllTechnologies(page, size, isAscending);
    }

}
