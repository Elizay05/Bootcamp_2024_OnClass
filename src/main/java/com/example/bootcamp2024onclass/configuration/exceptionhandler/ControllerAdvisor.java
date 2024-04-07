package com.example.bootcamp2024onclass.configuration.exceptionhandler;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.*;
import com.example.bootcamp2024onclass.configuration.Constants;
import com.example.bootcamp2024onclass.domain.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;


@ControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvisor {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ExceptionArgumentResponse>> handlerArgumentInvalidException(MethodArgumentNotValidException exception) {
        var errors = exception.getFieldErrors()
                .stream()
                .map(ExceptionArgumentResponse::new)
                .toList();
        return ResponseEntity.badRequest().body(errors);
    }
    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNoDataFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(
                Constants.NO_DATA_FOUND_EXCEPTION_MESSAGE, HttpStatus.NOT_FOUND.toString(), LocalDateTime.now()));
    }
    @ExceptionHandler(TechnologyAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleTechnologyAlreadyExistsException() {
        return ResponseEntity.badRequest().body(new ExceptionResponse(Constants.TECHNOLOGY_ALREADY_EXISTS_EXCEPTION_MESSAGE,
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }
    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleElementNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(
                Constants.ELEMENT_NOT_FOUND_EXCEPTION_MESSAGE, HttpStatus.CONFLICT.toString(), LocalDateTime.now()));
    }
    @ExceptionHandler(CapacityAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleCapacityAlreadyExistsException() {
        return ResponseEntity.badRequest().body(new ExceptionResponse(Constants.CAPACITY_ALREADY_EXISTS_EXCEPTION_MESSAGE,
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }
    @ExceptionHandler(MinSizeTechnologiesException.class)
    public ResponseEntity<ExceptionResponse> handleMinSizeTechnologiesException(MinSizeTechnologiesException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.INVALID_MIN_TECHNOLOGIES_EXCEPTION_MESSAGE, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(CapacityTechnologiesRepeatException.class)
    public ResponseEntity<ExceptionResponse> handleCapacityTechnologiesRepeatException(CapacityTechnologiesRepeatException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.CAPACITY_TECHNOLOGIES_REPEAT_EXCEPTION_MESSAGE, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(MaxSizeTechnologiesException.class)
    public ResponseEntity<ExceptionResponse> handleMaxSizeTechnologiesException(MaxSizeTechnologiesException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.INVALID_MAX_TECHNOLOGIES_EXCEPTION_MESSAGE, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(BootcampAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleBootcampAlreadyExistsException() {
        return ResponseEntity.badRequest().body(new ExceptionResponse(Constants.BOOTCAMP_ALREADY_EXISTS_EXCEPTION_MESSAGE,
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(MinSizeCapacitesException.class)
    public ResponseEntity<ExceptionResponse> handleMinSizeCapacitiesException(MinSizeCapacitesException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.INVALID_MIN_CAPACITIES_EXCEPTION_MESSAGE, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(BootcampCapacitiesRepeatException.class)
    public ResponseEntity<ExceptionResponse> handleBootcampCapacitiesRepeatException(BootcampCapacitiesRepeatException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.BOOTCAMP_CAPACITIES_REPEAT_EXCEPTION_MESSAGE, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(MaxSizeCapacitiesException.class)
    public ResponseEntity<ExceptionResponse> handleMaxSizeCapacitiesException(MaxSizeCapacitiesException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.INVALID_MAX_CAPACITIES_EXCEPTION_MESSAGE, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }
    @ExceptionHandler(DateVersionBootcampAlreadyUseException.class)
    public ResponseEntity<ExceptionResponse> handleDateVersionBootcampAlreadyUseException() {
        return ResponseEntity.badRequest().body(new ExceptionResponse(Constants.DATE_VERSIONBOOTCAMP_ALREADY_USE_EXCEPTION_MESSAGE,
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(StartDateBeforeCurrentDateException.class)
    public ResponseEntity<ExceptionResponse> handleStartDateBeforeCurrentDateException(StartDateBeforeCurrentDateException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.STARTDATE_BEFORE_CURRENTDATE_EXCEPTION_MESSAGE, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(StartDateAfterEndDateException.class)
    public ResponseEntity<ExceptionResponse> handleStartDateAfterEndDateException(StartDateAfterEndDateException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.STARTDATE_AFTER_ENDDATE_EXCEPTION_MESSAGE, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }
}


