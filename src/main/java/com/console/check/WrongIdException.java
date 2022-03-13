package com.console.check;

public class WrongIdException extends Exception{
    public WrongIdException() {
        System.out.println("Неправильный id товара!");
    }
}
