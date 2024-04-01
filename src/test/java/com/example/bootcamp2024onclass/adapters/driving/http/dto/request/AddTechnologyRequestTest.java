package com.example.bootcamp2024onclass.adapters.driving.http.dto.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

class AddTechnologyRequestTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test

    @DisplayName("When_AddTechnologyRequestWithBlankFields_Expect_FailValidation")
    void testAddTechnologyRequest_BlankFields() {
        AddTechnologyRequest request = new AddTechnologyRequest("", "");
        var violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test

    @DisplayName("When_AddTechnologyRequestWithExceedingMaxLengthFields_Expect_FailValidation")
    void testAddTechnologyRequest_ExceedingMaxLengthFields() {
        String name = "ThisNameIsWayTooLongAndExceedsTheMaximumAllowedLengthOfFiftyCharacters";
        String description = "ThisIsAnExampleTextThatExceedsTheMaximumAllowedLengthOfNinetyCharactersAndIsWayTooLong";
        AddTechnologyRequest request = new AddTechnologyRequest(name, description);
        var violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("When_AddTechnologyRequestWithValidData_Expect_PassValidation")
    void testAddTechnologyRequestTestValidRequest() {
        AddTechnologyRequest request = new AddTechnologyRequest("ValidName", "ValidDescription");
        var violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("When_GetterMethodsCalled_Expect_CorrectValues")
    void testAddTechnologyRequest_GetterMethods() {
        String name = "Name";
        String description = "Description";
        AddTechnologyRequest request = new AddTechnologyRequest(name, description);
        assertEquals(name, request.getName());
        assertEquals(description, request.getDescription());
    }
}
