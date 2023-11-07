package com.levik.features.di.bpp;

import com.levik.features.di.context.ApplicationContext;

public interface BeanPostProcessor {

    void setApplicationContext(ApplicationContext applicationContext);

    default Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }
}
