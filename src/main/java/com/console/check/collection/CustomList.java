package com.console.check.collection;

public interface CustomList<E> {

    CustomIterator<E> getIterator();

    void setMaxSize(int maxSize);

    void add(E e);

    void addAll(CustomList<? extends E> list);

    void addAll(E[] es);

    E set(int index, E e);

    E remove(int index);

    void clear();

    int find(E e);

    E get(int index);

    Object[] toArray();

    int size();

    void trim();

}
