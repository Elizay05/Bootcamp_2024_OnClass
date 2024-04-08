package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.adapter;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.VersionBootcampEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.DateVersionBootcampAlreadyUseException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper.IVersionBootcampEntityMapper;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.IBootcampRepository;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.IVersionBootcampRepository;
import com.example.bootcamp2024onclass.domain.model.VersionBootcamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class VersionBootcampAdapterTest {
    @Mock
    private IVersionBootcampRepository versionBootcampRepository;

    @Mock
    private IVersionBootcampEntityMapper versionBootcampEntityMapper;

    @Mock
    private IBootcampRepository bootcampRepository;

    private VersionBootcampAdapter versionBootcampAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        versionBootcampAdapter = new VersionBootcampAdapter(versionBootcampRepository, versionBootcampEntityMapper, bootcampRepository);
    }

    @Test
    void testSaveVersionBootcamp_Success() {
        // Mocking
        VersionBootcamp versionBootcamp = new VersionBootcamp(1L, 1L, 50, LocalDate.of(2024, 4, 7), LocalDate.of(2024, 4, 14));
        BootcampEntity bootcampEntity = new BootcampEntity();
        bootcampEntity.setId(1L);
        bootcampEntity.setName("Test Bootcamp");
        VersionBootcampEntity versionBootcampEntity = new VersionBootcampEntity();
        versionBootcampEntity.setId(1L);
        versionBootcampEntity.setMaximumQuota(50);
        versionBootcampEntity.setStartDate(LocalDate.of(2024, 4, 7));
        versionBootcampEntity.setEndDate(LocalDate.of(2024, 4, 14));
        versionBootcampEntity.setBootcamp(bootcampEntity);

        // Stubbing
        when(versionBootcampRepository.findByBootcampId(1L)).thenReturn(new ArrayList<>());
        when(bootcampRepository.findById(1L)).thenReturn(Optional.of(bootcampEntity));
        when(versionBootcampEntityMapper.toEntity(versionBootcamp)).thenReturn(versionBootcampEntity);
        when(versionBootcampRepository.save(versionBootcampEntity)).thenReturn(versionBootcampEntity);
        when(versionBootcampEntityMapper.toModel(versionBootcampEntity)).thenReturn(versionBootcamp);

        // Invocation
        VersionBootcamp savedVersionBootcamp = versionBootcampAdapter.saveVersionBootcamp(versionBootcamp);

        // Verification
        assertNotNull(savedVersionBootcamp);
        assertEquals(versionBootcamp.getId(), savedVersionBootcamp.getId());
        assertEquals(versionBootcamp.getMaximumQuota(), savedVersionBootcamp.getMaximumQuota());
        assertEquals(versionBootcamp.getStartDate(), savedVersionBootcamp.getStartDate());
        assertEquals(versionBootcamp.getEndDate(), savedVersionBootcamp.getEndDate());
        assertEquals(versionBootcamp.getBootcampId(), savedVersionBootcamp.getBootcampId());
        assertEquals(versionBootcamp.getBootcampName(), savedVersionBootcamp.getBootcampName());
    }

    @Test
    void testSaveVersionBootcamp_DateAlreadyUsed() {
        // Mocking
        VersionBootcamp versionBootcamp = new VersionBootcamp(1L, 1L, 50, LocalDate.of(2024, 4, 7), LocalDate.of(2024, 4, 14));
        VersionBootcampEntity existingVersionBootcampEntity = new VersionBootcampEntity();
        existingVersionBootcampEntity.setId(2L);
        existingVersionBootcampEntity.setStartDate(LocalDate.of(2024, 4, 7));
        existingVersionBootcampEntity.setEndDate(LocalDate.of(2024, 5, 13));
        List<VersionBootcampEntity> existingVersions = new ArrayList<>();
        existingVersions.add(existingVersionBootcampEntity);

        // Stubbing
        when(versionBootcampRepository.findByBootcampId(1L)).thenReturn(existingVersions);

        // Invocation & Verification
        DateVersionBootcampAlreadyUseException exception = assertThrows(DateVersionBootcampAlreadyUseException.class, () -> versionBootcampAdapter.saveVersionBootcamp(versionBootcamp));
        assertNotNull(exception);
    }

    @Test
    void testSaveVersionBootcamp_BootcampNotFound() {
        // Mocking
        VersionBootcamp versionBootcamp = new VersionBootcamp(1L, 1L, 50, LocalDate.of(2024, 4, 7), LocalDate.of(2024, 4, 14));

        // Stubbing
        when(versionBootcampRepository.findByBootcampId(1L)).thenReturn(new ArrayList<>());
        when(bootcampRepository.findById(1L)).thenReturn(Optional.empty());

        // Invocation & Verification
        assertThrows(ElementNotFoundException.class, () -> versionBootcampAdapter.saveVersionBootcamp(versionBootcamp));
    }

    @Test
    @DisplayName("When_GetAllVersionBootcamps_WithBootcampName_Expect_SuccessfulResult")
    void testGetAllVersionBootcampsWithBootcampName() {
        when(bootcampRepository.findByName("SampleBootcamp")).thenReturn(Optional.of(new BootcampEntity()));
        when(versionBootcampRepository.findByBootcamp(any(BootcampEntity.class), any(Pageable.class)))
                .thenReturn(Arrays.asList(new VersionBootcampEntity(), new VersionBootcampEntity()));

        List<VersionBootcamp> result = versionBootcampAdapter.getAllVersionBootcamps(0, 10, null, true, "SampleBootcamp");

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("When_GetAllVersionBootcamps_WithOutBootcampName_Expect_SuccessfulResult")
    void testGetAllVersionBootcampsWithoutBootcampName() {
        when(versionBootcampRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Arrays.asList(new VersionBootcampEntity(), new VersionBootcampEntity())));

        List<VersionBootcamp> result = versionBootcampAdapter.getAllVersionBootcamps(0, 10, null, true, null);

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("When_GetAllVersionBootcamps_WithOrderByMaximumQuota_Expect_SuccessfulResult")
    void testGetAllVersionBootcampsWithOrderByMaximumQuota() {
        when(versionBootcampRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Arrays.asList(new VersionBootcampEntity(), new VersionBootcampEntity())));

        List<VersionBootcamp> result = versionBootcampAdapter.getAllVersionBootcamps(0, 10, "maximumQuota", true, null);

        assertEquals(2, result.size());
    }
    @Test
    @DisplayName("When_GetAllVersionBootcamps_WithOrderByStartDate_Expect_SuccessfulResult")
    void testGetAllVersionBootcampsWithOrderByStartDate() {
        when(versionBootcampRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Arrays.asList(new VersionBootcampEntity(), new VersionBootcampEntity())));

        List<VersionBootcamp> result = versionBootcampAdapter.getAllVersionBootcamps(0, 10, "startDate", true, null);

        assertEquals(2, result.size());
    }
}
