package com.lakeqiu.base.juc.utils.charpter10;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * arrive()方法可以有以下使用场景，假如主线程开启子线程后，每个子线程有两个阶段的任务，
 * 而主线程只需要子线程完成第一阶段的任务即可进行执行下去，那么，怎么设计子线程完成第一阶段后不停继续第二阶段的任务？
 * @author lakeqiu
 */
public class PhaserTest4 {
    private static final Phaser PHASE = new Phaser(4);

    public static void main(String[] args) throws InterruptedException {
        Task task = new Task(PHASE);
        IntStream.rangeClosed(1, 3).forEach(i -> new Thread(task).start());

        PHASE.arriveAndAwaitAdvance();
        System.out.println(Thread.currentThread().getName() + " --> 子线程初始化完毕，继续执行");
        TimeUnit.SECONDS.sleep(1);
        System.out.println(Thread.currentThread().getName() + " --> 完成");
    }

    static class Task implements Runnable{
        private Phaser phaser;
        private Random random = new Random(System.currentTimeMillis());

        public Task(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " --> 执行第一阶段任务");
                TimeUnit.SECONDS.sleep(random.nextInt(2));
                System.out.println(Thread.currentThread().getName() + " --> 完成第一阶段任务");
                phaser.arrive();


                System.out.println(Thread.currentThread().getName() + " --> 执行第二阶段任务");
                TimeUnit.SECONDS.sleep(random.nextInt(2));
                System.out.println(Thread.currentThread().getName() + " --> 完成第二阶段任务");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
