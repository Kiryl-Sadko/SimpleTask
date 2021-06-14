package com.jazzteam.task;

public interface Converter<T extends Number> {

    String convertToString(T number);
}
