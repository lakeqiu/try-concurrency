package com.lakeqiu.base.juc.utils.charpter4;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Semaphore当锁用
 * @author lakeqiu
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        SemaphoreLock lock = new SemaphoreLock();

        IntStream.rangeClosed(1, 3).forEach(i -> new Thread(() -> {
                    try {
                        lock.lock();
                        System.out.println(Thread.currentThread().getName() + " --> 获取了锁");
                        TimeUnit.MILLISECONDS.sleep(1000);
                        System.out.println(Thread.currentThread().getName() + " --> 释放了锁");
                        lock.unlock();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start()
        );

    }

    static class SemaphoreLock{
        /**
         * 限流一个
         */
        private final Semaphore semaphore = new Semaphore(1);

        public void lock() throws InterruptedException {
            semaphore.acquire();
        }

        public void unlock(){
            semaphore.release();
        }
    }
}
