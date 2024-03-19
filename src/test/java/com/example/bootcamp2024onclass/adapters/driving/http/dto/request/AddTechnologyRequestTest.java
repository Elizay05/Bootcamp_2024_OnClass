package com.example.bootcamp2024onclass.adapters.driving.http.dto.request;

import org.junit.jupiter.api.Test;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

class AddTechnologyRequestTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    void testAddTechnologyRequestTestBlankNameField() {
        AddTechnologyRequest request = new AddTechnologyRequest("", "Description");
        var violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testAddTechnologyRequestTestExceedingMaxLengthNameField() {
        String name = "ThisNameIsWayTooLongAndExceedsTheMaximumAllowedLengthOfFiftyCharacters";
        AddTechnologyRequest request = new AddTechnologyRequest(name, "Description");
        var violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testAddTechnologyRequestTestValidRequest() {
        AddTechnologyRequest request = new AddTechnologyRequest("ValidName", "ValidDescription");
        var violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testAddTechnologyRequestTestGetterMethods() {
        String name = "Name";
        String description = "Description";
        AddTechnologyRequest request = new AddTechnologyRequest(name, description);
        assertEquals(name, request.getName());
        assertEquals(description, request.getDescription());
    }
}
