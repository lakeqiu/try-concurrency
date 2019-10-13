package com.lakeqiu.base.juc.utils.charpter5;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @author lakeqiu
 */
public class ReentrantLockTest {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();


        IntStream.rangeClosed(1, 3).forEach(i -> new Thread(() -> {
            boolean b = false;
            try {
                // 尝试获取锁，1秒后放弃获取锁
                b = lock.tryLock(1000L, TimeUnit.MILLISECONDS);
                if (b){
                    System.out.println(i + " --> 获取到了锁");
                    TimeUnit.MILLISECONDS.sleep(1000);
                }else {
                    System.out.println(i + " --> 不拿了");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                if (lock.isHeldByCurrentThread()){
                    lock.unlock();
                }
            }
        }).start());
    }
}
