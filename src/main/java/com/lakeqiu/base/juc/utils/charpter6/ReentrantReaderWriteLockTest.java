package com.lakeqiu.base.juc.utils.charpter6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

/**
 * @author lakeqiu
 */
public class ReentrantReaderWriteLockTest {
    /**
     * 获取读写锁
     */
    static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    static Lock rl = lock.readLock();
    static Lock wl = lock.writeLock();

    static List<String> list = new ArrayList<>();
    static int i = 1;

    public static void main(String[] args) throws InterruptedException {
        IntStream.rangeClosed(1, 3).forEach(i -> new Thread(() -> write()).start());
        IntStream.rangeClosed(1, 5).forEach(i -> new Thread(() -> read()).start());
        TimeUnit.MILLISECONDS.sleep(10000);
    }

    public static void read(){
        rl.lock();
        System.out.println(Thread.currentThread().getName() + " --> 读取");
        System.out.println(list);
        rl.unlock();
    }

    public static void write(){
        wl.lock();
        System.out.println(Thread.currentThread().getName() + " --> 写入" + i);
        list.add("写入" + i++);
        wl.unlock();
    }
}
