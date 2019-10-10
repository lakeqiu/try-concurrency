package com.lakeqiu.base.juc.utils.charpter1;

import java.util.concurrent.CountDownLatch;

/**
 * @author lakeqiu
 */
public class CountDownLatchTest2 {
    private static final CountDownLatch LATCH = new CountDownLatch(1);

    public static void main(String[] args) {


        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " --> 准备数据中");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " --> 数据已经准备好");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                LATCH.countDown();
            }
        }).start();

        new Thread(() -> {
            try {
                LATCH.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " --> 开始处理数据");
        }).start();

        new Thread(() -> {
            try {
                LATCH.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " --> 开始处理数据");
        }).start();
    }
}
