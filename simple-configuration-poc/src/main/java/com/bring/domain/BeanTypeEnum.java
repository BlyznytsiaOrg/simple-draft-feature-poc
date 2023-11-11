package com.bring.domain;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.bring.annotation.Bean;
import com.bring.annotation.Configuration;
import com.bring.utils.Pair;

public enum BeanTypeEnum {
    
    CONFIGURATION(Configuration.class),
    
    CONFIG_BEAN(Bean.class);

    private final List<Class<?>> annotationClasses;
    
    BeanTypeEnum(Class<?>... annotationClasses) {
        this.annotationClasses = List.of(annotationClasses);
    }

    public static BeanTypeEnum findBeanType(Class<?> clazz) {
        Map<Class<?>, BeanTypeEnum> annotationToBeanType = getAnnotationToBeanType();
        
        return Arrays.stream(clazz.getAnnotations())
                .map(annotation -> annotationToBeanType.get(annotation.getClass()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format(
                    "Unable to create Bean of type=[%s]. Class is not annotated with [%s]", 
                    clazz.getSimpleName(), 
                    Arrays.toString(values()))));
    }

    public static BeanTypeEnum findBeanType(Method method) {
        Map<Class<?>, BeanTypeEnum> annotationToBeanType = getAnnotationToBeanType();

        return Arrays.stream(method.getAnnotations())
            .map(annotation -> annotationToBeanType.get(annotation.getClass()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException(String.format(
                "Unable to create Bean of type=[%s]. Method is not annotated with [%s]",
                method.getReturnType(), 
                Arrays.toString(values()))));
    }
    
    private static Map<Class<?>, BeanTypeEnum> getAnnotationToBeanType() {
        return Arrays.stream(values())
            .flatMap(beanType -> beanType.getAnnotationClasses()
                .stream()
                .map(annotation -> Pair.of(annotation, beanType)))
            .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
    }
    
    private List<Class<?>> getAnnotationClasses() {
        return annotationClasses;
    }
    
}
