package com.lakeqiu.base.concurrency.charpter5;

import java.util.stream.Stream;

/**
 * 生产者消费者模式
 *  生产者生产完东西，消费者就消费掉
 *
 *  版本1
 *
 *  存在问题：当有多个生产者消费者时会假死（不输出了，但程序不会停），而且并没有产生死锁
 * @author lakeqiu
 */
public class ProduceConsumerV1 {
    public static void main(String[] args) {
        ProduceConsumerV1 v1 = new ProduceConsumerV1();

        /*new Thread(){
            @Override
            public void run() {
                while (true){
                    v1.produce();
                }
            }
        }.start();


        new Thread(){
            @Override
            public void run() {
                while (true){
                    v1.consume();
                }
            }
        }.start();*/

        // 两个生产者
        Stream.of("P1", "P2", "P3").forEach(n ->
                new Thread(){
                    @Override
                    public void run() {
                        while (true){
                            v1.produce();
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
                            v1.consume();
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
    private final Object LOCK = new Object();

    /**
     * 生产者
     */
    public void produce(){
        synchronized (LOCK){
            // 判断是否可以生产
            // 生产的已经被消耗了，需要生产
            if (isProduced){
                System.out.println(Thread.currentThread().getName() + "生产了-->" + (++index));

                // 唤醒消费者
                LOCK.notifyAll();

                // 将标志位置为false，意思是可以消费(这样也可以防止生产者再次进行生产)
                isProduced = false;
            }else { // 生产出来的还没有被消费掉，不可以生产
                try {
                    // 进入等待，等待消费者通知可以生产了
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 消费者
     */
    public void consume(){
        synchronized (LOCK){
            // 没有可以消费的，等待生产者生产
            if (isProduced){
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else { // 有可以消费的，可以消费了
                System.out.println(Thread.currentThread().getName() + "消费了-->" + index);

                // 唤醒生产者可以继承生产了
                LOCK.notifyAll();

                // 将标志位设置为true，意为可生产
                isProduced = true;
            }
        }
    }
}
