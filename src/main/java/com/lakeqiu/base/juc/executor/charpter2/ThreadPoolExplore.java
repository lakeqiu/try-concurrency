package com.lakeqiu.base.juc.executor.charpter2;

import sun.plugin2.main.server.WindowsHelper;

import java.util.concurrent.*;

/**
 * @author lakeqiu
 */
public class ThreadPoolExplore {
    public static void main(String[] args) {
        test2();
    }

    private static void test1() {
        ThreadPoolExecutor service = new ThreadPoolExecutor(3, 5, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), r -> new Thread(r), new ThreadPoolExecutor.AbortPolicy());
        System.out.println(service.getPoolSize());
        service.submit(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        while (true){
            System.out.println(service.getPoolSize());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 怎么关闭常驻线程
     */
    private static void test2(){
        ThreadPoolExecutor service = new ThreadPoolExecutor(3, 5, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), r -> new Thread(r), new ThreadPoolExecutor.AbortPolicy());
        System.out.println(service.getPoolSize());

        service.prestartAllCoreThreads();


        // 让线程池可以回收常驻线程
        service.allowCoreThreadTimeOut(true);

        service.submit(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        while (service.getPoolSize() != 0){
            System.out.println(service.getPoolSize());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        service.submit(() -> System.out.println("1234"));
    }
}
