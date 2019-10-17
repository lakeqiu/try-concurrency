package com.lakeqiu.base.juc.executor.charpter2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author lakeqiu
 */
public class ExecutorTest {
    public static void main(String[] args) {
        useCacheThreadPool();
    }

    /**
     * 使用CacheThreadPool
     * 进去这个方法，可以看到
     * 这个线程池的核心线程为0，其队列为SynchronousQueue（不存放任何一个任务）。
     * 故这个线程池每来一个任务会就创建一个线程去执行任务。空闲的线程超过60秒就会被清除。
     */
    private static void useCacheThreadPool(){
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        IntStream.rangeClosed(1, 5).forEach(i -> cachedThreadPool.submit(() -> {
            System.out.println(Thread.currentThread().getName() + " --> " + i);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }

    /**
     * 创建一个固定大小的线程池，该方法可指定线程池的固定大小，对于超出的线程会在`LinkedBlockingQueue`队列中等待。 也不会去销毁线程。
     */
    private static void useFixedThreadPool(){
        ExecutorService service = Executors.newFixedThreadPool(10);
    }

    private static void useScheduledThreadPool(){
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(10);
    }


    private static void useSingleThreadPool(){
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    }
    
    private static void useWorkStealingPool(){
        ExecutorService service = Executors.newWorkStealingPool();

    }
}
