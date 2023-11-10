package com.levik.features.generic;

import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        Class<A> aClass = A.class;

        Reflections reflections = new Reflections("com.levik.features.generic");

        for (var field : aClass.getDeclaredFields()) {
            Class<?> type = field.getType();

            if (type.isAssignableFrom(List.class)) {
                Type genericType = field.getGenericType();
                if (genericType instanceof ParameterizedType parameterizedType) {
                    Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];

                    System.out.println(field.getName() + " " + field.getType() + " " + actualTypeArgument);

                    if (actualTypeArgument instanceof Class actualTypeArgumentClass) {
                        String name = actualTypeArgumentClass.getName();
                        Class<?> interfaceClass = Class.forName(name);

                        reflections.getSubTypesOf(interfaceClass)
                                .stream()
                                .map(Class::getName)
                                .forEach(System.out::println);
                    }

                }

            }
        }
    }
}
