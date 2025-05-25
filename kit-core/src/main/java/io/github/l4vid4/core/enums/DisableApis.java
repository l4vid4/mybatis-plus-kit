package io.github.l4vid4.core.enums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DisableApis {
    Api[] value();

    enum Api{
        GET_BY_ID,
        LIST,
        LIST_BY_CONDITION,
        LIST_BY_IDS,
        PAGE,
        PAGE_VO,
        SAVE,
        UPDATE,
        DELETE_BY_ID,
        DELETE
    }
}
