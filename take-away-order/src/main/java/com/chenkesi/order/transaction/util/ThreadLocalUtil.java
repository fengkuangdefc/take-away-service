package com.chenkesi.order.transaction.util;

public class ThreadLocalUtil {

    private static final ThreadLocal<Boolean> threadLocal = new ThreadLocal<>();

    public static Boolean get(){
        return threadLocal.get();
    }

    public static void set(Boolean value){
        threadLocal.set(value);
    }

    public static void remove(){
        threadLocal.remove();
    }
}
