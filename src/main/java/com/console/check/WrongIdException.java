package com.console.check;

public class WrongIdException extends Exception{
    public WrongIdException() {
        System.out.println("Неверный id, имя или цена товара");
    }
}
