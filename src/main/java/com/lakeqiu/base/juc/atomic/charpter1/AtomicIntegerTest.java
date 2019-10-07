package com.lakeqiu.base.juc.atomic.charpter1;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lakeqiu
 */
public class AtomicIntegerTest {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(10);

        // 旧的期望值为12，期望更新为20
        // 肯定更新失败，因为内存值为10，旧的期望值不符合，返回false
        boolean b = atomicInteger.compareAndSet(12, 20);
        System.out.println(atomicInteger.get());

        // 这个旧的期望值符合，更新成功
        // 返回true
        boolean b1 = atomicInteger.compareAndSet(10, 20);
        System.out.println(atomicInteger.get());
    }

}
