package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.VersionBootcampEntity;
import com.example.bootcamp2024onclass.domain.model.VersionBootcamp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IVersionBootcampEntityMapperImplTest {
    private final IVersionBootcampEntityMapperImpl mapper = new IVersionBootcampEntityMapperImpl();

    @Test
    @DisplayName("When_VersionBootcampEntityConvertedToVersionBootcampModel_Expect_SuccessfulConversion")
    void testToModel() {
        VersionBootcampEntity versionBootcampEntity = new VersionBootcampEntity();
        versionBootcampEntity.setId(1L);
        versionBootcampEntity.setMaximumQuota(50);
        versionBootcampEntity.setStartDate(LocalDate.of(2024, 4, 7));
        versionBootcampEntity.setEndDate(LocalDate.of(2024, 4, 14));

        BootcampEntity bootcampEntity = new BootcampEntity();
        bootcampEntity.setId(100L);
        bootcampEntity.setName("Test Bootcamp");
        versionBootcampEntity.setBootcamp(bootcampEntity);

        VersionBootcamp versionBootcamp = mapper.toModel(versionBootcampEntity);

        assertNotNull(versionBootcamp);
        assertEquals(versionBootcampEntity.getId(), versionBootcamp.getId());
        assertEquals(versionBootcampEntity.getMaximumQuota(), versionBootcamp.getMaximumQuota());
        assertEquals(versionBootcampEntity.getStartDate(), versionBootcamp.getStartDate());
        assertEquals(versionBootcampEntity.getEndDate(), versionBootcamp.getEndDate());
        assertEquals(bootcampEntity.getId(), versionBootcamp.getBootcampId());
        assertEquals(bootcampEntity.getName(), versionBootcamp.getBootcampName());
    }

    @Test
    @DisplayName("When_VersionBootcampEntity_WithNullArguments_ConvertedToVersionBootcampModel_Expect_FailedConversion")
    void testToModelWithNullEntity() {
        VersionBootcamp versionBootcamp = mapper.toModel(null);

        assertNull(versionBootcamp);
    }

    @Test
    @DisplayName("When_VersionBootcampConvertedToVersionBootcampEntity_Expect_SuccessfulConversion")
    void testToEntity() {
        VersionBootcamp versionBootcamp = new VersionBootcamp(1L, 100L, 50, LocalDate.of(2024, 4, 7), LocalDate.of(2024, 4, 14));
        versionBootcamp.setBootcampName("Test Bootcamp");

        VersionBootcampEntity versionBootcampEntity = mapper.toEntity(versionBootcamp);

        assertNotNull(versionBootcampEntity);
        assertEquals(versionBootcamp.getId(), versionBootcampEntity.getId());
        assertEquals(versionBootcamp.getMaximumQuota(), versionBootcampEntity.getMaximumQuota());
        assertEquals(versionBootcamp.getStartDate(), versionBootcampEntity.getStartDate());
        assertEquals(versionBootcamp.getEndDate(), versionBootcampEntity.getEndDate());
    }

    @Test
    @DisplayName("When_VersionBootcamp_WithNullArguments_ConvertedToVersionBootcampEntity_Expect_FailedConversion")
    void testToEntityWithNullModel() {
        VersionBootcampEntity versionBootcampEntity = mapper.toEntity(null);

        assertNull(versionBootcampEntity);
    }

    @Test
    @DisplayName("When_VersionBootcampEntity_WithBootcampEntityNull_ConvertedToVersionBootcamp_Expect_FailedConversion")
    void testToModelWithNullBootcampEntity() {
        VersionBootcampEntity versionBootcampEntity = new VersionBootcampEntity();
        versionBootcampEntity.setId(1L);
        versionBootcampEntity.setMaximumQuota(50);
        versionBootcampEntity.setStartDate(LocalDate.of(2024, 4, 7));
        versionBootcampEntity.setEndDate(LocalDate.of(2024, 4, 14));

        versionBootcampEntity.setBootcamp(null);

        VersionBootcamp versionBootcamp = mapper.toModel(versionBootcampEntity);

        assertNull(versionBootcamp.getBootcampId());
        assertNull(versionBootcamp.getBootcampName());
    }

    @Test
    @DisplayName("When_VersionBootcampEntityListIsNotEmpty_Expect_ListOfVersionBootcampsReturned")
    void testToModelList() {
        List<VersionBootcampEntity> versionBootcampEntities = Arrays.asList(
                new VersionBootcampEntity(1L, 20, LocalDate.of(2024, 4, 7), LocalDate.of(2024, 4, 14), new BootcampEntity()),
                new VersionBootcampEntity(2L, 30, LocalDate.of(2024, 4, 7), LocalDate.of(2024, 4, 14), new BootcampEntity())
        );

        List<VersionBootcamp> result = mapper.toModelList(versionBootcampEntities);

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("When_VersionBootcampEntityListIsNull_Expect_Failed")
    void testToModelListWithNullList() {
        List<VersionBootcamp> result = mapper.toModelList(null);
        assertNull(result);
    }
}


