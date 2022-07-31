package com.console.check.mapper;

public interface Mapper <F, T>{

    T map(F object);
}