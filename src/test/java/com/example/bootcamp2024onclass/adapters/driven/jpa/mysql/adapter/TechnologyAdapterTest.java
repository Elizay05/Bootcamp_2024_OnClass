package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.adapter;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.NoDataFoundException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.TechnologyAlreadyExistsException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper.ITechnologyEntityMapper;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.example.bootcamp2024onclass.domain.model.CustomPage;
import com.example.bootcamp2024onclass.domain.model.PaginationCriteria;
import com.example.bootcamp2024onclass.domain.model.Technology;
import com.example.bootcamp2024onclass.domain.util.SortDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class TechnologyAdapterTest {
    @Mock
    private ITechnologyRepository technologyRepository;

    @Mock
    private ITechnologyEntityMapper technologyEntityMapper;

    private TechnologyAdapter technologyAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        technologyAdapter = new TechnologyAdapter(technologyEntityMapper, technologyRepository);
    }
    @Test
    @DisplayName("Save Technology - Success: Should save a new technology")
    void testSaveTechnology_Success() {
        Technology technology = new Technology(1L, "Java", "Java para niños");
        TechnologyEntity technologyEntity = new TechnologyEntity();
        when(technologyRepository.findByName("Java")).thenReturn(Optional.empty());
        when(technologyEntityMapper.toEntity(technology)).thenReturn(technologyEntity);

        technologyAdapter.saveTechnology(technology);

        verify(technologyRepository, times(1)).findByName("Java");
        verify(technologyEntityMapper, times(1)).toEntity(technology);
        verify(technologyRepository, times(1)).save(technologyEntity);
    }

    @Test
    @DisplayName("Save Technology - Already Exists: Should throw exception when technology already exists")
    void testSaveTechnology_AlreadyExists() {
        Technology technology = new Technology(2L, "Java", "Java para niños");
        when(technologyRepository.findByName("Java")).thenReturn(Optional.of(new TechnologyEntity()));

        assertThrows(TechnologyAlreadyExistsException.class, () -> {
            technologyAdapter.saveTechnology(technology);
        });

        verify(technologyRepository, times(1)).findByName("Java");
        verify(technologyRepository, never()).save(any());
    }

    @Test
    @DisplayName("Get All Technologies - Success: Should retrieve all technologies")
    void testGetAllTechnologies_Success() {

        PaginationCriteria criteria = new PaginationCriteria(0, 10, SortDirection.ASC, "name");
        List<Technology> mockTechnologies = Arrays.asList(
                new Technology(1L, "Java", "Programming language"),
                new Technology(2L, "Python", "Programming language")
        );
        CustomPage<Technology> mockCustomPage = new CustomPage<>(mockTechnologies, 0, 10, 2, 1);

        when(technologyRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Arrays.asList( new TechnologyEntity())));
        when(technologyEntityMapper.toModelList(anyList())).thenReturn(mockTechnologies);


        CustomPage<Technology> result = technologyAdapter.getAllTechnologies(criteria);

        verify(technologyRepository, times(1)).findAll(any(Pageable.class));
        assertNotNull(result);
    }

    @Test
    @DisplayName("Get All Technologies - No Data Found: Should throw exception when no data is found")
    void testGetAllTechnologies_NoDataFound() {
        PaginationCriteria criteria = new PaginationCriteria(0, 10, SortDirection.ASC, "name");

        when(technologyRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));

        assertThrows(NoDataFoundException.class, () -> {
            technologyAdapter.getAllTechnologies(criteria);
        });

        verify(technologyRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Get Total Body Technologies - Success: Should retrieve all technologies")
    void testGetTotalBodyTechnologies_Success() {
        List<Technology> mockTechnologies = Arrays.asList(
                new Technology(1L, "Java", "Programming language"),
                new Technology(2L, "Python", "Programming language")
        );

        when(technologyRepository.findAll()).thenReturn(Arrays.asList(
                new TechnologyEntity(1L, "Java", "Programming language"),
                new TechnologyEntity(2L, "Python", "Programming language")
        ));

        when(technologyEntityMapper.toModelList(anyList())).thenReturn(mockTechnologies);

        List<Technology> result = technologyAdapter.getTotalBodyTechnologies();

        assertEquals(mockTechnologies, result);

        verify(technologyRepository, times(1)).findAll();
        verify(technologyEntityMapper, times(1)).toModelList(anyList());
    }

    @Test
    @DisplayName("Get Total Body Technologies - No Data Found: Should throw exception when no data is found")
    void testGetTotalBodyTechnologiesEmptyList_NoDataFound() {
        when(technologyRepository.findAll()).thenReturn(Arrays.asList());

        when(technologyEntityMapper.toModelList(anyList())).thenReturn(Arrays.asList());

        assertThrows(NoDataFoundException.class, () -> technologyAdapter.getTotalBodyTechnologies());

        verify(technologyRepository, times(1)).findAll();

        verify(technologyEntityMapper, never()).toModelList(anyList());
    }
}
