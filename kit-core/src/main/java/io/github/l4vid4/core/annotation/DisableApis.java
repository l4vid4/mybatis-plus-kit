package io.github.l4vid4.core.annotation;

import io.github.l4vid4.core.enums.Api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DisableApis {
    Api[] value();

}
