package org.zaham.jexcel.functional;

import java.io.IOException;
import java.util.Objects;
@FunctionalInterface
public interface FourthFunction<A, B, C, D, R> {
    R apply(A t, B u, C v, D w) throws IllegalAccessException, IOException;

    default <V> FourthFunction<A, B, C,D,V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (A a, B b, C c,D d) -> after.apply(apply(a, b, c,d));
    }
}