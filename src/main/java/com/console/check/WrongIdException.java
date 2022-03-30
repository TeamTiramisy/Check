package com.console.check;

public class WrongIdException extends RuntimeException{

    public WrongIdException(String message) {
        super(message);
    }
}
