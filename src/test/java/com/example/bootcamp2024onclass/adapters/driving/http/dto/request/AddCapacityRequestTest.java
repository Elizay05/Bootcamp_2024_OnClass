package com.example.bootcamp2024onclass.adapters.driving.http.dto.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddCapacityRequestTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    @DisplayName("Add Capacity Request with Valid Data Should Pass Validation")
    void testAddCapacityRequestTestValidRequest() {
        List<Long> technologyIds = Arrays.asList(1L, 2L, 3L);
        AddCapacityRequest request = new AddCapacityRequest("Capacity Name", "Capacity Description", technologyIds);
        var violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Add Capacity Request with Blank Fields Should Fail Validation")
    void testAddCapacityRequestTestBlankFields() {
        List<Long> technologyIds = Arrays.asList(1L, 2L, 3L);
        AddCapacityRequest request = new AddCapacityRequest("", "", technologyIds);
        var violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Add Capacity Request with Exceeding Max Length Fields Should Fail Validation")
    void testAddCapacityRequestTestExceedingMaxLengthFields() {
        List<Long> technologyIds = Arrays.asList(1L, 2L, 3L);
        String name = "ThisNameIsWayTooLongAndExceedsTheMaximumAllowedLengthOfFiftyCharacters";
        String description = "ThisIsAnExampleTextThatExceedsTheMaximumAllowedLengthOfNinetyCharactersAndIsWayTooLong";
        AddCapacityRequest request = new AddCapacityRequest(name, description, technologyIds);
        var violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Add Capacity Request with Duplicate Technology IDs Should Fail Validation")
    void testAddCapacityRequestTestDuplicateTechnologyIds() {
        List<Long> technologyIds = Arrays.asList(1L, 2L, 2L);
        AddCapacityRequest request = new AddCapacityRequest("Capacity Name", "Capacity Description", technologyIds);
        var violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }
    @Test
    @DisplayName("Getter Methods Should Return Correct Values")
    void testAddCapacityRequestTestGetterMethods() {
        String name = "Name";
        String description = "Description";
        List<Long> technologyIds = Arrays.asList(1L, 2L, 2L);
        AddCapacityRequest request = new AddCapacityRequest(name, description, technologyIds);
        assertEquals(name, request.getName());
        assertEquals(description, request.getDescription());
        assertEquals(technologyIds, request.getTechnologyIds());
    }

}
