package com.bring.bpp;

import com.bring.factory.BeanFactory;

public interface BeanPostProcessor {
    
    void postProcessBeanFactory(BeanFactory beanFactory);
    
}
