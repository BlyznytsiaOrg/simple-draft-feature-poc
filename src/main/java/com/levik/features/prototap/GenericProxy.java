package com.levik.features.prototap;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class GenericProxy<T> implements InvocationHandler {
    private final T originalInstance;

    public GenericProxy(T originalInstance) {
        this.originalInstance = originalInstance;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // Create a new instance of the original class for each method call
        T newDependencyInstance = (T) originalInstance.getClass().newInstance();

        // Delegate the method call to the new instance
        return method.invoke(newDependencyInstance, args);
    }
}
