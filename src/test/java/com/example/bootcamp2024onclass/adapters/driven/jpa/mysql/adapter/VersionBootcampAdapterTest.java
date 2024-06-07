package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.adapter;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.VersionBootcampEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.DateVersionBootcampAlreadyUseException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper.IVersionBootcampEntityMapper;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.IBootcampRepository;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.IVersionBootcampRepository;
import com.example.bootcamp2024onclass.domain.model.CustomPage;
import com.example.bootcamp2024onclass.domain.model.PaginationCriteria;
import com.example.bootcamp2024onclass.domain.model.VersionBootcamp;
import com.example.bootcamp2024onclass.domain.util.SortDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    @DisplayName("Save Version Bootcamp - Success: Should save a new version bootcamp")
    void testSaveVersionBootcamp_Success() {
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

        when(versionBootcampRepository.findByBootcampId(1L)).thenReturn(new ArrayList<>());
        when(bootcampRepository.findById(1L)).thenReturn(Optional.of(bootcampEntity));
        when(versionBootcampEntityMapper.toEntity(versionBootcamp)).thenReturn(versionBootcampEntity);
        when(versionBootcampRepository.save(versionBootcampEntity)).thenReturn(versionBootcampEntity);
        when(versionBootcampEntityMapper.toModel(versionBootcampEntity)).thenReturn(versionBootcamp);

        VersionBootcamp savedVersionBootcamp = versionBootcampAdapter.saveVersionBootcamp(versionBootcamp);

        assertNotNull(savedVersionBootcamp);
        assertEquals(versionBootcamp.getId(), savedVersionBootcamp.getId());
        assertEquals(versionBootcamp.getMaximumQuota(), savedVersionBootcamp.getMaximumQuota());
        assertEquals(versionBootcamp.getStartDate(), savedVersionBootcamp.getStartDate());
        assertEquals(versionBootcamp.getEndDate(), savedVersionBootcamp.getEndDate());
        assertEquals(versionBootcamp.getBootcampId(), savedVersionBootcamp.getBootcampId());
        assertEquals(versionBootcamp.getBootcampName(), savedVersionBootcamp.getBootcampName());
    }

    @Test
    @DisplayName("Save Version Bootcamp - Date Already Used: Should throw exception when date already used")
    void testSaveVersionBootcamp_DateAlreadyUsed() {
        VersionBootcamp versionBootcamp = new VersionBootcamp(1L, 1L, 50, LocalDate.of(2024, 4, 7), LocalDate.of(2024, 4, 14));
        VersionBootcampEntity existingVersionBootcampEntity = new VersionBootcampEntity();
        existingVersionBootcampEntity.setId(2L);
        existingVersionBootcampEntity.setStartDate(LocalDate.of(2024, 4, 7));
        existingVersionBootcampEntity.setEndDate(LocalDate.of(2024, 5, 13));
        List<VersionBootcampEntity> existingVersions = new ArrayList<>();
        existingVersions.add(existingVersionBootcampEntity);

        when(versionBootcampRepository.findByBootcampId(1L)).thenReturn(existingVersions);

        DateVersionBootcampAlreadyUseException exception = assertThrows(DateVersionBootcampAlreadyUseException.class, () -> versionBootcampAdapter.saveVersionBootcamp(versionBootcamp));
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Save Version Bootcamp - Bootcamp Not Found: Should throw exception when bootcamp not found")
    void testSaveVersionBootcamp_BootcampNotFound() {
        VersionBootcamp versionBootcamp = new VersionBootcamp(1L, 1L, 50, LocalDate.of(2024, 4, 7), LocalDate.of(2024, 4, 14));

        when(versionBootcampRepository.findByBootcampId(1L)).thenReturn(new ArrayList<>());
        when(bootcampRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> versionBootcampAdapter.saveVersionBootcamp(versionBootcamp));
    }

    @Test
    @DisplayName("Get All Version Bootcamps - Order By Start Date without Bootcamp Name: Should get all version bootcamps ordered by start date")
    void testGetAllVersionBootcamps_OrderByStartDateWithoutBootcampName() {
        PaginationCriteria criteria = new PaginationCriteria(0, 10, SortDirection.ASC, "startDate");

        LocalDate startDate = LocalDate.of(2024, 4, 7);
        LocalDate endDate = LocalDate.of(2024, 4, 14);

        List<VersionBootcampEntity> mockEntities = Arrays.asList(
                new VersionBootcampEntity(1L, 500, startDate, endDate, new BootcampEntity()),
                new VersionBootcampEntity(2L, 500, startDate, endDate, new BootcampEntity())
        );
        Page<VersionBootcampEntity> mockPage = new PageImpl<>(mockEntities);

        when(versionBootcampRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        List<VersionBootcamp> mockBootcamps = Arrays.asList(
                new VersionBootcamp(1L, 1L, 500, startDate, endDate),
                new VersionBootcamp(2L, 1L, 500, startDate, endDate)
        );
        when(versionBootcampEntityMapper.toModel(any(VersionBootcampEntity.class))).thenAnswer(invocation -> {
            VersionBootcampEntity entity = invocation.getArgument(0);
            return new VersionBootcamp(entity.getId(), entity.getBootcamp().getId(), entity.getMaximumQuota(), entity.getStartDate(), entity.getEndDate());
        });

        CustomPage<VersionBootcamp> result = versionBootcampAdapter.getAllVersionBootcamps(criteria, null);

        verify(versionBootcampRepository, times(1)).findAll(any(Pageable.class));
        verify(versionBootcampEntityMapper, times(2)).toModel(any(VersionBootcampEntity.class));

        assertEquals(mockEntities.size(), result.getContent().size());
        assertEquals(mockEntities.get(0).getStartDate(), result.getContent().get(0).getStartDate());
        assertEquals(mockEntities.get(1).getStartDate(), result.getContent().get(1).getStartDate());
    }

    @Test
    @DisplayName("Get All Version Bootcamps - Order By Maximum Quota with Bootcamp Name: Should get all version bootcamps ordered by maximum quota and bootcamp name")
    void testGetAllVersionBootcamps_OrderByMaximumQuotaWithBootcampName() {
        PaginationCriteria criteria = new PaginationCriteria(0, 10, SortDirection.DESC, "maximumQuota");

        LocalDate startDate = LocalDate.of(2024, 4, 7);
        LocalDate endDate = LocalDate.of(2024, 4, 14);

        BootcampEntity mockBootcampEntity = new BootcampEntity();
        mockBootcampEntity.setId(1L);
        mockBootcampEntity.setName("Bootcamp 1");

        List<VersionBootcampEntity> mockEntities = Arrays.asList(
                new VersionBootcampEntity(1L, 500, startDate, endDate, mockBootcampEntity),
                new VersionBootcampEntity(2L, 600, startDate, endDate, mockBootcampEntity)
        );
        Page<VersionBootcampEntity> mockPage = new PageImpl<>(mockEntities);

        when(bootcampRepository.findByName(anyString())).thenReturn(Optional.of(mockBootcampEntity));
        when(versionBootcampRepository.findByBootcamp(any(BootcampEntity.class), any(Pageable.class))).thenReturn(mockPage);

        List<VersionBootcamp> mockBootcamps = Arrays.asList(
                new VersionBootcamp(1L, 1L, 500, startDate, endDate),
                new VersionBootcamp(2L, 1L, 600, startDate, endDate)
        );
        when(versionBootcampEntityMapper.toModel(any(VersionBootcampEntity.class))).thenAnswer(invocation -> {
            VersionBootcampEntity entity = invocation.getArgument(0);
            return new VersionBootcamp(entity.getId(), entity.getBootcamp().getId(), entity.getMaximumQuota(), entity.getStartDate(), entity.getEndDate());
        });

        CustomPage<VersionBootcamp> result = versionBootcampAdapter.getAllVersionBootcamps(criteria, "Bootcamp 1");

        verify(bootcampRepository, times(1)).findByName(anyString());
        verify(versionBootcampRepository, times(1)).findByBootcamp(any(BootcampEntity.class), any(Pageable.class));
        verify(versionBootcampEntityMapper, times(2)).toModel(any(VersionBootcampEntity.class));

        assertEquals(mockEntities.size(), result.getContent().size());
        assertEquals(mockEntities.get(0).getMaximumQuota(), result.getContent().get(0).getMaximumQuota());
        assertEquals(mockEntities.get(1).getMaximumQuota(), result.getContent().get(1).getMaximumQuota());
    }


    @Test
    @DisplayName("Get All Version Bootcamps - Order By Maximum Quota Ascending without Bootcamp Name: Should get all version bootcamps ordered by maximum quota ascending")
    void testGetAllVersionBootcamps_OrderByMaximumQuotaAscWithoutBootcampName() {
        PaginationCriteria criteria = new PaginationCriteria(0, 10, SortDirection.ASC, "maximumQuota");

        LocalDate startDate = LocalDate.of(2024, 4, 7);
        LocalDate endDate = LocalDate.of(2024, 4, 14);

        List<VersionBootcampEntity> mockEntities = Arrays.asList(
                new VersionBootcampEntity(1L, 500, startDate, endDate, new BootcampEntity()),
                new VersionBootcampEntity(2L, 600, startDate, endDate, new BootcampEntity())
        );
        Page<VersionBootcampEntity> mockPage = new PageImpl<>(mockEntities);

        when(versionBootcampRepository.findByMaximumQuotaAsc(any(Pageable.class))).thenReturn(mockPage);

        List<VersionBootcamp> mockBootcamps = Arrays.asList(
                new VersionBootcamp(1L, 1L, 500, startDate, endDate),
                new VersionBootcamp(2L, 1L, 600, startDate, endDate)
        );
        when(versionBootcampEntityMapper.toModel(any(VersionBootcampEntity.class))).thenAnswer(invocation -> {
            VersionBootcampEntity entity = invocation.getArgument(0);
            return new VersionBootcamp(entity.getId(), entity.getBootcamp().getId(), entity.getMaximumQuota(), entity.getStartDate(), entity.getEndDate());
        });

        CustomPage<VersionBootcamp> result = versionBootcampAdapter.getAllVersionBootcamps(criteria, null);

        verify(versionBootcampRepository, times(1)).findByMaximumQuotaAsc(any(Pageable.class));
        verify(versionBootcampEntityMapper, times(2)).toModel(any(VersionBootcampEntity.class));

        assertEquals(mockEntities.size(), result.getContent().size());
        assertEquals(mockEntities.get(0).getMaximumQuota(), result.getContent().get(0).getMaximumQuota());
        assertEquals(mockEntities.get(1).getMaximumQuota(), result.getContent().get(1).getMaximumQuota());
    }


    @Test
    @DisplayName("Get All Version Bootcamps - Order By Maximum Quota Descending without Bootcamp Name: Should get all version bootcamps ordered by maximum quota descending")
    void testGetAllVersionBootcamps_OrderByMaximumQuotaDescWithoutBootcampName() {
        PaginationCriteria criteria = new PaginationCriteria(0, 10, SortDirection.DESC, "maximumQuota");

        LocalDate startDate = LocalDate.of(2024, 4, 7);
        LocalDate endDate = LocalDate.of(2024, 4, 14);

        List<VersionBootcampEntity> mockEntities = Arrays.asList(
                new VersionBootcampEntity(1L, 600, startDate, endDate, new BootcampEntity()),
                new VersionBootcampEntity(2L, 500, startDate, endDate, new BootcampEntity())
        );
        Page<VersionBootcampEntity> mockPage = new PageImpl<>(mockEntities);

        when(versionBootcampRepository.findByMaximumQuotaDesc(any(Pageable.class))).thenReturn(mockPage);

        List<VersionBootcamp> mockBootcamps = Arrays.asList(
                new VersionBootcamp(1L, 1L, 600, startDate, endDate),
                new VersionBootcamp(2L, 1L, 500, startDate, endDate)
        );
        when(versionBootcampEntityMapper.toModel(any(VersionBootcampEntity.class))).thenAnswer(invocation -> {
            VersionBootcampEntity entity = invocation.getArgument(0);
            return new VersionBootcamp(entity.getId(), entity.getBootcamp().getId(), entity.getMaximumQuota(), entity.getStartDate(), entity.getEndDate());
        });

        CustomPage<VersionBootcamp> result = versionBootcampAdapter.getAllVersionBootcamps(criteria, null);

        verify(versionBootcampRepository, times(1)).findByMaximumQuotaDesc(any(Pageable.class));
        verify(versionBootcampEntityMapper, times(2)).toModel(any(VersionBootcampEntity.class));

        assertEquals(mockEntities.size(), result.getContent().size());
        assertEquals(mockEntities.get(0).getMaximumQuota(), result.getContent().get(0).getMaximumQuota());
        assertEquals(mockEntities.get(1).getMaximumQuota(), result.getContent().get(1).getMaximumQuota());
    }


    @Test
    @DisplayName("Is Null Or Empty - Should return true for null")
    void testIsNullOrEmpty_Null() {
        assertTrue(versionBootcampAdapter.isNullOrEmpty(null));
    }

    @Test
    @DisplayName("Is Null Or Empty - Should return true for empty string")
    void testIsNullOrEmpty_Empty() {
        assertTrue(versionBootcampAdapter.isNullOrEmpty(""));
    }

    @Test
    @DisplayName("Is Null Or Empty - Should return false for non-empty string")
    void testIsNullOrEmpty_NonEmpty() {
        assertFalse(versionBootcampAdapter.isNullOrEmpty("notEmpty"));
    }

    @Test
    @DisplayName("Get Sort For Order By - Should return Sort by startDate ascending")
    void testGetSortForOrderBy_StartDateAsc() {
        Sort sort = versionBootcampAdapter.getSortForOrderBy("startDate", Sort.Direction.ASC);
        assertEquals(Sort.by(Sort.Direction.ASC, "startDate"), sort);
    }

    @Test
    @DisplayName("Get Sort For Order By - Should return Sort by maximumQuota descending")
    void testGetSortForOrderBy_MaximumQuotaDesc() {
        Sort sort = versionBootcampAdapter.getSortForOrderBy("maximumQuota", Sort.Direction.DESC);
        assertEquals(Sort.by(Sort.Direction.DESC, "maximumQuota"), sort);
    }

    @Test
    @DisplayName("Get Sort For Order By - Should return default Sort by startDate ascending for unknown orderBy")
    void testGetSortForOrderBy_UnknownOrderBy() {
        Sort sort = versionBootcampAdapter.getSortForOrderBy("unknown", Sort.Direction.ASC);
        assertEquals(Sort.by(Sort.Direction.ASC, "startDate"), sort);
    }
}
