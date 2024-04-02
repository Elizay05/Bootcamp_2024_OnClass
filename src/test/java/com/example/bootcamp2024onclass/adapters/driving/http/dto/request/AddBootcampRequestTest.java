package com.example.bootcamp2024onclass.adapters.driving.http.dto.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AddBootcampRequestTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    @DisplayName("When_AddBootcampRequestWithValidData_Expect_PassValidation")
    void testAddBootcampRequest_ValidRequest() {
        List<Long> capacities = Arrays.asList(1L, 2L, 3L);
        AddBootcampRequest request = new AddBootcampRequest("Bootcamp Name", "Bootcamp Description", capacities);
        var violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("When_AddBootcampRequestWithBlankFields_Expect_FailValidation")
    void testAddBootcampRequest_BlankFields() {
        List<Long> capacitties = Arrays.asList(1L, 2L, 3L);
        AddBootcampRequest request = new AddBootcampRequest("", "", capacitties);
        var violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("When_AddBootcampRequestWithExceedingMaxLengthFields_Expect_FailValidation")
    void testAddBootcampRequest_ExceedingMaxLengthFields() {
        List<Long> capacities = Arrays.asList(1L, 2L, 3L);
        String name = "ThisNameIsWayTooLongAndExceedsTheMaximumAllowedLengthOfFiftyCharacters";
        String description = "ThisIsAnExampleTextThatExceedsTheMaximumAllowedLengthOfNinetyCharactersAndIsWayTooLong";
        AddBootcampRequest request = new AddBootcampRequest(name, description, capacities);
        var violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }
    @Test
    @DisplayName("When_GetterMethodsCalled_Expect_CorrectValues")
    void testAddBootcampRequest_GetterMethods() {
        String name = "Name";
        String description = "Description";
        List<Long> capacities = Arrays.asList(1L, 2L, 2L);
        AddBootcampRequest request = new AddBootcampRequest(name, description, capacities);
        assertEquals(name, request.getName());
        assertEquals(description, request.getDescription());
        assertEquals(capacities, request.getCapacities());
    }
}
