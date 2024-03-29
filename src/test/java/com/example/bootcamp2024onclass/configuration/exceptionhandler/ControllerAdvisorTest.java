package com.example.bootcamp2024onclass.configuration.exceptionhandler;

import com.example.bootcamp2024onclass.configuration.Constants;
import com.example.bootcamp2024onclass.domain.exception.CapacityTechnologiesRepeatException;
import com.example.bootcamp2024onclass.domain.exception.MaxSizeTechnologiesException;
import com.example.bootcamp2024onclass.domain.exception.MinSizeTechnologiesException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ControllerAdvisorTest {

    @Test
    void testHandleNoDataFoundException() {
        ControllerAdvisor controllerAdvisor = new ControllerAdvisor();

        ResponseEntity<ExceptionResponse> responseEntityMock = mock(ResponseEntity.class);

        when(responseEntityMock.getStatusCode()).thenReturn(HttpStatus.NOT_FOUND);
        when(responseEntityMock.getBody()).thenReturn(new ExceptionResponse(
                Constants.NO_DATA_FOUND_EXCEPTION_MESSAGE, HttpStatus.NOT_FOUND.toString(), LocalDateTime.now()));

        ResponseEntity<ExceptionResponse> responseEntity = controllerAdvisor.handleNoDataFoundException();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(Constants.NO_DATA_FOUND_EXCEPTION_MESSAGE, responseEntity.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND.toString(), responseEntity.getBody().getStatus());
        assertEquals(LocalDateTime.now().getDayOfYear(), responseEntity.getBody().getTimestamp().getDayOfYear());
    }

    @Test
    void testHandleTechnologyAlreadyExistsException() {

        ControllerAdvisor controllerAdvisor = new ControllerAdvisor();
        ResponseEntity<ExceptionResponse> responseEntityMock = mock(ResponseEntity.class);
        when(responseEntityMock.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);
        when(responseEntityMock.getBody()).thenReturn(new ExceptionResponse(
                Constants.TECHNOLOGY_ALREADY_EXISTS_EXCEPTION_MESSAGE, HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));


        ResponseEntity<ExceptionResponse> responseEntity = controllerAdvisor.handleTechnologyAlreadyExistsException();


        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(Constants.TECHNOLOGY_ALREADY_EXISTS_EXCEPTION_MESSAGE, responseEntity.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), responseEntity.getBody().getStatus());
        assertEquals(LocalDateTime.now().getDayOfYear(), responseEntity.getBody().getTimestamp().getDayOfYear());
    }

    @Test
    void testHandleElementNotFoundException() {

        ControllerAdvisor controllerAdvisor = new ControllerAdvisor();
        ResponseEntity<ExceptionResponse> responseEntityMock = mock(ResponseEntity.class);
        when(responseEntityMock.getStatusCode()).thenReturn(HttpStatus.NOT_FOUND);
        when(responseEntityMock.getBody()).thenReturn(new ExceptionResponse(
                Constants.ELEMENT_NOT_FOUND_EXCEPTION_MESSAGE, HttpStatus.CONFLICT.toString(), LocalDateTime.now()));


        ResponseEntity<ExceptionResponse> responseEntity = controllerAdvisor.handleElementNotFoundException();


        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(Constants.ELEMENT_NOT_FOUND_EXCEPTION_MESSAGE, responseEntity.getBody().getMessage());
        assertEquals(HttpStatus.CONFLICT.toString(), responseEntity.getBody().getStatus());
        assertEquals(LocalDateTime.now().getDayOfYear(), responseEntity.getBody().getTimestamp().getDayOfYear());
    }


    @Test
    void testHandleArgumentInvalidException() {

        ControllerAdvisor controllerAdvisor = new ControllerAdvisor();
        BindingResult bindingResult = mock(BindingResult.class);

        List<FieldError> fieldErrors = List.of(
                new FieldError("objectName", "field1", "error1"),
                new FieldError("objectName", "field2", "error2")
        );

        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        List<ExceptionArgumentResponse> errorList = fieldErrors.stream()
                .map(ExceptionArgumentResponse::new)
                .toList();

        ResponseEntity<List<ExceptionArgumentResponse>> responseEntityMock = mock(ResponseEntity.class);
        when(responseEntityMock.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);
        when(responseEntityMock.getBody()).thenReturn(errorList);


        ResponseEntity<List<ExceptionArgumentResponse>> responseEntity = controllerAdvisor.handlerArgumentInvalidException(exception);


        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(errorList.size(), responseEntity.getBody().size());
        assertEquals(errorList.get(0).getField(), responseEntity.getBody().get(0).getField());
        assertEquals(errorList.get(0).getMessage(), responseEntity.getBody().get(0).getMessage());
        assertEquals(errorList.get(1).getField(), responseEntity.getBody().get(1).getField());
        assertEquals(errorList.get(1).getMessage(), responseEntity.getBody().get(1).getMessage());
    }

    @Test
    @DisplayName("Handle Capacity Already Exists Exception")
    void testHandleCapacityAlreadyExistsException() {
        ControllerAdvisor controllerAdvisor = new ControllerAdvisor();
        ResponseEntity<ExceptionResponse> expectedResponse = new ResponseEntity<>(
                new ExceptionResponse(
                        Constants.CAPACITY_ALREADY_EXISTS_EXCEPTION_MESSAGE,
                        HttpStatus.BAD_REQUEST.toString(),
                        LocalDateTime.now()
                ),
                HttpStatus.BAD_REQUEST
        );

        ResponseEntity<ExceptionResponse> responseEntity = controllerAdvisor.handleCapacityAlreadyExistsException();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(Constants.CAPACITY_ALREADY_EXISTS_EXCEPTION_MESSAGE, responseEntity.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), responseEntity.getBody().getStatus());
        assertEquals(LocalDateTime.now().getDayOfYear(), responseEntity.getBody().getTimestamp().getDayOfYear());
    }

    @Test
    @DisplayName("Handle Min Size Technologies Exception")
    void testHandleMinSizeTechnologiesException() {
        ControllerAdvisor controllerAdvisor = new ControllerAdvisor();
        MinSizeTechnologiesException exception = new MinSizeTechnologiesException();
        ResponseEntity<ExceptionResponse> expectedResponse = new ResponseEntity<>(
                new ExceptionResponse(
                        String.format(Constants.INVALID_MIN_TECHNOLOGIES_EXCEPTION_MESSAGE, exception.getMessage()),
                        HttpStatus.BAD_REQUEST.toString(),
                        LocalDateTime.now()
                ),
                HttpStatus.BAD_REQUEST
        );
        ResponseEntity<ExceptionResponse> responseEntity = controllerAdvisor.handleMinSizeTechnologiesException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(Constants.INVALID_MIN_TECHNOLOGIES_EXCEPTION_MESSAGE, responseEntity.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), responseEntity.getBody().getStatus());
        assertEquals(LocalDateTime.now().getDayOfYear(), responseEntity.getBody().getTimestamp().getDayOfYear());
    }

    @Test
    @DisplayName("Handle Capacity Technologies Repeat Exception")
    void testHandleCapacityTechnologiesRepeatException() {
        ControllerAdvisor controllerAdvisor = new ControllerAdvisor();
        CapacityTechnologiesRepeatException exception = new CapacityTechnologiesRepeatException();
        ResponseEntity<ExceptionResponse> expectedResponse = new ResponseEntity<>(
                new ExceptionResponse(
                        String.format(Constants.CAPACITY_TECHNOLOGIES_REPEAT_EXCEPTION_MESSAGE, exception.getMessage()),
                        HttpStatus.BAD_REQUEST.toString(),
                        LocalDateTime.now()
                ),
                HttpStatus.BAD_REQUEST
        );

        ResponseEntity<ExceptionResponse> responseEntity = controllerAdvisor.handleCapacityTechnologiesRepeatException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(Constants.CAPACITY_TECHNOLOGIES_REPEAT_EXCEPTION_MESSAGE, responseEntity.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), responseEntity.getBody().getStatus());
        assertEquals(LocalDateTime.now().getDayOfYear(), responseEntity.getBody().getTimestamp().getDayOfYear());
    }

    @Test
    @DisplayName("Handle Max Size Technologies Exception")
    void testHandleMaxSizeTechnologiesException() {
        ControllerAdvisor controllerAdvisor = new ControllerAdvisor();
        MaxSizeTechnologiesException exception = new MaxSizeTechnologiesException();
        ResponseEntity<ExceptionResponse> expectedResponse = new ResponseEntity<>(
                new ExceptionResponse(
                        String.format(Constants.INVALID_MAX_TECHNOLOGIES_EXCEPTION_MESSAGE, exception.getMessage()),
                        HttpStatus.BAD_REQUEST.toString(),
                        LocalDateTime.now()
                ),
                HttpStatus.BAD_REQUEST
        );

        ResponseEntity<ExceptionResponse> responseEntity = controllerAdvisor.handleMaxSizeTechnologiesException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(Constants.INVALID_MAX_TECHNOLOGIES_EXCEPTION_MESSAGE, responseEntity.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), responseEntity.getBody().getStatus());
        assertEquals(LocalDateTime.now().getDayOfYear(), responseEntity.getBody().getTimestamp().getDayOfYear());
    }
}

