package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.example.bootcamp2024onclass.domain.model.Technology;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ITechnologyEntityMapperImplTest {

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
}
