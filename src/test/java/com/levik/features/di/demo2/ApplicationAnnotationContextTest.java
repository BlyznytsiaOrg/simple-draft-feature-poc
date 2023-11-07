package com.levik.features.di.demo2;

import com.levik.features.di.context.impl.ApplicationAnnotationContext;
import org.junit.jupiter.api.Test;

class ApplicationAnnotationContextTest {

    @Test
    void shouldNotInjectValueWhenYouHaveTwoConstructorsWithoutAutowired() {
        var context = new ApplicationAnnotationContext(ApplicationAnnotationContextTest.class);
        ClassWithTwoConstructor aClass = context.getBean(ClassWithTwoConstructor.class);
    }
}