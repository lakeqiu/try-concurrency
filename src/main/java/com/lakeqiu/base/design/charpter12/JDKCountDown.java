package com.lakeqiu.base.design.charpter12;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 * jdk自带的CountDown示例
 * Count-Down设计模式其实也叫做Latch(阀门)设计模式。当若干个线程并发执行完某个特定的任务，然后等到所有的子任务都执行结束之后再统一汇总。
 *
 * 简单来说就是等待线程任务执行往后再执行，之前我们用的是join方法
 * @author lakeqiu
 */
public class JDKCountDown {

    public static void main(String[] args) throws InterruptedException {
        // 数字必须与线程个数对应
        CountDownLatch countDownLatch = new CountDownLatch(5);

        System.out.println("第一阶段开始执行");
        IntStream.rangeClosed(1, 5).forEach(i ->
                new Thread(() -> {
                    System.out.println(Thread.currentThread().getName() +  " --> " + i);
                    try {
                        // 模拟线程在工作
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 让调用await()方法的线程等待这个线程结束
                    countDownLatch.countDown();
                },String.valueOf(i)).start()
        );

        // 等待线程结束
        countDownLatch.await();
        System.out.println("第二阶段开始执行");
        System.out.println("。。。。。。。。");
        System.out.println("第二阶段执行结束");
    }
}
