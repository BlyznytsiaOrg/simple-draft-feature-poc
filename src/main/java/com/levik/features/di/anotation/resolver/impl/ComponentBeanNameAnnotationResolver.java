package com.levik.features.di.anotation.resolver.impl;

import com.levik.features.di.anotation.Component;
import com.levik.features.di.anotation.resolver.AnnotationResolver;

public class ComponentBeanNameAnnotationResolver implements AnnotationResolver {
    @Override
    public boolean isSupported(Class<?> clazz) {
        return  clazz.getAnnotation(Component.class) != null;
    }

    @Override
    public String resolver(Class<?> clazz) {
        String value = clazz.getAnnotation(Component.class).value();
        return value.isEmpty() ? clazz.getSimpleName() : value;
    }
}
