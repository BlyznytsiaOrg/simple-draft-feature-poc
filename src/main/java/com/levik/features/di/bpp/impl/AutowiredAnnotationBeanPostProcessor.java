package com.levik.features.di.bpp.impl;

import com.levik.features.di.anotation.Autowired;
import com.levik.features.di.bpp.BeanPostProcessor;
import com.levik.features.di.context.ApplicationContext;

import static com.levik.features.di.utils.ReflectionUtils.setField;

public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        var beanType = bean.getClass();

        for (var field : beanType.getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                var dependency = applicationContext.getBean(field.getType());
                setField(field, bean, dependency);
            }
        }

        return bean;
    }
}
