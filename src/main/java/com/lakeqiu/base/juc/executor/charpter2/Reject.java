package com.lakeqiu.base.juc.executor.charpter2;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * 拒绝策略
 * @author lakeqiu
 */
public class Reject {
    public static void main(String[] args) throws InterruptedException {
//        abortPolicy();
//        discardPolicy();
//        callerRunPolicy();
        discardOldestPolicy();
    }

    /**
     * 直接丢弃
     * @throws InterruptedException
     */
    private static void discardPolicy() throws InterruptedException {
        ExecutorService service = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), r -> new Thread(r), new ThreadPoolExecutor.DiscardPolicy());

        IntStream.rangeClosed(1, 2).forEach(i -> service.submit(() -> {
            System.out.println(i + " --> 执行任务");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        TimeUnit.SECONDS.sleep(1);
        service.submit(() -> System.out.println(Thread.currentThread().getName() +  " --> mian"));
    }

    /**
     * 抛出异常
     * @throws InterruptedException
     */
    private static void abortPolicy() throws InterruptedException {
        ExecutorService service = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), r -> new Thread(r), new ThreadPoolExecutor.AbortPolicy());

        IntStream.rangeClosed(1, 2).forEach(i -> service.submit(() -> {
            System.out.println(i + " --> 执行任务");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        TimeUnit.SECONDS.sleep(1);
        service.submit(() -> System.out.println(Thread.currentThread().getName() +  " --> mian"));
    }

    /**
     * 调用者执行
     * @throws InterruptedException
     */
    private static void callerRunPolicy() throws InterruptedException {
        ExecutorService service = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), r -> new Thread(r), new ThreadPoolExecutor.CallerRunsPolicy());

        IntStream.rangeClosed(1, 2).forEach(i -> service.submit(() -> {
            System.out.println(i + " --> 执行任务");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        TimeUnit.SECONDS.sleep(1);
        service.submit(() -> System.out.println(Thread.currentThread().getName() +  " --> mian"));
    }

    /**
     * 抛弃队首任务，接收新任务
     * @throws InterruptedException
     */
    private static void discardOldestPolicy() throws InterruptedException {
        ExecutorService service = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), r -> new Thread(r), new ThreadPoolExecutor.DiscardOldestPolicy());

        IntStream.rangeClosed(1, 2).forEach(i -> service.submit(() -> {
            System.out.println(i + " --> 执行任务");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        TimeUnit.SECONDS.sleep(1);
        service.submit(() -> System.out.println(Thread.currentThread().getName() +  " --> mian"));
    }
}
