package com.example.demo.util;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author duanxiaoduan
 * @version 2020/10/12
 */
public final class OptionalBean<T> {

    private static final OptionalBean<?> EMPTY = new OptionalBean<>();

    private final T value;

    private OptionalBean() {
        this.value = null;
    }

    private OptionalBean(T value) {
        this.value = value;
    }

    public static<T> OptionalBean<T> empty() {
        @SuppressWarnings("unchecked")
        OptionalBean<T> t = (OptionalBean<T>) EMPTY;
        return t;
    }

    public static <T> OptionalBean<T> ofNullable(T value) {
        return value == null ? empty() : of(value);
    }

    public static <T> OptionalBean<T> of(T value) {
        return new OptionalBean<>(value);
    }

    public T get() {
        return Objects.isNull(value) ? null : value;
    }

    public <R> OptionalBean<R> getBean(Function<? super T, ? extends R> fn) {
        return Objects.isNull(value) ? OptionalBean.empty() : OptionalBean.ofNullable(fn.apply(value));
    }
}
