package com.levik.features.di.exception;

public class NoUniqueBeanException extends RuntimeException {

    private static final String MESSAGE = "No unique bean exception %s";

    public <T> NoUniqueBeanException(Class<T> clazz) {
        super(String.format(MESSAGE, clazz));
    }
}
