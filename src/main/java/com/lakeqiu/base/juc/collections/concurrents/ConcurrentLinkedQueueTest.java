package com.lakeqiu.base.juc.collections.concurrents;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.IntStream;

/**
 * @author lakeqiu
 */
public class ConcurrentLinkedQueueTest {
    public static void main(String[] args) {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();

        IntStream.range(0, 99999).forEach(queue::offer);

        long start = System.currentTimeMillis();

        while (!queue.isEmpty()){
            queue.poll();
        }

        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }
}
