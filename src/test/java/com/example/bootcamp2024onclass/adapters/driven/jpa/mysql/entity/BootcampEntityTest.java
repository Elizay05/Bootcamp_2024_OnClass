package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BootcampEntityTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    @DisplayName("When_BootcampEntityIsValid_Expect_NoViolations")
    void testBootcampEntityValidEntity() {
        CapacityEntity capacity = new CapacityEntity();

        Set<CapacityEntity> capacities = new HashSet<>();
        capacities.add(capacity);

        BootcampEntity entity = new BootcampEntity(4L, "ValidName", "ValidDescription", capacities);

        var violations = validator.validate(entity);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Fields Should Not Be Blank")
    void testBootcampEntityFieldsNotBlank() {
        BootcampEntity bootcamp = new BootcampEntity();
        bootcamp.setName("");
        bootcamp.setDescription("");
        bootcamp.setCapacities(new HashSet<>());
        Set<ConstraintViolation<BootcampEntity>> violations = validator.validate(bootcamp);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Fields Should Not Exceed Maximum Length")
    void testBootcampEntityFieldsMaxLengthExceeded() {
        BootcampEntity bootcamp = new BootcampEntity();
        bootcamp.setName("ThisNameIsWayTooLongAndExceedsTheMaximumAllowedLengthOfFiftyCharacters");
        bootcamp.setDescription("ThisIsAnExampleTextThatExceedsTheMaximumAllowedLengthOfNinetyCharactersAndIsWayTooLong");
        bootcamp.setCapacities(new HashSet<>());

        Set<ConstraintViolation<BootcampEntity>> violations = validator.validate(bootcamp);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Capacities Should Not Be Null")
    void testBootcampEntityCapacitiesNotNull() {
        BootcampEntity bootcamp = new BootcampEntity();
        bootcamp.setName("Test name");
        bootcamp.setDescription("Test description");
        bootcamp.setCapacities(null);

        Set<ConstraintViolation<BootcampEntity>> violations = validator.validate(bootcamp);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Getter Methods Should Return Correct Values")
    void testGetterMethods() {
        String name = "Name";
        String description = "Description";
        Set<CapacityEntity> capacities = new HashSet<>();

        BootcampEntity entity = new BootcampEntity(1L, name, description, capacities);

        assertEquals(1L, entity.getId());
        assertEquals(name, entity.getName());
        assertEquals(description, entity.getDescription());
        assertEquals(capacities, entity.getCapacities());
    }
}
