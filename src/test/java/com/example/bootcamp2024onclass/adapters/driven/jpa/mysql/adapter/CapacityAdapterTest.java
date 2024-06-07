package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.adapter;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.CapacityAlreadyExistsException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.NoDataFoundException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper.ICapacityEntityMapper;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.ICapacityRepository;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.example.bootcamp2024onclass.domain.model.Capacity;
import com.example.bootcamp2024onclass.domain.model.CustomPage;
import com.example.bootcamp2024onclass.domain.model.PaginationCriteria;
import com.example.bootcamp2024onclass.domain.model.Technology;
import com.example.bootcamp2024onclass.domain.util.SortDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.*;

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

        CapacityEntity capacityEntity = new CapacityEntity();
        Set<TechnologyEntity> technologyEntities = new HashSet<>();
        technologyEntities.add(new TechnologyEntity(1L, "Java", "Java para niños"));
        technologyEntities.add(new TechnologyEntity(2L, "Python", "Python for beginners"));
        technologyEntities.add(new TechnologyEntity(3L, "JavaScript", "JavaScript basics"));
        capacityEntity.setTechnologies(technologyEntities);

        Capacity capacity = new Capacity(1L, "Capacity", "Description", technologies);

        when(capacityEntityMapper.toEntity(capacity)).thenReturn(capacityEntity);
        when(capacityRepository.findByName("Capacity")).thenReturn(Optional.empty());
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
    @DisplayName("Save Capacity - Technology Not Found: Should throw exception when technology is not found")
    void testSaveCapacity_TechnologyNotFound() {
        // Configurar las tecnologías con las IDs correctas
        Technology technology1 = new Technology(1L, "Java", "Java para niños");
        Technology technology2 = new Technology(2L, "Python", "Python for beginners");
        Technology technology3 = new Technology(3L, "JavaScript", "JavaScript basics");

        // Crear una lista de tecnologías
        List<Technology> technologies = Arrays.asList(technology1, technology2, technology3);

        // Crear una instancia de Capacity con las tecnologías
        Capacity capacity = new Capacity(1L, "Capacity", "Description", technologies);

        // Configurar el mock de capacityEntityMapper para devolver la instancia de CapacityEntity configurada
        CapacityEntity capacityEntity = new CapacityEntity();
        Set<TechnologyEntity> technologyEntities = new HashSet<>();
        technologyEntities.add(new TechnologyEntity(1L, "Java", "Java para niños"));
        technologyEntities.add(new TechnologyEntity(2L, "Python", "Python for beginners"));
        technologyEntities.add(new TechnologyEntity(3L, "JavaScript", "JavaScript basics"));
        capacityEntity.setTechnologies(technologyEntities);

        when(capacityEntityMapper.toEntity(capacity)).thenReturn(capacityEntity);
        when(capacityRepository.findByName("Capacity")).thenReturn(Optional.empty());
        when(technologyRepository.findById(1L)).thenReturn(Optional.empty()); // Corregido el ID aquí

        // Verificar que se lance la excepción ElementNotFoundException
        ElementNotFoundException exception = assertThrows(ElementNotFoundException.class, () -> {
            capacityAdapter.saveCapacity(capacity);
        });

        // Verificar que se llamó al método findById del technologyRepository con el id 1L
        verify(technologyRepository, times(1)).findById(1L);

        // Verificar que no se llamó al método save del capacityRepository
        verify(capacityRepository, never()).save(any());

    }*/

    @Test
    @DisplayName("Get All Capacities - Sorted By Name: Should return all capacities sorted by name")
    void testGetAllCapacitiesSortedByName() {
        PaginationCriteria criteria = new PaginationCriteria(0, 10, SortDirection.ASC, "name");

        Set<TechnologyEntity> technologies = Set.of(
                new TechnologyEntity(1L, "Java", "Java for beginners"),
                new TechnologyEntity(2L, "Python", "Python for beginners"),
                new TechnologyEntity(3L, "JavaScript", "JavaScript basics")
        );

        List<CapacityEntity> mockEntities = Arrays.asList(
                new CapacityEntity(1L, "Capacity A", "Description A", technologies),
                new CapacityEntity(2L, "Capacity B", "Description B", technologies)
        );
        Page<CapacityEntity> mockPage = new PageImpl<>(mockEntities);

        Sort.Direction direction = Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by(direction, "name"));
        when(capacityRepository.findAll(pageable)).thenReturn(mockPage);

        List<Capacity> mockCapacities = Arrays.asList(
                new Capacity(1L, "Capacity A", "Description A", Arrays.asList(new Technology(1L), new Technology(2L), new Technology(3L))),
                new Capacity(2L, "Capacity B", "Description B", Arrays.asList(new Technology(1L), new Technology(2L), new Technology(3L)))
        );
        when(capacityEntityMapper.toModelList(mockPage.getContent())).thenReturn(mockCapacities);

        CustomPage<Capacity> result = capacityAdapter.getAllCapacities(criteria);

        verify(capacityRepository, times(1)).findAll(pageable);
        verify(capacityEntityMapper, times(1)).toModelList(mockPage.getContent());

        assertEquals(mockPage.getNumber(), result.getPageNumber());
        assertEquals(mockPage.getTotalElements(), result.getTotalElements());
        assertEquals(mockPage.getTotalPages(), result.getTotalPages());
        assertEquals(criteria.getSize(), result.getPageSize());
        assertEquals(mockCapacities, result.getContent());
    }


    @Test
    @DisplayName("Get All Capacities - Sorted By Technologies Size ASC: Should return all capacities sorted by technologies size ASC")
    void testGetAllCapacitiesSortedByTechnologiesSizeASC() {
        PaginationCriteria criteria = new PaginationCriteria(0, 10, SortDirection.ASC, "technologies.size");

        Set<TechnologyEntity> technologies = Set.of(
                new TechnologyEntity(1L, "Java", "Java for beginners"),
                new TechnologyEntity(2L, "Python", "Python for beginners"),
                new TechnologyEntity(3L, "JavaScript", "JavaScript basics")
        );

        List<CapacityEntity> mockEntities = Arrays.asList(
                new CapacityEntity(1L, "Capacity A", "Description A", technologies),
                new CapacityEntity(2L, "Capacity B", "Description B", technologies)
        );
        Page<CapacityEntity> mockPage = new PageImpl<>(mockEntities);

        Sort.Direction direction = Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by(direction, "technologies.size"));
        when(capacityRepository.findAllOrderedByTechnologiesCountAsc(pageable)).thenReturn(mockPage);

        List<Capacity> mockCapacities = Arrays.asList(
                new Capacity(1L, "Capacity A", "Description A", Arrays.asList(new Technology(1L), new Technology(2L), new Technology(3L))),
                new Capacity(2L, "Capacity B", "Description B", Arrays.asList(new Technology(1L), new Technology(2L), new Technology(3L)))
        );
        when(capacityEntityMapper.toModelList(mockPage.getContent())).thenReturn(mockCapacities);

        CustomPage<Capacity> result = capacityAdapter.getAllCapacities(criteria);

        verify(capacityRepository, times(1)).findAllOrderedByTechnologiesCountAsc(pageable);
        verify(capacityEntityMapper, times(1)).toModelList(mockPage.getContent());

        assertEquals(mockPage.getNumber(), result.getPageNumber());
        assertEquals(mockPage.getTotalElements(), result.getTotalElements());
        assertEquals(mockPage.getTotalPages(), result.getTotalPages());
        assertEquals(criteria.getSize(), result.getPageSize());
        assertEquals(mockCapacities, result.getContent());
    }


    @Test
    @DisplayName("Get All Capacities - Sorted By Technologies Size DESC: Should return all capacities sorted by technologies size DESC")
    void testGetAllCapacitiesSortedByTechnologiesSizeDESC() {
        PaginationCriteria criteria = new PaginationCriteria(0, 10, SortDirection.DESC, "technologies.size");

        Set<TechnologyEntity> technologies = Set.of(
                new TechnologyEntity(1L, "Java", "Java for beginners"),
                new TechnologyEntity(2L, "Python", "Python for beginners"),
                new TechnologyEntity(3L, "JavaScript", "JavaScript basics")
        );

        List<CapacityEntity> mockEntities = Arrays.asList(
                new CapacityEntity(1L, "Capacity A", "Description A", technologies),
                new CapacityEntity(2L, "Capacity B", "Description B", technologies)
        );
        Page<CapacityEntity> mockPage = new PageImpl<>(mockEntities);

        Sort.Direction direction = Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by(direction, "technologies.size"));
        when(capacityRepository.findAllOrderedByTechnologiesCountDesc(pageable)).thenReturn(mockPage);

        List<Capacity> mockCapacities = Arrays.asList(
                new Capacity(1L, "Capacity A", "Description A", Arrays.asList(new Technology(1L), new Technology(2L), new Technology(3L))),
                new Capacity(2L, "Capacity B", "Description B", Arrays.asList(new Technology(1L), new Technology(2L), new Technology(3L)))
        );
        when(capacityEntityMapper.toModelList(mockPage.getContent())).thenReturn(mockCapacities);

        CustomPage<Capacity> result = capacityAdapter.getAllCapacities(criteria);

        verify(capacityRepository, times(1)).findAllOrderedByTechnologiesCountDesc(pageable);
        verify(capacityEntityMapper, times(1)).toModelList(mockPage.getContent());

        assertEquals(mockPage.getNumber(), result.getPageNumber());
        assertEquals(mockPage.getTotalElements(), result.getTotalElements());
        assertEquals(mockPage.getTotalPages(), result.getTotalPages());
        assertEquals(criteria.getSize(), result.getPageSize());
        assertEquals(mockCapacities, result.getContent());
    }


    @Test
    @DisplayName("Get Total Body Capacities - Success: Should retrieve all capacities")
    void testGetTotalBodyCapacities_Success() {
        List<Capacity> mockCapacities = Arrays.asList(
                new Capacity(1L, "Java", "Programming language", Arrays.asList(new Technology(1L), new Technology(2L), new Technology(3L))),
                new Capacity(2L, "Python", "Programming language", Arrays.asList(new Technology(1L), new Technology(2L), new Technology(3L)))
        );

        Set<TechnologyEntity> technologies = Set.of(
                new TechnologyEntity(1L, "Java", "Java for beginners"),
                new TechnologyEntity(2L, "Python", "Python for beginners"),
                new TechnologyEntity(3L, "JavaScript", "JavaScript basics")
        );

        when(capacityRepository.findAll()).thenReturn(Arrays.asList(
                new CapacityEntity(1L, "Java", "Programming language", technologies),
                new CapacityEntity(2L, "Python", "Programming language", technologies)
        ));

        when(capacityEntityMapper.toModelList(anyList())).thenReturn(mockCapacities);

        List<Capacity> result = capacityAdapter.getTotalBodyCapacities();

        assertEquals(mockCapacities, result);

        verify(capacityRepository, times(1)).findAll();
        verify(capacityEntityMapper, times(1)).toModelList(anyList());
    }

    @Test
    @DisplayName("Get Total Body Capacities - No Data Found: Should throw exception when no data is found")
    void testGetTotalBodyCapacitiesEmptyList_NoDataFound() {
        when(capacityRepository.findAll()).thenReturn(Arrays.asList());

        when(capacityEntityMapper.toModelList(anyList())).thenReturn(Arrays.asList());

        assertThrows(NoDataFoundException.class, () -> capacityAdapter.getTotalBodyCapacities());

        verify(capacityRepository, times(1)).findAll();

        verify(capacityEntityMapper, never()).toModelList(anyList());
    }
}