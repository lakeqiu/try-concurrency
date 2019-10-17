package com.lakeqiu.base.juc.executor.charpter1;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lakeqiu
 */
public class ThreadPoolExecutorTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) buildThreadPoolExecutor();

        poolExecutor.execute(() -> sleep(3));
        poolExecutor.execute(() -> sleep(2));
        poolExecutor.execute(() -> sleep(3));


        // ThreadPoolExecutor的关闭， 只是展示一下api而已
        // 关闭线程池，会等待已经提交的任务完成
        // 先打断空闲的线程，等待工作的线程把所有任务完成再来打断
        poolExecutor.shutdown();
        // 在一定时间内没有完成，强制停止
        poolExecutor.awaitTermination(1, TimeUnit.DAYS);
        // 立即返回（打断工作线程），并将没有完成的任务（队列中的）返回
        // 先打断线程，再返回
        List<Runnable> runnables = poolExecutor.shutdownNow();

        int activeCount = -1;
        int queueTask = -1;

        while (true){
            if ((activeCount != poolExecutor.getActiveCount()) || (queueTask != poolExecutor.getQueue().size())){
                System.out.println(poolExecutor.getActiveCount());
                System.out.println(poolExecutor.getCorePoolSize());
                System.out.println(poolExecutor.getQueue().size());
                System.out.println(poolExecutor.getMaximumPoolSize());
                activeCount = poolExecutor.getActiveCount();
                queueTask = poolExecutor.getQueue().size();
                System.out.println("================================");
            }
        }

    }

    /**
     * int corePoolSize, 线程核心数（即平常常存的线程数）
     * int maximumPoolSize, 最大线程数
     * long keepAliveTime, 保活时间，即如果在线程数大于核心数，其有线程处于空闲的情况下，将对线程回收到核心数状态
     * TimeUnit unit, keepAliveTime的单位
     * BlockingQueue<Runnable> workQueue, 用来保存等待执行的任务的阻塞队列
     * ThreadFactory threadFactory, 用于设置创建线程的工厂
     * RejectedExecutionHandler handler 拒绝策略
     * @return
     */
    private static ExecutorService buildThreadPoolExecutor(){
        ExecutorService service = new ThreadPoolExecutor(1, 2, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        },
                new ThreadPoolExecutor.AbortPolicy());

        System.out.println("======线程池初始化完毕======");

        return service;
    }


    private static void sleep(long time){
        try {
            System.out.println(Thread.currentThread().getName() + " --> 执行任务");
            TimeUnit.SECONDS.sleep(time);
            System.out.println(Thread.currentThread().getName() + " --> 完成任务");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
