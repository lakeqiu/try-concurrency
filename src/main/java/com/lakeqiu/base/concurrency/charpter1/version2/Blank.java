package com.lakeqiu.base.concurrency.charpter1.version2;

import java.util.Arrays;

/**
 * 采用多线程方式模拟银行排队叫号
 * 版本2
 * @author lakeqiu
 */
public class Blank {
    public static void main(String[] args) {
        TicketWindowsRunnable runnable = new TicketWindowsRunnable();

        new Thread(runnable, "柜台1").start();
        new Thread(runnable, "柜台2").start();
        new Thread(runnable, "柜台3").start();
       /* Thread thread = new Thread(runnable);
        thread.start();
        ThreadGroup threadGroup = thread.getThreadGroup();
        System.out.println(threadGroup.activeCount());
        // 获取当前活动线程
        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);
        // 遍历
        for (Thread thread1 : threads) {
            System.out.println(thread1);
        }
        // 遍历也可以这样写
        Arrays.asList(threads).forEach(System.out::println);
        // 上面的等于这一句
        Arrays.asList(threads).forEach(a -> System.out.println(a));*/
    }
}
