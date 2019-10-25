package com.lakeqiu.base.juc.collections.concurrents;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.IntStream;

/**
 * @author lakeqiu
 */
public class ConcurrentHashMapTest {
    static HashMap<String, Integer> map = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        concurrentHashMap();
        Thread.currentThread().join();
    }


    public static void hashMap(){


        IntStream.range(0, 9).boxed().forEach(i -> new Thread(() -> {
            for (int j = 0; j < 100; j++){
                map.put(UUID.randomUUID().toString(), i);
            }
        }).start());
    }

    public static void concurrentHashMap(){
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

        IntStream.range(0, 3).boxed().forEach(i -> new Thread(() -> {
            for (int j = 0; j < 100; j++){
                String s = UUID.randomUUID().toString();
                try {
                    System.out.println(s);
                    queue.put(s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                map.put(s, i);
            }
        }).start());


        IntStream.range(0, 9).boxed().forEach(i -> new Thread(() -> {
            for (int j = 0; j < 100; j++){
                try {
                    System.out.println(map.get(queue.take()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start());
    }
}
