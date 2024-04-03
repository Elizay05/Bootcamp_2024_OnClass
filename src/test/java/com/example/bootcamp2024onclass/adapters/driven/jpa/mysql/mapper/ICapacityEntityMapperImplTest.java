package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.TechnologyEntity;
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

class ICapacityEntityMapperImplTest {

    private final ICapacityEntityMapperImpl mapper = new ICapacityEntityMapperImpl();

    @Test
    @DisplayName("When_CapacityEntityConvertedToCapacityModel_Expect_SuccessfulConversion")
    void testToModel() {
        Long capacityId = 1L;
        String capacityName = "Capacity Name";
        String capacityDescription = "Capacity Description";

        CapacityEntity capacityEntity = new CapacityEntity();
        capacityEntity.setId(capacityId);
        capacityEntity.setName(capacityName);
        capacityEntity.setDescription(capacityDescription);

        Set<TechnologyEntity> technologyEntities = new HashSet<>();
        technologyEntities.add(new TechnologyEntity(1L, "Java", "Programming language"));
        technologyEntities.add(new TechnologyEntity(2L, "Python", "High-level programming language"));
        technologyEntities.add(new TechnologyEntity(3L, "JavaScript", "High-level programming language"));
        capacityEntity.setTechnologies(technologyEntities);

        Capacity capacity = mapper.toModel(capacityEntity);
        assertNotNull(capacity);
        assertEquals(capacityId, capacity.getId());
        assertEquals(capacityName, capacity.getName());
        assertEquals(capacityDescription, capacity.getDescription());
        assertEquals(technologyEntities.size(), capacity.getTechnologies().size());
    }

    @Test
    @DisplayName("When_CapacityConvertedToCapacityEntity_Expect_SuccessfulConversion")
    void testToEntity() {
        List<Technology> technologies = new ArrayList<>();
        technologies.add(new Technology(1L, "Java", "Programming language"));
        technologies.add(new Technology(2L, "Python", "High-level programming language"));
        technologies.add(new Technology(3L, "JavaScript", "High-level programming language"));
        Capacity capacity = new Capacity(1L, "Capacity Name", "Capacity Description", technologies);

        CapacityEntity capacityEntity = mapper.toEntity(capacity);
        assertNotNull(capacityEntity);
        assertEquals(capacity.getName(), capacityEntity.getName());
        assertEquals(capacity.getDescription(), capacityEntity.getDescription());
        assertEquals(technologies.size(), capacityEntity.getTechnologies().size());
    }

    @Test
    @DisplayName("When_CapacityEntityListIsNotEmpty_Expect_ListOfCapacitiesReturned")
    void testToModelList() {
        Set<TechnologyEntity> technologyEntities = new HashSet<>();
        technologyEntities.add(new TechnologyEntity(1L, "Java", "Programming language"));
        technologyEntities.add(new TechnologyEntity(2L, "Python", "High-level programming language"));
        technologyEntities.add(new TechnologyEntity(3L, "JavaScript", "High-level programming language"));

        List<CapacityEntity> capacityEntities = new ArrayList<>();
        capacityEntities.add(new CapacityEntity(1L, "Capacity Name 1", "Capacity Description 1", technologyEntities));
        capacityEntities.add(new CapacityEntity(2L, "Capacity Name 2", "Capacity Description 2", technologyEntities));
        capacityEntities.add(new CapacityEntity(3L, "Capacity Name 3", "Capacity Description 3", technologyEntities));

        List<Capacity> mappedList = mapper.toModelList(capacityEntities);
        assertNotNull(mappedList);
        assertEquals(capacityEntities.size(), mappedList.size());
        for (int i = 0; i < capacityEntities.size(); i++) {
            CapacityEntity capacityEntity = capacityEntities.get(i);
            Capacity mappedCapacity = mappedList.get(i);
            assertEquals(capacityEntity.getId(), mappedCapacity.getId());
            assertEquals(capacityEntity.getName(), mappedCapacity.getName());
            assertEquals(capacityEntity.getDescription(), mappedCapacity.getDescription());
        }
    }
}
