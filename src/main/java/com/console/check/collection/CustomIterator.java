package com.console.check.collection;

public interface CustomIterator<E> {

    boolean hasNext();


    E next();


    void remove();

    void addBefore(E e);

    void addAfter(E e);
}
