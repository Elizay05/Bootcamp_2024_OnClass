package com.example.Bootcamp_2024_OnClass.domain.exception;

public class EmptyFieldException extends RuntimeException{
    public EmptyFieldException(String message){
        super(message);
    }
}
