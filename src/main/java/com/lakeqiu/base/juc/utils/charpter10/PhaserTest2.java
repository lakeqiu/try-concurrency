package com.lakeqiu.base.juc.utils.charpter10;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 如果主线程需要等子线程将初始化任务执行完才能往下执行，但有一个子线程在执行任务过程中挂了，
 * 怎么设计才能不影响主线程，让主线程执行下去呢？
 * @author lakeqiu
 */
public class PhaserTest2 {
    final static Phaser PHASER = new Phaser(11);


    public static void main(String[] args) throws InterruptedException {
        Task task = new Task(PHASER);
        IntStream.rangeClosed(1, 10).forEach(i -> new Thread(task).start());

        PHASER.arriveAndAwaitAdvance();

        TimeUnit.SECONDS.sleep(1);
        System.out.println(Thread.currentThread().getName() + " --> 子线程任务完成，主线程继续执行");
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
                TimeUnit.SECONDS.sleep(random.nextInt(2));

                boolean b = random.nextBoolean();
                if (b){
                    System.out.println(Thread.currentThread().getName() + " --> 出错");
                    // 出错将自己剔除，即phaser要等待的线程减1
                    phaser.arriveAndDeregister();
                    return;
                }
                // 等待其他任务
                phaser.arriveAndAwaitAdvance();
                System.out.println(Thread.currentThread().getName() + " --> 继续执行任务");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
