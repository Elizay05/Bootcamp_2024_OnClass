package com.example.bootcamp2024onclass.domain.api.usecase;

import com.example.bootcamp2024onclass.domain.model.Technology;
import com.example.bootcamp2024onclass.domain.spi.ITechnologyPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        doThrow(new RuntimeException("No se pudo guardar la tecnología")).when(technologyPersistencePort).saveTechnology(technology);

        assertThrows(RuntimeException.class, () -> {
            technologyUseCase.saveTechnology(technology);
        });
    }

}
