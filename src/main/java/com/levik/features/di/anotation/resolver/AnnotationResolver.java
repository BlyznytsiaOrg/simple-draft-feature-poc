package com.levik.features.di.anotation.resolver;

public interface AnnotationResolver {

    boolean isSupported(Class<?> clazz);
    String resolver(Class<?> clazz);
}
