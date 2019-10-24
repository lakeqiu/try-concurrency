package com.lakeqiu.base.juc.collections.blocking;

import java.util.concurrent.LinkedTransferQueue;

/**
 * @author lakeqiu
 */
public class LinkedTransferQueueTest {
    public static void main(String[] args) {
        LinkedTransferQueue<String> transferQueue = new LinkedTransferQueue<>();

        StringBuffer buffer = new StringBuffer("1");

        new Thread(() -> {
            for (int i = 2; i < 20; i++){
                buffer.append(String.valueOf(i));
                System.out.println("生产者 --> " + buffer);
                transferQueue.add(buffer.toString());
            }
        }).start();

        new Thread(() -> {
            while (true){
                try {
                    String take = transferQueue.take();
                    System.out.println("消费者 --> " + take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
