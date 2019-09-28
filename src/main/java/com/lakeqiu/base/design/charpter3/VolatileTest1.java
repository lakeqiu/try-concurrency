package com.lakeqiu.base.design.charpter3;

/**
 * 对INIT_VALUE加上Volatile关键字和去掉关键字运行一遍看下结果就清楚了
 * @author lakeqiu
 */
public class VolatileTest1 {

    private volatile static int INIT_VALUE = 0;

    private final static int MAX_VALUE = 10;

    public static void main(String[] args) {
        // 阅读者，负责读取值
        new Thread(() -> {
            // 这里java会帮我们优化，jvm认为这里面只有读操作，没有写的操作，故认为
            // 把值更新一次到cache中即可，没必要再去主内存中拿东西了也导致了
            int localValue = INIT_VALUE;
            while (INIT_VALUE < MAX_VALUE){
                if (localValue != INIT_VALUE){
                    System.out.println(Thread.currentThread().getName() + "-->INIT_VALUE被更新了：" + INIT_VALUE);
                    localValue = INIT_VALUE;
                }
            }
        }, "阅读者").start();

        // 更新者，负责更新值
        new Thread(() -> {
            int localValue = INIT_VALUE;
            while (INIT_VALUE < MAX_VALUE){
                System.out.println(Thread.currentThread().getName() + "-->更新了INIT_VALUE：" + (++INIT_VALUE));
                localValue = INIT_VALUE;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "更新者").start();
    }
}
