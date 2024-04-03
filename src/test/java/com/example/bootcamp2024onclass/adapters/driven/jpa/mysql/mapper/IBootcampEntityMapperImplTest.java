package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.example.bootcamp2024onclass.domain.model.Bootcamp;
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
}
