package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VersionBootcampEntityTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    @DisplayName("When_VersionBootcampEntityIsValid_Expect_NoViolations")
    void testVersionBootcampEntityValidEntity() {
        Long id = 4L;
        Integer maximumquota = 50;
        LocalDate startDate = LocalDate.of(2025, 1, 30);
        LocalDate endDate = LocalDate.of(2025, 12, 30);
        BootcampEntity bootcampEntity = new BootcampEntity();

        VersionBootcampEntity entity = new VersionBootcampEntity(id, maximumquota, startDate, endDate, bootcampEntity);

        var violations = validator.validate(entity);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("When_SettersMethodsIsValid_Expect_NoViolations")
    void testEntityValidation() {
        Integer maximumQuota = 50;
        LocalDate startDate = LocalDate.of(2025, 1, 30);
        LocalDate endDate = LocalDate.of(2025, 12, 30);
        BootcampEntity bootcamp = new BootcampEntity();

        VersionBootcampEntity entity = new VersionBootcampEntity();
        entity.setMaximumQuota(maximumQuota);
        entity.setStartDate(startDate);
        entity.setEndDate(endDate);
        entity.setBootcamp(bootcamp);

        Set<ConstraintViolation<VersionBootcampEntity>> violations = validator.validate(entity);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Getter Methods Should Return Correct Values")
    void testGetterMethods() {
        Long id = 4L;
        Integer maximumquota = 50;
        LocalDate startDate = LocalDate.of(2025, 1, 30);
        LocalDate endDate = LocalDate.of(2025, 12, 30);
        BootcampEntity bootcampEntity = new BootcampEntity();

        VersionBootcampEntity entity = new VersionBootcampEntity(id, maximumquota, startDate, endDate, bootcampEntity);


        assertEquals(id, entity.getId());
        assertEquals(maximumquota, entity.getMaximumQuota());
        assertEquals(startDate, entity.getStartDate());
        assertEquals(endDate, entity.getEndDate());
        assertEquals(bootcampEntity, entity.getBootcamp());
    }
}
