package com.console.check.collection;

import java.util.Arrays;

public class CustomArrayList<E> implements CustomList<E> {

    private E[] values;
    private int size;
    private boolean isMaxSize = false;
    private static final Object[] DEFAULT = {};


    public CustomArrayList() {
        this.values = (E[]) DEFAULT;
    }

    public CustomArrayList(int initialSize) {
        this.values = (E[]) new Object[initialSize];
    }

    @Override
    public void setMaxSize(int maxSize) {
        isMaxSize = true;
        if (size > maxSize){
        E[] tmp = (E[]) new Object[maxSize];
            for (int i = 0; i < tmp.length; i++) {
                tmp[i] = values[i];
            }
            values = tmp;
            size = maxSize;
        } else {
            E[] tmp = values;
            values = (E[]) new Object[maxSize];
            System.arraycopy(tmp, 0, values, 0, tmp.length);
        }
    }

    @Override
    public void add(E e) {
        if (isMaxSize && size < values.length){
            values[size++] = e;
        }
        if (isMaxSize){
            return;
        }
        if (size < values.length) {
            values[size++] = e;
        } else {
            E[] tmp = values;
            values = (E[]) new Object[tmp.length + 1];
            System.arraycopy(tmp, 0, values, 0, tmp.length);
            values[size++] = e;
        }
    }

    @Override
    public void addAll(CustomList<? extends E> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));

        }
    }

    @Override
    public void addAll(E[] es) {
        for (int i = 0; i < es.length; i++) {
            add(es[i]);
        }
    }

    @Override
    public E set(int index, E e) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Индекс " + index + " выходит за пределы длины " + size);
        }
        E oldElement;
        oldElement = values[index];
        values[index] = e;
        return oldElement;
    }

    @Override
    public E remove(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Индекс " + index + " выходит за пределы длины " + size);
        }
        E oldElement;
        oldElement = values[index];
        E[] tmp = values;
        values = (E[]) new Object[tmp.length - 1];
        System.arraycopy(tmp, 0, values, 0, index);
        System.arraycopy(tmp, index + 1, values, index, tmp.length - index - 1);
        --size;
        return oldElement;
    }

    @Override
    public void clear() {
        values = (E[]) DEFAULT;
        size = 0;
    }

    @Override
    public int find(E e) {
        for (int i = 0; i < values.length; i++) {
            if (values[i].equals(e))
                return i;
        }
        return -1;
    }

    @Override
    public E get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Индекс " + index + " выходит за пределы длины " + size);
        }
        return values[index];
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(values, size);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void trim() {
        for (int i = 0; i < size; i++) {
            if (values[i] == null) {
                remove(i);
            }
        }
    }

    @Override
    public CustomIterator<E> getIterator() {
        return new ArrayListIterator();
    }


    private class ArrayListIterator implements CustomIterator<E> {

        private int cursor;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public E next() {
            return values[cursor++];
        }

        @Override
        public void remove() {
            if (cursor == size){
                CustomArrayList.this.remove(size - 1);
            } else {
                int indexElement = find(next()) - 1;
                CustomArrayList.this.remove(indexElement);
                cursor = cursor - 2;
            }
        }

        @Override
        public void addBefore(E e) {
            E element = values[cursor - 1];
            values[cursor- 1] = e;
            E[] tmp = (E[]) new Object[values.length + 1];
            System.arraycopy(values, 0, tmp, 0, cursor);
            tmp[cursor] = element;
            System.arraycopy(values, cursor, tmp, cursor + 1, values.length - cursor);
            values = tmp;
            size++;
            cursor++;
        }


        @Override
        public void addAfter(E e) {
            if (cursor == size){
                add(e);
                cursor++;
            } else {
                E element = set(cursor, e);
                E[] tmp = (E[]) new Object[values.length + 1];
                System.arraycopy(values, 0, tmp, 0, cursor + 1);
                tmp[cursor + 1] = element;
                System.arraycopy(values, cursor + 1, tmp, cursor + 2, values.length - cursor - 1);
                values = tmp;
                size++;
                cursor++;
            }
        }
    }

    public String toString() {
        CustomIterator<E> it = getIterator();
        if (!it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (; ; ) {
            E e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (!it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }

}
