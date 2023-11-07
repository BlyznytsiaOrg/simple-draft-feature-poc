package com.levik.features.di.demo3;

import com.levik.features.di.context.impl.ApplicationAnnotationContext;
import org.junit.jupiter.api.Test;

class ApplicationAnnotationContextTest {

    @Test
    void shouldNotInjectValueWhenYouHaveTwoConstructorsWithOneAutowired() {
        var context = new ApplicationAnnotationContext(ApplicationAnnotationContextTest.class);
        ClassWithTwoConstructorOneOfItHasAutowired aClass = context.getBean(ClassWithTwoConstructorOneOfItHasAutowired.class);
    }
}