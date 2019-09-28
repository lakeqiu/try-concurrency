package com.lakeqiu.base.concurrency.charpter8;

import java.util.Arrays;

/**
 * ThreadGroup的使用
 * @author lakeqiu
 */
public class ThreadGroupCreate {
    public static void main(String[] args) {
        // 创建一个ThreadGroup（不指定父ThreadGroup的话，默认当前线程的ThreadGroup）
        // 指定名字为TG1
        ThreadGroup tg1 = new ThreadGroup("TG1");

        // 创建线程并指定其ThreadGroup(不指定ThreadGroup，默认当前线程的ThreadGroup)
        Thread t1 = new Thread(tg1, "t1") {
            @Override
            public void run() {
                // 获取当前线程的ThreadGroup
                System.out.println(Thread.currentThread().getThreadGroup());
                // 获取当前线程组活跃的线程数
                System.out.println(Thread.currentThread().getThreadGroup().activeCount());
                // 获取当前线程组及子线程组的活跃线程数
                System.out.println(Thread.currentThread().getThreadGroup().activeGroupCount());
                // 检查当前线程是否有权限修改ThreadGroup
                Thread.currentThread().getThreadGroup().checkAccess();

                // 获取当前线程的活跃线程
                // ThreadGroup类的enumerate()方法用于将每个活动线程的线程组及其子组复制到指定的数组中。
                // enumerate(threads, true)是连子ThreadGroup也复制，false则不复制（类似于深、浅拷贝）
                Thread[] threads = new Thread[Thread.currentThread().getThreadGroup().activeCount()];
                Thread.enumerate(threads);
                Arrays.asList(threads).stream().forEach(System.out::println);
            }
        };

        t1.start();

        // 创建ThreadGroup，指定父ThreadGroup
        ThreadGroup tg2 = new ThreadGroup(tg1, "TG2");
    }
}
