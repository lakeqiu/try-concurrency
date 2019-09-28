package com.lakeqiu.base.concurrency.charpter5;

import java.util.stream.Stream;

/**
 * 生产者消费者模式
 *  生产者生产完东西，消费者就消费掉
 *
 *  版本2
 *
 *  改造生产消费方法
 * @author lakeqiu
 */
public class ProduceConsumerV2 {
    public static void main(String[] args) {
        ProduceConsumerV2 v2 = new ProduceConsumerV2();

        /*new Thread(){
            @Override
            public void run() {
                while (true){
                    v2.produce();
                }
            }
        }.start();


        new Thread(){
            @Override
            public void run() {
                while (true){
                    v2.consume();
                }
            }
        }.start();*/

        // 两个生产者
        Stream.of("P1", "P2", "P3").forEach(n ->
                new Thread(){
                    @Override
                    public void run() {
                        while (true){
                            v2.produce();
                        }
                    }
                }.start()
        );
        // 两个消费者
        Stream.of("C1", "C2", "C3").forEach(n ->
                new Thread(){
                    @Override
                    public void run() {
                        while (true){
                            v2.consume();
                        }
                    }
                }.start()
        );
    }

    /**
     * 生产消费的东西
     */
    private int index = 0;

    /**
     * 标志位
     * 用来判断生产者是否可以生产
     */
    private boolean isProduced = true;

    /**
     * 用来辅助锁住代码块
     */
    private final static Object LOCK = new Object();

    /**
     * 生产者
     */
    public void produce(){
        synchronized (LOCK){

            // 判断是否可以生产
            while (!isProduced){
                try {
                    // 进入等待,还不可以生产
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 生产
            System.out.println(Thread.currentThread().getName() + "生产了-->" + (++index));

            // 唤醒消费者
            LOCK.notifyAll();

            // 将标志位置为false，意思是可以消费(这样也可以防止生产者再次进行生产)
            isProduced = false;
        }
    }

    /**
     * 消费者
     */
    public void consume(){
        synchronized (LOCK){
            // 没有可以消费的，等待生产者生产
            while (isProduced){
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 消费
            System.out.println(Thread.currentThread().getName() + "消费了-->" + index);

            // 唤醒生产者可以继续生产了
            LOCK.notifyAll();

            // 将标志位设置为true，意为可生产
            isProduced = true;
        }
    }
}
