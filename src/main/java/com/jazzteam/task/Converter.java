package com.jazzteam.task;

public interface Converter<T extends Number> {

    String toString(T number);
}
