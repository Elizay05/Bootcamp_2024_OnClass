package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.adapter;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.NoDataFoundException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.TechnologyAlreadyExistsException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper.ITechnologyEntityMapper;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.example.bootcamp2024onclass.domain.model.Technology;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    void testGetAllTechnologies_Success() {
        Integer page = 0;
        Integer size = 10;
        boolean isAscending = true;
        List<TechnologyEntity> technologyEntities = new ArrayList<>();
        technologyEntities.add(new TechnologyEntity(1L, "Java", "Programming language"));
        technologyEntities.add(new TechnologyEntity(2L, "Python", "Programming language"));
        Page<TechnologyEntity> pageResult = new PageImpl<>(technologyEntities);

        when(technologyRepository.findAll(any(Pageable.class))).thenReturn(pageResult);
        when(technologyEntityMapper.toModelList(technologyEntities)).thenReturn(Arrays.asList(
                new Technology(1L, "Java", "Programming language"),
                new Technology(2L, "Python", "Programming language")
        ));

        List<Technology> result = technologyAdapter.getAllTechnologies(page, size, isAscending);

        assertEquals(2, result.size());
        assertEquals("Java", result.get(0).getName());
        assertEquals("Python", result.get(1).getName());
        verify(technologyRepository, times(1)).findAll(any(Pageable.class));
        verify(technologyEntityMapper, times(1)).toModelList(technologyEntities);
    }

    @Test
    void testGetAllTechnologies_NoDataFound() {
        Integer page = 0;
        Integer size = 10;
        boolean isAscending = true;
        List<TechnologyEntity> technologyEntities = new ArrayList<>();
        Page<TechnologyEntity> pageResult = new PageImpl<>(technologyEntities);

        when(technologyRepository.findAll(any(Pageable.class))).thenReturn(pageResult);

        assertThrows(NoDataFoundException.class, () -> {
            technologyAdapter.getAllTechnologies(page, size, isAscending);
        });

        verify(technologyRepository, times(1)).findAll(any(Pageable.class));
        verify(technologyEntityMapper, never()).toModelList(any());
    }


}
