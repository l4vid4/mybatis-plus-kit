package io.github.l4vid4.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoApi {
    // 自定义路径前缀
    String path() default "";

    Class<?> service() default Object.class;
}
