package com.levik.features.di;

import com.levik.features.di.context.impl.ApplicationAnnotationContext;
import com.levik.features.di.demo1.A;
import com.levik.features.di.demo1.B;
import com.levik.features.di.demo1.C;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationAnnotationContextTest {

    @Test
    void shouldValidateThatContentCreateCorrectly() {
        String message = "Hi, Yevgen";
        var context = new ApplicationAnnotationContext(ApplicationAnnotationContextTest.class);
        A aClass = context.getBean(A.class);
        aClass.setMessage(message);
        B bClass = context.getBean(B.class);
        C cBean = context.getBean(C.class);

        assertThat(bClass).isNotNull();
        assertThat(bClass.getA()).isNotNull();
        assertThat(bClass.getA()).isEqualTo(aClass);
        assertThat(bClass.getA().getMessage()).isEqualTo(message);
        assertThat(aClass.getC()).isNotNull();
        assertThat(aClass.getC()).isEqualTo(cBean);

        assertThat(context.getAllBeans().size()).isEqualTo(3);
    }
}