package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.example.bootcamp2024onclass.domain.model.Bootcamp;
import com.example.bootcamp2024onclass.domain.model.Capacity;
import com.example.bootcamp2024onclass.domain.model.Technology;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class IBootcampEntityMapperImplTest {

    private final IBootcampEntityMapperImpl mapper = new IBootcampEntityMapperImpl();

    @DisplayName("When_BootcampEntityConvertedToBootcampModel_Expect_SuccessfulConversion")
    void testToModel() {
        Long bootcampId = 1L;
        String bootcampName = "Bootcamp Name";
        String bootcampDescription = "Bootcamp Description";

        BootcampEntity bootcampEntity = new BootcampEntity();
        bootcampEntity.setId(bootcampId);
        bootcampEntity.setName(bootcampName);
        bootcampEntity.setDescription(bootcampDescription);

        Set<TechnologyEntity> technologyEntities = new HashSet<>();
        technologyEntities.add(new TechnologyEntity(1L, "Java", "Programming language"));
        technologyEntities.add(new TechnologyEntity(2L, "Python", "High-level programming language"));
        technologyEntities.add(new TechnologyEntity(3L, "JavaScript", "High-level programming language"));

        Set<CapacityEntity> capacityEntities = new HashSet<>();
        capacityEntities.add(new CapacityEntity(1L, "Name 1", "Description 1", technologyEntities));
        capacityEntities.add(new CapacityEntity(2L, "Name 2", "Description 2", technologyEntities));
        capacityEntities.add(new CapacityEntity(3L, "Name 3", "Description 3", technologyEntities));
        bootcampEntity.setCapacities(capacityEntities);

        Bootcamp bootcamp = mapper.toModel(bootcampEntity);
        assertNotNull(bootcamp);
        assertEquals(bootcampId, bootcamp.getId());
        assertEquals(bootcampName, bootcamp.getName());
        assertEquals(bootcampDescription, bootcamp.getDescription());
        assertEquals(capacityEntities.size(), bootcamp.getCapacities().size());
    }

    @Test
    @DisplayName("When_BootcampConvertedToBootcampEntity_Expect_SuccessfulConversion")
    void testToEntity() {
        List<Technology> technologies = new ArrayList<>();
        technologies.add(new Technology(1L));
        technologies.add(new Technology(2L));
        technologies.add(new Technology(3L));

        List<Capacity> capacities = new ArrayList<>();
        capacities.add(new Capacity(1L, "Java", "Programming language", technologies));
        capacities.add(new Capacity(2L, "Python", "High-level programming language", technologies));
        capacities.add(new Capacity(3L, "JavaScript", "High-level programming language", technologies));

        Bootcamp bootcamp = new Bootcamp(1L, "Capacity Name", "Capacity Description", capacities);

        BootcampEntity bootcampEntity = mapper.toEntity(bootcamp);
        assertNotNull(bootcampEntity);
        assertEquals(bootcamp.getName(), bootcampEntity.getName());
        assertEquals(bootcamp.getDescription(), bootcampEntity.getDescription());
        assertEquals(capacities.size(), bootcampEntity.getCapacities().size());
    }
  
  @Test
    @DisplayName("When_BootcampEntityListIsNotEmpty_Expect_ListOfBootcampsReturned")
    void testToModelList() {
        Set<TechnologyEntity> technologyEntities = new HashSet<>();
        technologyEntities.add(new TechnologyEntity(1L, "Technology Name 1", "Technology Description 1"));
        technologyEntities.add(new TechnologyEntity(2L, "Technology Name 2", "Technology Description 2"));
        technologyEntities.add(new TechnologyEntity(3L, "Technology Name 3", "Technology Description 3"));

        Set<CapacityEntity> capacityEntities = new HashSet<>();
        capacityEntities.add(new CapacityEntity(1L, "Capacity Name 1", "Capacity Description 1", technologyEntities));
        capacityEntities.add(new CapacityEntity(2L, "Capacity Name 2", "Capacity Description 2", technologyEntities));
        capacityEntities.add(new CapacityEntity(3L, "Capacity Name 3", "Capacity Description 3", technologyEntities));

        List<BootcampEntity> bootcampEntities = new ArrayList<>();
        bootcampEntities.add(new BootcampEntity(1L, "Bootcamp Name 1", "Bootcamp Description 1", capacityEntities));
        bootcampEntities.add(new BootcampEntity(2L, "Bootcamp Name 2", "Bootcamp Description 2", capacityEntities));
        bootcampEntities.add(new BootcampEntity(3L, "Bootcamp Name 3", "Bootcamp Description 3", capacityEntities));

        List<Bootcamp> mappedList = mapper.toModelList(bootcampEntities);
        assertNotNull(mappedList);
        assertEquals(bootcampEntities.size(), mappedList.size());
        for (int i = 0; i < bootcampEntities.size(); i++) {
            BootcampEntity bootcampEntity = bootcampEntities.get(i);
            Bootcamp mappedBootcamp = mappedList.get(i);
            assertEquals(bootcampEntity.getId(), mappedBootcamp.getId());
            assertEquals(bootcampEntity.getName(), mappedBootcamp.getName());
            assertEquals(bootcampEntity.getDescription(), mappedBootcamp.getDescription());
        }

}
