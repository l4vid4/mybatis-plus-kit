package io.github.l4vid4.core.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ParameterizedTypeImpl implements ParameterizedType {
    private final Type raw;
    private final Type[] args;
    private final Type owner;

    public static ParameterizedType make(Type raw, Type[] args, Type owner) {
        return new ParameterizedTypeImpl(raw, args, owner);
    }

    private ParameterizedTypeImpl(Type raw, Type[] args, Type owner) {
        this.raw = raw;
        this.args = args;
        this.owner = owner;
    }

    @Override public Type[] getActualTypeArguments() { return args; }

    @Override public Type getRawType() { return raw; }

    @Override public Type getOwnerType() { return owner; }
}
