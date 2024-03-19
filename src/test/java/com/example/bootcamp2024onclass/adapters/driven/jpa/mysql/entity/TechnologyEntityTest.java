package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity;

import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;


class TechnologyEntityTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();
    @Test
    void testTechnologyEntityBlankNameField() {
        TechnologyEntity entity = new TechnologyEntity(2L, "", "Description");
        var violations = validator.validate(entity);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testTechnologyEntityExceedingMaxLengthNameField() {
        String name = "ThisNameIsWayTooLongAndExceedsTheMaximumAllowedLengthOfFiftyCharacters";
        TechnologyEntity entity = new TechnologyEntity(3L, name, "Description");
        var violations = validator.validate(entity);
        assertFalse(violations.isEmpty());
    }


    @Test
    void testTechnologyEntityValidEntity() {
        TechnologyEntity entity = new TechnologyEntity(4L, "ValidName", "ValidDescription");
        var violations = validator.validate(entity);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testGetterMethods() {
        String name = "Name";
        String description = "Description";
        TechnologyEntity entity = new TechnologyEntity(1L, name, description);
        assertEquals(name, entity.getName());
        assertEquals(description, entity.getDescription());
    }
}
