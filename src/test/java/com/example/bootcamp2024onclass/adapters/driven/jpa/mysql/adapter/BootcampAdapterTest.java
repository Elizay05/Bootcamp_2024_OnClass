package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.adapter;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.BootcampAlreadyExistsException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper.IBootcampEntityMapper;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.IBootcampRepository;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.ICapacityRepository;
import com.example.bootcamp2024onclass.domain.model.Bootcamp;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class BootcampAdapterTest {

    @Mock
    private IBootcampRepository bootcampRepository;

    @Mock
    private IBootcampEntityMapper bootcampEntityMapper;

    @Mock
    private ICapacityRepository capacityRepository;

    private BootcampAdapter bootcampAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bootcampAdapter = new BootcampAdapter(bootcampRepository, bootcampEntityMapper, capacityRepository);
    }

    @Test
    @DisplayName("Save Bootcamp - Success: Should save a new bootcamp")
    void testSaveBootcamp_Success() {
        List<Technology> technologies = Arrays.asList(
                new Technology(1L),
                new Technology(2L),
                new Technology(3L)
        );
        List<Capacity> capacities = new ArrayList<>();
        capacities.add(new Capacity(1L, "Java", "Java para niños", technologies));
        capacities.add(new Capacity(2L, "Python", "Python for beginners", technologies));
        capacities.add(new Capacity(3L, "JavaScript", "JavaScript basics", technologies));

        Bootcamp bootcamp = new Bootcamp(1L, "Bootcamp", "Description", capacities);
        BootcampEntity bootcampEntity = new BootcampEntity();
        when(bootcampRepository.findByName("Bootcamp")).thenReturn(Optional.empty());
        when(bootcampEntityMapper.toEntity(bootcamp)).thenReturn(bootcampEntity);
        when(capacityRepository.findById(anyLong())).thenReturn(Optional.of(new CapacityEntity()));

        bootcampAdapter.saveBootcamp(bootcamp);

        verify(bootcampRepository, times(1)).findByName("Bootcamp");
        verify(bootcampEntityMapper, times(1)).toEntity(bootcamp);
        verify(bootcampRepository, times(1)).save(bootcampEntity);
    }

    @Test
    @DisplayName("Save Bootcamp - Already Exists: Should throw exception when bootcamp already exists")
    void testSaveBootcamp_AlreadyExists() {
        List<Technology> technologies = Arrays.asList(
                new Technology(1L),
                new Technology(2L),
                new Technology(3L)
        );
        Capacity capacity1 = new Capacity(1L, "Java", "Java for beginners", technologies);
        Capacity capacity2 = new Capacity(2L, "Python", "Python for beginners", technologies);
        Capacity capacity3 = new Capacity(3L, "JavaScript", "JavaScript basics", technologies);

        List<Capacity> capacities = Arrays.asList(capacity1, capacity2, capacity3);

        Bootcamp bootcamp = new Bootcamp(2L, "Bootcamp", "Description", capacities);

        when(bootcampRepository.findByName("Bootcamp")).thenReturn(Optional.of(new BootcampEntity()));

        assertThrows(BootcampAlreadyExistsException.class, () -> {
            bootcampAdapter.saveBootcamp(bootcamp);
        });

        verify(bootcampRepository, times(1)).findByName("Bootcamp");
        verify(bootcampRepository, never()).save(any());
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
    @DisplayName("When_GetAllBootcamps_OrderByNameAscending_Expect_SuccessfulResult")
    void shouldOrderBootcampsByNameAscending()  {
        List<BootcampEntity> mockedBootcampEntities = new ArrayList<>();

        when(bootcampRepository.findAll()).thenReturn(mockedBootcampEntities);

        List<Bootcamp> result = bootcampAdapter.getAllBootcamps(0, 10, true, true);

        List<Bootcamp> expected = mockedBootcampEntities.stream()
                .map(bootcampEntityMapper::toModel)
                .sorted(Comparator.comparing(Bootcamp::getName))
                .collect(Collectors.toList());

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("When_GetAllBootcamps_OrderByNameDescending_Expect_SuccessfulResult")
    void shouldOrderBootcampsByNameDescending() {
        List<BootcampEntity> mockedBootcampEntities = new ArrayList<>();

        when(bootcampRepository.findAll()).thenReturn(mockedBootcampEntities);

        List<Bootcamp> result = bootcampAdapter.getAllBootcamps(0, 10, true, false);

        List<Bootcamp> expected = mockedBootcampEntities.stream()
                .map(bootcampEntityMapper::toModel)
                .sorted(Comparator.comparing(Bootcamp::getName).reversed())
                .collect(Collectors.toList());

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("When_GetAllBootcamps_OrderByCapacitiesSizeAscending_Expect_SuccessfulResult")
    void shouldOrderBootcampsByCapacitiesSizeAscending() {
        List<BootcampEntity> mockedBootcampEntities = new ArrayList<>();

        when(bootcampRepository.findAll()).thenReturn(mockedBootcampEntities);

        List<Bootcamp> result = bootcampAdapter.getAllBootcamps(0, 10, false, true);

        List<Bootcamp> expected = mockedBootcampEntities.stream()
                .map(bootcampEntityMapper::toModel)
                .sorted(Comparator.comparingInt(bootcamp -> bootcamp.getCapacities().size()))
                .collect(Collectors.toList());

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("When_GetAllBootcamps_OrderByCapacitiesSizeDescending_Expect_SuccessfulResult")
    void shouldOrderBootcampsByCapacitiesSizeDescending()  {
        List<BootcampEntity> mockedBootcampEntities = new ArrayList<>();

        when(bootcampRepository.findAll()).thenReturn(mockedBootcampEntities);

        List<Bootcamp> result = bootcampAdapter.getAllBootcamps(0, 10, false, false);

        List<Bootcamp> expected = mockedBootcampEntities.stream()
                .map(bootcampEntityMapper::toModel)
                .sorted((c1, c2) -> Integer.compare(c2.getCapacities().size(), c1.getCapacities().size()))
                .collect(Collectors.toList());

        assertEquals(expected, result);
    }
}
