package com.bring.bpp;

import com.bring.domain.BeanDefinition;
import com.bring.domain.BeanTypeEnum;
import com.bring.factory.BeanFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;

public class ConfigurationClassPostProcessor implements BeanPostProcessor {
    
    @Override
    public void postProcessBeanFactory(BeanFactory beanFactory) {
        Map<String, BeanDefinition> beanDefinitionMap = beanFactory.getBeanDefinitionMap();
        
        beanFactory.getBeanDefinitionNames()
                .forEach(beanName -> {
                    BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                    
                    if (beanDefinition.getBeanType() == BeanTypeEnum.CONFIGURATION) {
                      for (Method method : beanDefinition.getBeanClass().getMethods()) {
                        loadBeanDefinitionsForBeanMethod(beanFactory, method);
                      }
                    }
                    
                });
    }

    private void loadBeanDefinitionsForBeanMethod(BeanFactory beanFactory, Method method) {
        BeanDefinition beanDefinition = BeanDefinition.builder()
            .beanClass(method.getReturnType())
            .beanType(BeanTypeEnum.findBeanType(method))
            .dependencies(Arrays.stream(method.getParameters())
                .map(Parameter::getName)
                .toList())
            .build();
        
        if (beanDefinition.getDependencies().isEmpty()) {
          beanFactory.registerBeanDefinition(beanDefinition);
        } else {
          
        }
    }

}
