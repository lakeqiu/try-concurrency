package com.lakeqiu.base.juc.executor.charpter4;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lakeqiu
 */
public class ScheduledThreadPoolExecutorTest {
    public static void main(String[] args) throws InterruptedException {
//        test1();
//        test2();
        test3();
    }

    /**
     * 简单使用
     */
    private static void test1(){
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        // 两秒后执行任务
        executor.schedule(() -> System.out.println("11111"), 2, TimeUnit.SECONDS);
    }

    /**
     * 定时任务
     */
    private static void test2(){
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        // 一秒以后执行这个任务，每两秒执行一次
        executor.scheduleAtFixedRate(() -> {
            System.out.println("11111");
            try {
                TimeUnit.SECONDS.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1,2, TimeUnit.SECONDS);
    }

    /**
     * 设置定时任务在shutdown后不停止，继续执行
     */
    private static void test3() throws InterruptedException {
        ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(2);
        // 将定时任务设置为线程池shutdown后进行执行
        poolExecutor.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);

        poolExecutor.scheduleAtFixedRate(() -> {
            System.out.println("11111");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1,1, TimeUnit.SECONDS);

       TimeUnit.SECONDS.sleep(2);
       poolExecutor.shutdown();
        System.out.println("结束");
    }
}
