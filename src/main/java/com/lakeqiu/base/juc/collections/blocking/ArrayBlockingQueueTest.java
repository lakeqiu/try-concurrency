package com.lakeqiu.base.juc.collections.blocking;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.IntStream;

/**
 * @author lakeqiu
 */
public class ArrayBlockingQueueTest {
    // 创建容量为5的非公平阻塞队列
    // 默认策略为抛出异常
    ArrayBlockingQueue queue = new ArrayBlockingQueue<Integer>(5);
    public static void main(String[] args) {




    }

    /**
     * 其实就是调用offer实现的
     */
    void add(){
        IntStream.range(0, 5).forEach(queue::offer);
        System.out.println("-----");
        // 队列已满，会抛出异常
        queue.add(5);
        System.out.println("+++++");
    }

    void put(){
        IntStream.range(0, 5).forEach(i -> {
            try {
                queue.put(i);
                System.out.println("-----");
                // 队列已满，会阻塞住
                queue.put(5);
                System.out.println("+++++");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }
}
