package com.bring.context;

import com.bring.bpp.BeanPostProcessor;
import com.bring.bpp.ConfigurationClassPostProcessor;
import com.bring.domain.BeanDefinition;
import com.bring.domain.BeanTypeEnum;
import com.bring.factory.BeanFactory;

import java.util.List;
import java.util.stream.Stream;

public class AnnotationConfigApplicationContext implements BringApplicationContext {
    
    private final List<BeanPostProcessor> processors = List.of(new ConfigurationClassPostProcessor());
    
    private final BeanFactory beanFactory;
    
    public AnnotationConfigApplicationContext(Class<?> source) {
        this.beanFactory = new BeanFactory();
        register(source);
    }

    private void register(Class<?> clazz) {
        BeanDefinition beanDefinition = BeanDefinition.builder()
            .beanClass(clazz)
            .beanType(BeanTypeEnum.findBeanType(clazz))
            .build();
        
        beanFactory.registerBeanDefinition(beanDefinition);
    }

    @Override
    public void refresh() {
        processors.forEach(processor -> processor.postProcessBeanFactory(beanFactory));
    }
    
}
