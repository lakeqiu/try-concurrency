package com.lakeqiu.base.juc.utils.charpter10;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author lakeqiu
 */
public class PhaserTest {
    final static Phaser PHASER = new Phaser(3);


    public static void main(String[] args) {
        Task task = new Task(PHASER);
        IntStream.rangeClosed(1, 3).forEach(i -> new Thread(task).start());
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
                System.out.println(Thread.currentThread().getName() + " --> 完成任务，等待其他线程");
                TimeUnit.SECONDS.sleep(random.nextInt(10));
                // 等待其他任务
                phaser.arriveAndAwaitAdvance();
                System.out.println(Thread.currentThread().getName() + " --> 继续执行任务");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
