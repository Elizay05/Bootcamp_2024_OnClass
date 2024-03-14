package com.example.Bootcamp_2024_OnClass.configuration.exceptionhandler;

import org.springframework.validation.FieldError;

public class ExceptionArgumentResponse {
    private String field;
    private String message;

    public ExceptionArgumentResponse(FieldError fieldError) {
        this.field = fieldError.getField();
        this.message = fieldError.getDefaultMessage();
    }
    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

}
