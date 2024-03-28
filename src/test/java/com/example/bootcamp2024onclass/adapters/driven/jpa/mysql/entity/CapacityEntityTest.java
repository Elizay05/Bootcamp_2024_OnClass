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

class CapacityEntityTest {
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    @DisplayName("When_CapacityEntityIsValid_Expect_NoViolations")
    void testCapacityEntityValidEntity() {
        TechnologyEntity technology = new TechnologyEntity();

        Set<TechnologyEntity> technologies = new HashSet<>();
        technologies.add(technology);

        CapacityEntity entity = new CapacityEntity(4L, "ValidName", "ValidDescription", technologies);

        var violations = validator.validate(entity);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Fields Should Not Be Blank")
    void testCapacityEntityFieldsNotBlank() {
        CapacityEntity capacity = new CapacityEntity();
        capacity.setName("");
        capacity.setDescription("");
        capacity.setTechnologies(new HashSet<>());
        Set<ConstraintViolation<CapacityEntity>> violations = validator.validate(capacity);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Fields Should Not Exceed Maximum Length")
    void testCapacityEntityFieldsMaxLengthExceeded() {
        CapacityEntity capacity = new CapacityEntity();
        capacity.setName("ThisNameIsWayTooLongAndExceedsTheMaximumAllowedLengthOfFiftyCharacters");
        capacity.setDescription("ThisIsAnExampleTextThatExceedsTheMaximumAllowedLengthOfNinetyCharactersAndIsWayTooLong");
        capacity.setTechnologies(new HashSet<>());

        Set<ConstraintViolation<CapacityEntity>> violations = validator.validate(capacity);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Technologies Should Not Be Null")
    void testCapacityEntityTechnologiesNotNull() {
        CapacityEntity capacity = new CapacityEntity();
        capacity.setName("Test name");
        capacity.setDescription("Test description");
        capacity.setTechnologies(null);

        Set<ConstraintViolation<CapacityEntity>> violations = validator.validate(capacity);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Getter Methods Should Return Correct Values")
    void testGetterMethods() {
        String name = "Name";
        String description = "Description";
        Set<TechnologyEntity> technologies = new HashSet<>();

        CapacityEntity entity = new CapacityEntity(1L, name, description, technologies);

        assertEquals(1L, entity.getId());
        assertEquals(name, entity.getName());
        assertEquals(description, entity.getDescription());
        assertEquals(technologies, entity.getTechnologies());
    }
}
