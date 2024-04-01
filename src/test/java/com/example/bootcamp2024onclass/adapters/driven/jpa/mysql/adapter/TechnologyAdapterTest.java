package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.adapter;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.TechnologyAlreadyExistsException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper.ITechnologyEntityMapper;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.example.bootcamp2024onclass.domain.model.Technology;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

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

}
