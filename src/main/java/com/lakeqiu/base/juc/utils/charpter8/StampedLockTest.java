package com.lakeqiu.base.juc.utils.charpter8;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * StampedLock例子
 * @author lakeqiu
 */
public class StampedLockTest {
    private final static StampedLock LOCK = new StampedLock();

    private final static List<Long> DATA = new ArrayList<>();

    private final static ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws InterruptedException {
        Runnable readTask = () -> {
            while (true){
                read();
            }
        };

        Runnable writeTask = () -> {
            while (true){
                write();
            }
        };

        threadPool.submit(writeTask);

        TimeUnit.SECONDS.sleep(1);
        IntStream.rangeClosed(1, 9).forEach((IntConsumer) threadPool.submit(readTask));


    }

    public static void read(){
        long stamped = LOCK.readLock();
        try {
            Optional.of(
                    // 转化为字符串并拼接
                    DATA.stream().map(String::valueOf).collect(Collectors.joining("，", "R-", ""))
            ).ifPresent(System.out::println);


            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlockRead(stamped);
        }
    }

    public static void write(){
        long stamped = LOCK.writeLock();
        try {
            DATA.add(System.currentTimeMillis());

            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            LOCK.unlockWrite(stamped);
        }
    }
}
