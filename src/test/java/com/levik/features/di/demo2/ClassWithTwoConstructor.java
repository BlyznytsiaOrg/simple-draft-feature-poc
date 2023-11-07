package com.levik.features.di.demo2;

import com.levik.features.di.anotation.Component;
import com.levik.features.di.demo1.A;

//@Component
public class ClassWithTwoConstructor {

    private A a;

    public ClassWithTwoConstructor() {
    }

    public ClassWithTwoConstructor(A a) {
        this.a = a;
    }
}
