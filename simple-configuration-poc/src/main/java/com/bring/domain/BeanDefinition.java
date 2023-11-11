package com.bring.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BeanDefinition {
    
    private Class<?> beanClass;
    
    private BeanTypeEnum beanType;
    
    private List<String> dependencies = new ArrayList<>();
    
    private Object source;
    
}