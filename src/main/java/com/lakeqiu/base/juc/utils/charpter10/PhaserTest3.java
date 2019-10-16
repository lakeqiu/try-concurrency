package com.lakeqiu.base.juc.utils.charpter10;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author lakeqiu
 */
public class PhaserTest3 {
    private static Phaser phaser = new Phaser(2){
        /**
         * 这个方法每次都会在线程结束屏障后执行，可以用其来判断线程执行有没有出问题
         * @param phase 线程注册数量
         * @param registeredParties 进入该方法的线程数
         * @return true 表示Phaser已经销毁，无用；false，Phaser没有销毁，还有用，
         * 可以使用isTerminated方法查看
         */
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            // 返回true：下面代码一个线程报错时不会陷入假死（phaser已经没有用了）
            // 返回false，会
            return false;
        }
    };

    public static void main(String[] args) {
        Task task = new Task(phaser);
        IntStream.rangeClosed(1, 2).forEach(i -> new Thread(task).start());

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
                phaser.arriveAndAwaitAdvance();

                System.out.println(Thread.currentThread().getName() + " --> 继续执行任务");
                if (random.nextBoolean()){
                    System.out.println("出错");
                    return;
                }
                System.out.println(Thread.currentThread().getName() + " --> 完成任务，等待其他线程");
                phaser.arriveAndAwaitAdvance();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
