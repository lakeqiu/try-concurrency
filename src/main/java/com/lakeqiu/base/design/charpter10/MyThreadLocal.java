package com.lakeqiu.base.design.charpter10;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义自己的ThreadLocal
 * @author lakeqiu
 */
public class MyThreadLocal<T> {
    /**
     * 当前线程的值作为map的key
     */
    private final Map<Thread, T> storage = new HashMap<>();

    public void set(T t){
        synchronized (this){
            // 获取当前线程
            Thread thread = Thread.currentThread();
            // 存进map中
            storage.put(thread, t);
        }
    }

    public T get(){
        synchronized (this){
            // 获取当前线程作为key
            Thread key = Thread.currentThread();

            T t = storage.get(key);
            // 检查t是否为空
            if (null == t){
                // 为空调用initialValue方法
                return initialValue();
            }
            // 不为空，返回
            return t;
        }
    }

    public T initialValue(){
        return null;
    }
}
