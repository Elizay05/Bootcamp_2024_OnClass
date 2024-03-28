package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.adapter;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.CapacityAlreadyExistsException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper.ICapacityEntityMapper;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.ICapacityRepository;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.example.bootcamp2024onclass.domain.model.Capacity;
import com.example.bootcamp2024onclass.domain.model.Technology;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CapacityAdapterTest {
    @Mock
    private ICapacityRepository capacityRepository;

    @Mock
    private ICapacityEntityMapper capacityEntityMapper;

    @Mock
    private ITechnologyRepository technologyRepository;

    private CapacityAdapter capacityAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        capacityAdapter = new CapacityAdapter(capacityRepository, capacityEntityMapper, technologyRepository);
    }

    @Test
    @DisplayName("Save Capacity - Success: Should save a new capacity")
    void testSaveCapacity_Success() {
        List<Technology> technologies = new ArrayList<>();
        technologies.add(new Technology(1L, "Java", "Java para niños"));
        technologies.add(new Technology(2L, "Python", "Python for beginners"));
        technologies.add(new Technology(3L, "JavaScript", "JavaScript basics"));

        Capacity capacity = new Capacity(1L, "Capacity", "Description", technologies);
        CapacityEntity capacityEntity = new CapacityEntity();
        when(capacityRepository.findByName("Capacity")).thenReturn(Optional.empty());
        when(capacityEntityMapper.toEntity(capacity)).thenReturn(capacityEntity);
        when(technologyRepository.findById(anyLong())).thenReturn(Optional.of(new TechnologyEntity()));

        capacityAdapter.saveCapacity(capacity);

        verify(capacityRepository, times(1)).findByName("Capacity");
        verify(capacityEntityMapper, times(1)).toEntity(capacity);
        verify(capacityRepository, times(1)).save(capacityEntity);
    }

    @Test
    @DisplayName("Save Capacity - Already Exists: Should throw exception when capacity already exists")
    void testSaveCapacity_AlreadyExists() {
        Technology technology1 = new Technology(1L, "Java", "Java for beginners");
        Technology technology2 = new Technology(2L, "Python", "Python for beginners");
        Technology technology3 = new Technology(3L, "JavaScript", "JavaScript basics");

        List<Technology> technologies = Arrays.asList(technology1, technology2, technology3);

        Capacity capacity = new Capacity(2L, "Capacity", "Description", technologies);

        when(capacityRepository.findByName("Capacity")).thenReturn(Optional.of(new CapacityEntity()));

        assertThrows(CapacityAlreadyExistsException.class, () -> {
            capacityAdapter.saveCapacity(capacity);
        });

        verify(capacityRepository, times(1)).findByName("Capacity");
        verify(capacityRepository, never()).save(any());
    }
    

    /*
    @Test
    void testSaveCapacity_TechnologyNotFound() {
        Technology technology1 = new Technology(1L, "Java", "Java for beginners");
        Technology technology2 = new Technology(2L, "Python", "Python for beginners");
        Technology technology3 = new Technology(3L, "JavaScript", "JavaScript basics");

        List<Technology> technologies = Arrays.asList(technology1, technology2, technology3);

        Capacity capacity = new Capacity(3L, "Capacity", "Description", technologies);

        // Configuración del mock para que devuelva un Optional vacío al llamar findById con el id 1L
        when(technologyRepository.findById(1L)).thenReturn(Optional.empty());

        // Verificar que al intentar guardar la capacidad con una tecnología no encontrada, se lance la excepción ElementNotFoundException
        ElementNotFoundException exception = assertThrows(ElementNotFoundException.class, () -> {
            capacityAdapter.saveCapacity(capacity);
        });

        // Verificar que se llamó al método findById del technologyRepository con el id 1L
        verify(technologyRepository, times(1)).findById(1L);

        // Verificar que no se llamó al método save del capacityRepository
        verify(capacityRepository, never()).save(any());

        // Opcionalmente, también podrías verificar el mensaje de la excepción si proporciona más información útil
        //assertEquals("The element indicated does not exis", exception.getMessage());

    }*/

    @Test
    @DisplayName("Test getAllCapacities: Order by name ascending")
    void testGetAllCapacities_OrderByNameAscending() {
        List<CapacityEntity> mockedCapacityEntities = new ArrayList<>();

        when(capacityRepository.findAll()).thenReturn(mockedCapacityEntities);

        List<Capacity> result = capacityAdapter.getAllCapacities(0, 10, true, true);

        List<Capacity> expected = mockedCapacityEntities.stream()
                .map(capacityEntityMapper::toModel)
                .sorted(Comparator.comparing(Capacity::getName))
                .collect(Collectors.toList());

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Test getAllCapacities: Order by name descending")
    void testGetAllCapacities_OrderByNameDescending() {
        List<CapacityEntity> mockedCapacityEntities = new ArrayList<>();

        when(capacityRepository.findAll()).thenReturn(mockedCapacityEntities);

        List<Capacity> result = capacityAdapter.getAllCapacities(0, 10, true, false);

        List<Capacity> expected = mockedCapacityEntities.stream()
                .map(capacityEntityMapper::toModel)
                .sorted(Comparator.comparing(Capacity::getName).reversed())
                .collect(Collectors.toList());

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Test getAllCapacities: Order by technologies size ascending")
    void testGetAllCapacities_OrderByTechnologiesSizeAscending() {
        List<CapacityEntity> mockedCapacityEntities = new ArrayList<>();

        when(capacityRepository.findAll()).thenReturn(mockedCapacityEntities);

        List<Capacity> result = capacityAdapter.getAllCapacities(0, 10, false, true);

        List<Capacity> expected = mockedCapacityEntities.stream()
                .map(capacityEntityMapper::toModel)
                .sorted(Comparator.comparingInt(capacity -> capacity.getTechnologies().size()))
                .collect(Collectors.toList());

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Test getAllCapacities: Order by technologies size descending")
    void testGetAllCapacities_OrderByTechnologiesSizeDescending() {
        List<CapacityEntity> mockedCapacityEntities = new ArrayList<>();

        when(capacityRepository.findAll()).thenReturn(mockedCapacityEntities);

        List<Capacity> result = capacityAdapter.getAllCapacities(0, 10, false, false);

        List<Capacity> expected = mockedCapacityEntities.stream()
                .map(capacityEntityMapper::toModel)
                .sorted((c1, c2) -> Integer.compare(c2.getTechnologies().size(), c1.getTechnologies().size()))
                .collect(Collectors.toList());

        assertEquals(expected, result);
    }

}