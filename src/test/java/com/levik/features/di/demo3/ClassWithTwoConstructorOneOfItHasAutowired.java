package com.levik.features.di.demo3;


import com.levik.features.di.anotation.Autowired;
import com.levik.features.di.anotation.Component;
import com.levik.features.di.demo1.A;

//@Component
public class ClassWithTwoConstructorOneOfItHasAutowired {

    private A a;

    public ClassWithTwoConstructorOneOfItHasAutowired() {
    }

    @Autowired
    public ClassWithTwoConstructorOneOfItHasAutowired(A a) {
        this.a = a;
    }
}
