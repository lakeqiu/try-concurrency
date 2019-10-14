package com.lakeqiu.base.juc.utils.charpter7;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * 生产者-消费者模式
 * @author lakeqiu
 */
public class ConditionTest {
    private final static ReentrantLock lock = new ReentrantLock();
    private final static Condition CONDITION = lock.newCondition();

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
                CONDITION.await();
            }
            // 可以生产
            System.out.println(Thread.currentThread().getName() + " 生产者 --> " + ++data);
            flag.set(false);
            // 通知消费者可以消费了
            CONDITION.signalAll();
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
                CONDITION.await();
            }

            System.out.println(Thread.currentThread().getName() + " 消费者 --> " + data);
            flag.set(true);
            CONDITION.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
