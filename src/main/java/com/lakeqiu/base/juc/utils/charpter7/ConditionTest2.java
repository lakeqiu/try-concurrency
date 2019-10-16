package com.lakeqiu.base.juc.utils.charpter7;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * 生产者-消费者模式
 *  使用Condition可以分出两个锁，这个唤醒时就是生产者唤醒生产者了
 * @author lakeqiu
 */
public class ConditionTest2 {
    private final static ReentrantLock lock = new ReentrantLock();
    /**
     * 生产者、消费者锁
     */
    private final static Condition PRODUCE_LOCK = lock.newCondition();
    private final static Condition CONSUME_LOCK = lock.newCondition();

    /**
     * 产品，是否可以生产
     */
    private static int data = 0;
    private static AtomicBoolean flag = new AtomicBoolean(true);

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 5).forEach(i -> new Thread(() -> {
            while (true){
                produce();
            }
        }).start());
        IntStream.rangeClosed(1, 5).forEach(i -> new Thread(() -> {
            while (true){
                consume();
            }
        }).start());
    }

    private static void produce(){
        lock.lock();
        try {
            while (!flag.get()){
                // 如果不可以生产，进入等待，等待消费者消费完通知
                PRODUCE_LOCK.await();
            }
            // 可以生产
            System.out.println(Thread.currentThread().getName() + " 生产者 --> " + ++data);
            flag.set(false);
            // 通知消费者可以消费了
            CONSUME_LOCK.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private static void consume(){
        lock.lock();
        try {
            while (flag.get()){
                CONSUME_LOCK.await();
            }

            System.out.println(Thread.currentThread().getName() + " 消费者 --> " + data);
            flag.set(true);
            PRODUCE_LOCK.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


}

