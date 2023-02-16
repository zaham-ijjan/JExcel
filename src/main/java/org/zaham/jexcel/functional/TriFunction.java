package org.zaham.jexcel.functional;

import java.util.Objects;

@FunctionalInterface
public interface TriFunction<A, B, C, R> {

    R apply(A a, B b, C c) throws Exception;

    default <V> TriFunction<A, B, C, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (A a, B b, C c) -> after.apply(apply(a, b, c));
    }
}