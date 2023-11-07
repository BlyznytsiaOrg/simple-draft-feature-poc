package com.levik.features.di.demo1;

import com.levik.features.di.anotation.Autowired;
import com.levik.features.di.anotation.Component;

@Component
public class A {

    private String message;

    @Autowired
    private C c;

    public A() {
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public C getC() {
        return c;
    }

    @Override
    public String toString() {
        return "A{" +
                "message='" + message + '\'' +
                ", c=" + c +
                '}';
    }
}
