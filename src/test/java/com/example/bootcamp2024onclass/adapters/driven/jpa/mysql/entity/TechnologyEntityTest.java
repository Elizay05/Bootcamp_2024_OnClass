package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;


class TechnologyEntityTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();
    @Test
    @DisplayName("Fields Should Not Be Blank")
    void testTechnologyEntityFieldsNotBlank() {
        TechnologyEntity entity = new TechnologyEntity(2L, "", "");
        var violations = validator.validate(entity);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Fields Should Not Exceed Maximum Length")
    void testTechnologyEntityFieldsMaxLengthExceeded() {
        String name = "ThisNameIsWayTooLongAndExceedsTheMaximumAllowedLengthOfFiftyCharacters";
        String description = "ThisIsAnExampleTextThatExceedsTheMaximumAllowedLengthOfNinetyCharactersAndIsWayTooLong";
        TechnologyEntity entity = new TechnologyEntity(3L, name, description);
        var violations = validator.validate(entity);
        assertFalse(violations.isEmpty());
    }


    @Test
    @DisplayName("When_TechnologyEntityIsValid_Expect_NoViolations")
    void testTechnologyEntityValidEntity() {
        TechnologyEntity entity = new TechnologyEntity(4L, "ValidName", "ValidDescription");
        var violations = validator.validate(entity);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Getter Methods Should Return Correct Values")
    void testGetterMethods() {
        String name = "Name";
        String description = "Description";
        TechnologyEntity entity = new TechnologyEntity(1L, name, description);
        assertEquals(name, entity.getName());
        assertEquals(description, entity.getDescription());
    }
}
