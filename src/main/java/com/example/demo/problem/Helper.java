package com.example.demo.problem;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.*;

/**
 * @author duanxiaoduan
 * @version 2019/11/20
 */
public enum Helper {

    /**
     * instance
     */
    instance;

    static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 2, 1L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());

    public static void print(String... input) {
        if (null == input) {
            return;
        }
        for (String string : input) {
            System.out.print(string);
        }
    }

    public void run(Runnable runnable) {
        threadPool.execute(runnable);
    }

    public void shutdown() {
        threadPool.shutdown();
    }

    @NotNull
    @Contract(pure = true)
    public static String[] buildNoArray(final int max) {
        String [] noArray = new String[max];
        for (int i = 0; i <max; i++) {
            noArray[i] = Integer.toString(i + 1);
        }
        return noArray;
    }

    public static String[] buildCharArray(final int max) {
        int temp = 65;
        String [] charArray = new String[max];
        for (int i = 0; i <max; i++) {
            charArray[i] = String.valueOf((char)(temp + i));
        }
        return charArray;
    }
}
