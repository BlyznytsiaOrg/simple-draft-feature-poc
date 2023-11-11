package com.bring.factory;

import com.bring.domain.BeanDefinition;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class BeanFactory {
    
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    
    private final List<String> beanDefinitionNames = new ArrayList<>();

    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.registerBeanDefinitions(Collections.singletonList(beanDefinition));
    }
    
    public void registerBeanDefinitions(List<BeanDefinition> beanDefinitions) {
        Map<String, BeanDefinition> definitions = beanDefinitions.stream()
                .collect(Collectors.toMap(
                        beanDefinition -> beanDefinition.getBeanClass().getSimpleName(), 
                        Function.identity()));
        
        this.beanDefinitionMap.putAll(definitions);
        this.beanDefinitionNames.addAll(beanDefinitionMap.keySet());
    }
    
}
