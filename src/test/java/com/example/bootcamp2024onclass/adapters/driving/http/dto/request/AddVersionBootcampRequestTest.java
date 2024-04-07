package com.example.bootcamp2024onclass.adapters.driving.http.dto.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AddVersionBootcampRequestTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();
    @Test
    @DisplayName("When_GetterMethodsCalled_Expect_CorrectValues")
    void testAddVersionBootcampRequest_GetterMethods() {
        Long bootcampId = 1L;
        Integer maximumQuota = 50;
        LocalDate startDate = LocalDate.of(2025, 1, 30);
        LocalDate endDate = LocalDate.of(2025, 12, 30);

        AddVersionBootcampRequest request = new AddVersionBootcampRequest(bootcampId, maximumQuota, startDate, endDate);

        assertNotNull(request);
        assertEquals(bootcampId, request.getBootcampId());
        assertEquals(maximumQuota, request.getMaximumQuota());
        assertEquals(startDate, request.getStartDate());
        assertEquals(endDate, request.getEndDate());
    }

    @Test
    @DisplayName("When_AddVersionBootcampRequestWithNullFields_Expect_FailValidation")
    void testAddVersionBootcampRequest_NullFields() {
        AddVersionBootcampRequest request = new AddVersionBootcampRequest(null, null, null, null);

        Set<ConstraintViolation<AddVersionBootcampRequest>> violations = validator.validate(request);

        assertEquals(4, violations.size());
        ConstraintViolation<AddVersionBootcampRequest> violation = violations.iterator().next();
        assertEquals(NotNull.class, violation.getConstraintDescriptor().getAnnotation().annotationType());
    }

    @Test
    @DisplayName("When_AddVersionBootcampRequestWithDateJsonFormat_Expect_SuccessValidation")
    void testAddVersionBootcampRequest_DateJsonFormat() {
        Long bootcampId = 1L;
        Integer maximumQuota = 50;
        LocalDate validStartDate = LocalDate.of(2025, 1, 30);

        AddVersionBootcampRequest requestWithValidDate = new AddVersionBootcampRequest(bootcampId, maximumQuota, validStartDate, validStartDate);

        Set<ConstraintViolation<AddVersionBootcampRequest>> violationsValidDate = validator.validate(requestWithValidDate);
        assertTrue(violationsValidDate.isEmpty());
    }
}
