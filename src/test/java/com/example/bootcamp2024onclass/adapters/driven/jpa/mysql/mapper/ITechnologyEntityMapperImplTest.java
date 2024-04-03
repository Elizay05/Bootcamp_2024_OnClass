package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.example.bootcamp2024onclass.domain.model.Technology;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ITechnologyEntityMapperImplTest {

    private final ITechnologyEntityMapperImpl mapper = new ITechnologyEntityMapperImpl();


    @Test
    @DisplayName("When_TechnologyEntityConvertedToTechnologyModel_Expect_SuccessfulConversion")
    void testToModel() {
        TechnologyEntity technologyEntity = new TechnologyEntity(1L, "Java", "Programming language");
        ITechnologyEntityMapperImpl mapper = new ITechnologyEntityMapperImpl();

        Technology technology = mapper.toModel(technologyEntity);

        assertEquals(technologyEntity.getId(), technology.getId());
        assertEquals(technologyEntity.getName(), technology.getName());
        assertEquals(technologyEntity.getDescription(), technology.getDescription());
    }

    @Test
    @DisplayName("When_TechnologyConvertedToTechnologyEntity_Expect_SuccessfulConversion")
    void testToEntity() {
        Technology technology = new Technology(1L, "Java", "Programming language");
        ITechnologyEntityMapperImpl mapper = new ITechnologyEntityMapperImpl();

        TechnologyEntity technologyEntity = mapper.toEntity(technology);

        assertEquals(technology.getId(), technologyEntity.getId());
        assertEquals(technology.getName(), technologyEntity.getName());
        assertEquals(technology.getDescription(), technologyEntity.getDescription());
    }

    @Test
    @DisplayName("When_TechnologyEntityListIsNotEmpty_Expect_ListOfTechnologiesReturned")
    void testToModelList() {
        List<TechnologyEntity> technologyEntities = Arrays.asList(
                new TechnologyEntity(1L, "Java", "Java para niños"),
                new TechnologyEntity(2L, "Python", "Python for beginners"),
                new TechnologyEntity(3L, "JavaScript", "JavaScript basics")
        );

        List<Technology> result = mapper.toModelList(technologyEntities);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Java", result.get(0).getName());
        assertEquals("Python", result.get(1).getName());
        assertEquals("JavaScript", result.get(2).getName());
    }
}
