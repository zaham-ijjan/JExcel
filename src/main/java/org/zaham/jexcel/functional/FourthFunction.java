package org.zaham.jexcel.functional;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

@FunctionalInterface
public interface FourthFunction<A, B, C, D, R> {
    R apply(A t, B u, C v, D w);

    default <V> FourthFunction<A, B, C,D,V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (A a, B b, C c,D d) -> after.apply(apply(a, b, c,d));
    }
}