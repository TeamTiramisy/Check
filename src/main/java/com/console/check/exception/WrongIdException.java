package com.console.check.exception;

public class WrongIdException extends RuntimeException{
    public WrongIdException(String message) {
        super(message);
    }
}
