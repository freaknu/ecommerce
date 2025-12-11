package com.microservice.productservice.product_service.aop;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RoleAnnotation {
    String value() default "";
}
