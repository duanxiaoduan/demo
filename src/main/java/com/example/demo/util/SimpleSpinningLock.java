package com.example.demo.util;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author duanxiaoduan
 * @version 2020/1/3
 */
public class SimpleSpinningLock {

    private AtomicReference ref = new AtomicReference();

    public void lock()  {
        Thread currentThread = Thread.currentThread();
        while (ref.compareAndSet(null, currentThread)) {

        }
    }

    public void unlock()  {
        ref.compareAndSet(this, null);
    }

    public static void main(String[] args) {
        SimpleSpinningLock simpleSpinningLock = new SimpleSpinningLock();

    }
}
