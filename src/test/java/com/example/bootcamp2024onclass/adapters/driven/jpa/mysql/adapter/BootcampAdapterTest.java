package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.adapter;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.BootcampAlreadyExistsException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper.IBootcampEntityMapper;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.IBootcampRepository;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.ICapacityRepository;
import com.example.bootcamp2024onclass.domain.model.*;
import com.example.bootcamp2024onclass.domain.util.SortDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

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
    @DisplayName("Get All Bootcamps - Sorted By Name: Should return all bootcamps sorted by name")
    void testGetAllBootcampsSortedByName() {
        PaginationCriteria criteria = new PaginationCriteria(0, 10, SortDirection.ASC, "name");

        Set<TechnologyEntity> technologies = Set.of(
                new TechnologyEntity(1L, "Java", "Java for beginners"),
                new TechnologyEntity(2L, "Python", "Python for beginners"),
                new TechnologyEntity(3L, "JavaScript", "JavaScript basics")
        );
        Set<CapacityEntity> capacities = Set.of(
                new CapacityEntity(1L, "Capacity A", "Description A", technologies)
        );

        List<BootcampEntity> mockEntities = Arrays.asList(
                new BootcampEntity(1L, "Bootcamp A", "Description A", capacities),
                new BootcampEntity(2L, "Bootcamp B", "Description B", capacities)
        );
        Page<BootcampEntity> mockPage = new PageImpl<>(mockEntities);

        Sort.Direction direction = Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by(direction, "name"));
        when(bootcampRepository.findAll(pageable)).thenReturn(mockPage);

        List<Bootcamp> mockBootcamps = Arrays.asList(
                new Bootcamp(1L, "Bootcamp A", "Description A", Arrays.asList(new Capacity(1L), new Capacity(2L), new Capacity(3L))),
                new Bootcamp(2L, "Bootcamp B", "Description B", Arrays.asList(new Capacity(1L), new Capacity(2L), new Capacity(3L)))
        );
        when(bootcampEntityMapper.toModelList(mockPage.getContent())).thenReturn(mockBootcamps);

        CustomPage<Bootcamp> result = bootcampAdapter.getAllBootcamps(criteria);

        verify(bootcampRepository, times(1)).findAll(pageable);
        verify(bootcampEntityMapper, times(1)).toModelList(mockPage.getContent());

        assertEquals(mockPage.getNumber(), result.getPageNumber());
        assertEquals(mockPage.getTotalElements(), result.getTotalElements());
        assertEquals(mockPage.getTotalPages(), result.getTotalPages());
        assertEquals(criteria.getSize(), result.getPageSize());
        assertEquals(mockBootcamps, result.getContent());
    }


    @Test
    @DisplayName("Get All Bootcamps - Sorted By Capacities Size ASC: Should return all bootcamps sorted by capacities size ASC")
    void testGetAllBootcampsSortedByCapacitiesSizeASC() {
        PaginationCriteria criteria = new PaginationCriteria(0, 10, SortDirection.ASC, "capacities.size");

        Set<TechnologyEntity> technologies = Set.of(
                new TechnologyEntity(1L, "Java", "Java for beginners"),
                new TechnologyEntity(2L, "Python", "Python for beginners"),
                new TechnologyEntity(3L, "JavaScript", "JavaScript basics")
        );
        Set<CapacityEntity> capacities = Set.of(
                new CapacityEntity(1L, "Capacity A", "Description A", technologies)
        );

        List<BootcampEntity> mockEntities = Arrays.asList(
                new BootcampEntity(1L, "Bootcamp A", "Description A", capacities),
                new BootcampEntity(2L, "Bootcamp B", "Description B", capacities)
        );
        Page<BootcampEntity> mockPage = new PageImpl<>(mockEntities);

        Sort.Direction direction = Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by(direction, "capacities.size"));
        when(bootcampRepository.findAllOrderedByCapacitiesCountAsc(pageable)).thenReturn(mockPage);

        List<Bootcamp> mockBootcamps = Arrays.asList(
                new Bootcamp(1L, "Bootcamp A", "Description A", Arrays.asList(new Capacity(1L), new Capacity(2L), new Capacity(3L))),
                new Bootcamp(2L, "Bootcamp B", "Description B", Arrays.asList(new Capacity(1L), new Capacity(2L), new Capacity(3L)))
        );
        when(bootcampEntityMapper.toModelList(mockPage.getContent())).thenReturn(mockBootcamps);

        CustomPage<Bootcamp> result = bootcampAdapter.getAllBootcamps(criteria);

        verify(bootcampRepository, times(1)).findAllOrderedByCapacitiesCountAsc(pageable);
        verify(bootcampEntityMapper, times(1)).toModelList(mockPage.getContent());

        assertEquals(mockPage.getNumber(), result.getPageNumber());
        assertEquals(mockPage.getTotalElements(), result.getTotalElements());
        assertEquals(mockPage.getTotalPages(), result.getTotalPages());
        assertEquals(criteria.getSize(), result.getPageSize());
        assertEquals(mockBootcamps, result.getContent());
    }


    @Test
    @DisplayName("Get All Bootcamps - Sorted By Capacities Size DESC: Should return all bootcamps sorted by capacities size DESC")
    void testGetAllBootcampsSortedByCapacitiesSizeDESC() {
        PaginationCriteria criteria = new PaginationCriteria(0, 10, SortDirection.DESC, "capacities.size");

        Set<TechnologyEntity> technologies = Set.of(
                new TechnologyEntity(1L, "Java", "Java for beginners"),
                new TechnologyEntity(2L, "Python", "Python for beginners"),
                new TechnologyEntity(3L, "JavaScript", "JavaScript basics")
        );
        Set<CapacityEntity> capacities = Set.of(
                new CapacityEntity(1L, "Capacity A", "Description A", technologies)
        );

        List<BootcampEntity> mockEntities = Arrays.asList(
                new BootcampEntity(1L, "Bootcamp A", "Description A", capacities),
                new BootcampEntity(2L, "Bootcamp B", "Description B", capacities)
        );
        Page<BootcampEntity> mockPage = new PageImpl<>(mockEntities);

        Sort.Direction direction = Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by(direction, "capacities.size"));
        when(bootcampRepository.findAllOrderedByCapacitiesCountDesc(pageable)).thenReturn(mockPage);

        List<Bootcamp> mockBootcamps = Arrays.asList(
                new Bootcamp(1L, "Bootcamp A", "Description A", Arrays.asList(new Capacity(1L), new Capacity(2L), new Capacity(3L))),
                new Bootcamp(2L, "Bootcamp B", "Description B", Arrays.asList(new Capacity(1L), new Capacity(2L), new Capacity(3L)))
        );
        when(bootcampEntityMapper.toModelList(mockPage.getContent())).thenReturn(mockBootcamps);

        CustomPage<Bootcamp> result = bootcampAdapter.getAllBootcamps(criteria);

        verify(bootcampRepository, times(1)).findAllOrderedByCapacitiesCountDesc(pageable);
        verify(bootcampEntityMapper, times(1)).toModelList(mockPage.getContent());

        assertEquals(mockPage.getNumber(), result.getPageNumber());
        assertEquals(mockPage.getTotalElements(), result.getTotalElements());
        assertEquals(mockPage.getTotalPages(), result.getTotalPages());
        assertEquals(criteria.getSize(), result.getPageSize());
        assertEquals(mockBootcamps, result.getContent());
    }

}
