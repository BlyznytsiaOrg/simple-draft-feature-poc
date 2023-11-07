package com.levik.features.di.context.impl;

import com.levik.features.di.anotation.Component;
import com.levik.features.di.bpp.BeanPostProcessor;
import com.levik.features.di.bpp.impl.AutowiredAnnotationBeanPostProcessor;
import com.levik.features.di.context.ApplicationContext;
import com.levik.features.di.context.BeanFactory;
import com.levik.features.di.exception.NoUniqueBeanException;
import com.levik.features.di.exception.NotFoundBeanException;
import org.reflections.Reflections;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

public class ApplicationAnnotationContext implements ApplicationContext {

    public final BeanFactory beanFactory;

    private final List<BeanPostProcessor> beanPostProcessors = List.of(
            new AutowiredAnnotationBeanPostProcessor()
    );

    public ApplicationAnnotationContext(String basePackage) {
        Reflections reflections = new Reflections(basePackage);
        beanFactory = new BeanFactory();
        Set<Class<?>> classesThatAnnotatedWithComponent = reflections.getTypesAnnotatedWith(Component.class);
        beanFactory.createBeans(classesThatAnnotatedWithComponent);
        //beanPostProcessors();
    }

    Map<String, Object> beansNameToObject() {
        return beanFactory.getBeansNameToObject();
    }

    public <T> ApplicationAnnotationContext(Class<T> clazz) {
        this(clazz.getPackageName());
    }

    @Override
    public <T> T getBean(Class<T> type) {
        Map<String, T> beans = getBeans(type);

        if (beans.size() > 1) {
            throw new NoUniqueBeanException(type);
        }

        return beans.values().stream().findFirst().orElseThrow(() -> new NotFoundBeanException(type));
    }

    @Override
    public <T> T getBean(Class<T> type, String name) {
        Object obj = beansNameToObject().get(name);
        return type.cast(obj);
    }

    @Override
    public <T> Map<String, T> getBeans(Class<T> type) {
        return beansNameToObject().entrySet()
                .stream()
                .filter(entry -> type.isAssignableFrom(entry.getValue().getClass()))
                .collect(toMap(Map.Entry::getKey, t -> type.cast(t.getValue())));
    }

    @Override
    public <T> Map<String, T> getAllBeans() {
        return (Map<String, T>) beansNameToObject();
    }

    @Override
    public void addBean(String name, Object objOrProxy) {
        beansNameToObject().put(name, objOrProxy);
    }


    private void beanPostProcessors() {
        for(var beanEntry : beansNameToObject().entrySet()) {
            var beanName = beanEntry.getKey();
            var bean = beanEntry.getValue();

            for (var beanPostProcessor : beanPostProcessors) {
                beanPostProcessor.setApplicationContext(this);
                beanPostProcessor.postProcessBeforeInitialization(bean, beanName);
            }
        }
    }
}
