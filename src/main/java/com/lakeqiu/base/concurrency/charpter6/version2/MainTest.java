package com.lakeqiu.base.concurrency.charpter6.version2;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * 测试类
 * @author lakeqiu
 */
public class MainTest {
    public static void main(String[] args) throws InterruptedException {
        // 创建锁
        BooleanLock booleanLock = new BooleanLock();

        Stream.of("T1", "T2", "T3", "T4").forEach(name ->
                new Thread(() -> {
                    try {
                        // 当前线程测试加锁
                        booleanLock.lock();
                        Optional.of(Thread.currentThread().getName() + " --> 成功加锁")
                                .ifPresent(System.out::println);

                        // 开始工作
                        work();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        // 放弃锁
                        booleanLock.unLock();
                    }
                }, name).start()
        );

        // 暴露版本1问题代码
        Thread.sleep(100);
        booleanLock.unLock();
    }

    /**
     * 线程的工作业务代码
     * @throws InterruptedException 被打断时抛出的异常
     */
    private static void work() throws InterruptedException {
        Optional.of(Thread.currentThread().getName() + " --> 开始工作")
                .ifPresent(System.out::println);
        Thread.sleep(1000);
    }
}
