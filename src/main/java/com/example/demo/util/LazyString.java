package com.example.demo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author duanxiaoduan
 * @version 2019-05-27
 */
public class LazyString {

    private final Supplier<?> stringSupplier;

    public static LazyString lazy(Supplier<?> stringSupplier) {
        return new LazyString(stringSupplier);
    }

    private LazyString(final Supplier<?> stringSupplier) {
        this.stringSupplier = stringSupplier;
    }

    @Override
    public String toString() {
        return String.valueOf(stringSupplier.get());
    }

    public static void main(String[] args) {
        Integer a = null;
        int b = 0;
        //System.out.println(b == a);
        //a.intValue();
        //System.out.println(null == a);
        System.out.println(a == null);
        //System.out.println(a == b);
        //System.out.println(Optional.ofNullable(a).orElse(-1) == b );

    }
}
