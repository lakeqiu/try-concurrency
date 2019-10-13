package com.lakeqiu.base.juc.utils.charpter2;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @author lakeqiu
 */
public class CyclicBarrierTest1 {
    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
            @Override
            public void run() {
                System.out.println("所有任务已经完成");
            }
        });

        new Thread(() -> {
            try {
                Thread.sleep(100);
                System.out.println("T1任务完成");
                cyclicBarrier.await();
                System.out.println("T1等待结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("T2任务完成");
                cyclicBarrier.await();
                System.out.println("T2等待结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(500);
        System.out.println("1111111");
        cyclicBarrier.reset();
    }
}
