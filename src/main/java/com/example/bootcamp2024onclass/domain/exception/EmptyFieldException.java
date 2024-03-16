package com.example.bootcamp2024onclass.domain.exception;

public class EmptyFieldException extends RuntimeException{
    public EmptyFieldException(String message){
        super(message);
    }
}
