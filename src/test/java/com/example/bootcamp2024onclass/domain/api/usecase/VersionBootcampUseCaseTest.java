package com.example.bootcamp2024onclass.domain.api.usecase;

import com.example.bootcamp2024onclass.domain.exception.StartDateAfterEndDateException;
import com.example.bootcamp2024onclass.domain.exception.StartDateBeforeCurrentDateException;
import com.example.bootcamp2024onclass.domain.model.VersionBootcamp;
import com.example.bootcamp2024onclass.domain.spi.IVersionBootcampPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class VersionBootcampUseCaseTest {

    @Mock
    private IVersionBootcampPersistencePort versionBootcampPersistencePort;

    @InjectMocks
    private VersionBootcampUseCase versionBootcampUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("When_SaveVersionBootcamp_Expect_Success")
    void testSaveVersionBootcampSuccess() {
        Long id = 1L;
        Long bootcampId = 2L;
        Integer maximumQuota = 50;
        LocalDate startDate = LocalDate.of(2025, 1, 30);
        LocalDate endDate = LocalDate.of(2025, 12, 30);

        VersionBootcamp versionBootcamp = new VersionBootcamp(id, bootcampId, maximumQuota, startDate, endDate);

        versionBootcampUseCase.saveVersionBootcamp(versionBootcamp);

        verify(versionBootcampPersistencePort, times(1)).saveVersionBootcamp(versionBootcamp);
    }

    @Test
    @DisplayName("When_SaveVersionBootcamp_Expect_StartDateBeforeCurrentDateException")
    void testSaveVersionBootcampStartDateBeforeCurrentDateException() {
        Long id = 1L;
        Long bootcampId = 2L;
        Integer maximumQuota = 50;
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.of(2025, 12, 30);

        VersionBootcamp versionBootcamp = new VersionBootcamp(id, bootcampId, maximumQuota, startDate, endDate);

        // Act & Assert
        assertThrows(StartDateBeforeCurrentDateException.class, () -> versionBootcampUseCase.saveVersionBootcamp(versionBootcamp));
    }

    @Test
    @DisplayName("When_SaveVersionBootcamp_Expect_StartDateAfterEndDateException")
    void testSaveVersionBootcampStartDateAfterEndDateException() {
        Long id = 1L;
        Long bootcampId = 2L;
        Integer maximumQuota = 50;
        LocalDate startDate = LocalDate.of(2025, 12, 30); // End date before start date
        LocalDate endDate = LocalDate.of(2025, 1, 30);

        VersionBootcamp versionBootcamp = new VersionBootcamp(id, bootcampId, maximumQuota, startDate, endDate);

        assertThrows(StartDateAfterEndDateException.class, () -> versionBootcampUseCase.saveVersionBootcamp(versionBootcamp));
    }

    @Test
    @DisplayName("When_SaveVersionBootcamp_Expect_Failed")
    void testSaveVersionBootcampFailed() {
        Long id = 1L;
        Long bootcampId = 2L;
        Integer maximumQuota = 50;
        LocalDate startDate = LocalDate.of(2025, 1, 30);
        LocalDate endDate = LocalDate.of(2025, 12, 30);

        VersionBootcamp versionBootcamp = new VersionBootcamp(id, bootcampId, maximumQuota, startDate, endDate);
        doThrow(new RuntimeException("Could not save versionBootcamp")).when(versionBootcampPersistencePort).saveVersionBootcamp(versionBootcamp);

        assertThrows(RuntimeException.class, () -> {
            versionBootcampUseCase.saveVersionBootcamp(versionBootcamp);
        });
    }

    @Test
    @DisplayName("When_GetAllVersionBootcamps_Expect_SuccessfulRetrieval")
    void testGetAllVersionBootcamps_Success() {
        List<VersionBootcamp> expectedVersionBootcamps = new ArrayList<>();
        when(versionBootcampPersistencePort.getAllVersionBootcamps(1, 10, "startDate", true, "Bootcamp A")).thenReturn(expectedVersionBootcamps);

        List<VersionBootcamp> result = versionBootcampUseCase.getAllVersionBootcamps(1, 10, "startDate", true, "Bootcamp A");

        assertEquals(expectedVersionBootcamps, result);
    }
}
