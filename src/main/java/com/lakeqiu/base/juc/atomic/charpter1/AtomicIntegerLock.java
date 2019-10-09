package com.lakeqiu.base.juc.atomic.charpter1;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 利用Atomic来创建锁
 *  获取失败的话直接结束线程
 *
 * 这个的原理是通过获取到锁的线程通过compareAndSet改掉值，而后面的线程获取值时旧的期望会不符合返回false
 * @author lakeqiu
 */
public class AtomicIntegerLock {
    public static void main(String[] args) {
        AtomicIntegerLock lock = new AtomicIntegerLock();


        new Thread(() -> {
            lock.tryLock();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unLock();
        }).start();


        new Thread(() -> {
            lock.tryLock();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unLock();
        }).start();
    }

    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    private final ThreadLocal<Thread> threadLocal = new ThreadLocal<>();

    /**
     * 获取锁
     */
    public void tryLock(){
        // 将值加1
        boolean success = atomicInteger.compareAndSet(0, 1);

        threadLocal.set(Thread.currentThread());
        // success为false说明atomicInteger已经被线程改过，有线程获取到锁了
        if (!success){
            throw new RuntimeException("获取锁失败");
        }
    }

    public void unLock(){
        if (threadLocal.get() != Thread.currentThread()){
            return;
        }

        // 已经释放过锁了，不需要释放了
        if (0 == atomicInteger.get()){
            return;
        }

        // 释放锁
        atomicInteger.compareAndSet(1, 0);
    }
}
