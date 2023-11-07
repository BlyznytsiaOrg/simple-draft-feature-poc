package com.levik.features.di.context;

import com.levik.features.di.anotation.Autowired;
import com.levik.features.di.anotation.resolver.AnnotationResolver;
import com.levik.features.di.anotation.resolver.impl.ComponentBeanNameAnnotationResolver;
import com.levik.features.di.exception.NoConstructorWithAutowiredAnnotationBeanException;
import lombok.Getter;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import static com.levik.features.di.utils.ReflectionUtils.setField;

public class BeanFactory {

    private static final String SET_METHOD_START_PREFIX = "set";

    private final Map<String, Object> beansNameToObject = new HashMap<>();
    private final List<AnnotationResolver> annotationResolvers = List.of(
            new ComponentBeanNameAnnotationResolver()
    );

    @SneakyThrows
    public void createBeans(Set<Class<?>> classes) {
        for (Class<?> clazz : classes) {
            createBean(clazz);
        }
    }

    private void createBean(Class<?> clazz) {
        String beanName = resolveBeanName(clazz);

        if (beansNameToObject.containsKey(beanName)) {
            return; // Bean with this name already created, no need to create it again.
        }

        findAutowiredConstructor(clazz)
                .map(constructorToUse -> createBeanUsingConstructor(constructorToUse, beanName))
                .orElseThrow(() -> new NoConstructorWithAutowiredAnnotationBeanException(clazz));

        injectDependencies(clazz, beanName);
    }

    private Optional<Constructor<?>> findAutowiredConstructor(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getConstructors();

        if (constructors.length == 1) {
            return Optional.of(constructors[0]);
        }

        Optional<Constructor<?>> autowiredConstructor = Arrays.stream(constructors)
                .filter(constructor -> constructor.isAnnotationPresent(Autowired.class))
                .findFirst();

        if (constructors.length > 1 && autowiredConstructor.isEmpty()) {
            throw new NoConstructorWithAutowiredAnnotationBeanException(clazz, Arrays.toString(constructors));
        }

        return autowiredConstructor;
    }

    @SneakyThrows
    private Object createBeanUsingConstructor(Constructor<?> constructor, String beanName) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] dependencies = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            String dependencyBeanName = resolveBeanName(parameterTypes[i]);
            Object dependencyObject = getOrCreateBean(dependencyBeanName, parameterTypes[i]);
            dependencies[i] = dependencyObject;
        }

        Object bean = constructor.newInstance(dependencies);
        beansNameToObject.put(beanName, bean);
        return constructor;
    }

    private Object getOrCreateBean(String beanName, Class<?> beanType) {
        Object existingBean = beansNameToObject.get(beanName);
        if (existingBean != null) {
            return existingBean;
        } else {
            createBean(beanType);
            return beansNameToObject.get(beanName);
        }
    }

    private void injectDependencies(Class<?> clazz, String beanName) {
        Object bean = beansNameToObject.get(beanName);

        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Autowired.class))
                .forEach(field -> injectDependencyViaField(field, bean));

        Arrays.stream(clazz.getDeclaredMethods())
                .filter(this::isAutowiredSetterMethod)
                .forEach(method -> injectDependencyViaMethod(method, bean));
    }

    @SneakyThrows
    private void injectDependencyViaMethod(Method method, Object bean) {
        if (isAutowiredSetterMethod(method)) {
            Class<?> dependencyType = method.getParameterTypes()[0];
            String dependencyBeanName = resolveBeanName(dependencyType);
            Object dependencyObject = getOrCreateBean(dependencyBeanName, dependencyType);
            method.invoke(bean, dependencyObject);
        }
    }

    private void injectDependencyViaField(Field field, Object bean) {
        String dependencyBeanName = resolveBeanName(field.getType());
        Object dependencyObject = getOrCreateBean(dependencyBeanName, field.getType());
        setField(field, bean, dependencyObject);
    }

    private boolean isAutowiredSetterMethod(Method method) {
        return method.isAnnotationPresent(Autowired.class) && method.getName().startsWith(SET_METHOD_START_PREFIX);
    }

    private String resolveBeanName(Class<?> clazz) {
        return annotationResolvers.stream()
                .filter(resolver -> resolver.isSupported(clazz))
                .findFirst()
                .map(annotationResolver -> annotationResolver.resolver(clazz))
                .orElseThrow(() -> new IllegalStateException("No suitable resolver found for " + clazz.getName()));
    }

    public Map<String, Object> getBeansNameToObject() {
        return beansNameToObject;
    }
}