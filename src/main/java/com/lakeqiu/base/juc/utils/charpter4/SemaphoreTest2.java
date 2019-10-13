package com.lakeqiu.base.juc.utils.charpter4;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author lakeqiu
 */
public class SemaphoreTest2 {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);

        IntStream.rangeClosed(1, 3).forEach(i -> new Thread(() -> {
                    try {
                        semaphore.tryAcquire();
                        System.out.println(Thread.currentThread().getName() + " --> 获取了锁");
                        TimeUnit.MILLISECONDS.sleep(1000);
                        System.out.println(Thread.currentThread().getName() + " --> 释放了锁");
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start()
        );
    }
}
