package com.lakeqiu.base.concurrency.charpter9.version2;

import java.util.stream.IntStream;

/**
 * @author lakeqiu
 */
public class MainTest {
    public static void main(String[] args) throws InterruptedException {
        MyThreadPool threadPool = new MyThreadPool();
        // 创建40个任务
        IntStream.rangeClosed(0, 40).forEach(i -> {
            threadPool.submit(() -> {
                System.out.println("任务" + i + "由[" + Thread.currentThread().getName() + "]启动");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("任务" + i + "由[" + Thread.currentThread().getName() + "]完成");
            });
        });

        threadPool.shutdown();

        threadPool.submit(() -> {
            System.out.println("111");
        });
    }
}
