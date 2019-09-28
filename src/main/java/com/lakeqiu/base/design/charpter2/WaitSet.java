package com.lakeqiu.base.design.charpter2;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * wait方法后
 *  1、怎么唤醒
 *  2、去哪里唤醒
 *  3、唤醒后怎么出来
 * @author lakeqiu
 */
public class WaitSet {


    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        IntStream.rangeClosed(1, 10).forEach( i -> new Thread(String.valueOf(i)){
            @Override
            public void run() {
                synchronized (LOCK){
                    try {
                        Optional.of(Thread.currentThread().getName() + "进入休息室").ifPresent(System.out::println);
                        LOCK.wait();
                        Optional.of(Thread.currentThread().getName() + "走出休息室").ifPresent(System.out::println);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start());

        // 唤醒
        IntStream.rangeClosed(1, 10).forEach(i -> {
            synchronized (LOCK){
                LOCK.notify();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
