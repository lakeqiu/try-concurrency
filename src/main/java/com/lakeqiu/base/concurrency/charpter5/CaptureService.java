package com.lakeqiu.base.concurrency.charpter5;

import java.util.*;

/**
 * 综合案例：
 *  假如有多个主机上的数据需要采集，我们应该怎么使用多线程去采集呢？
 *
 *  分析：因为主机不确定，所以我们不能采用一个线程采集一个主机的做法。因为如果主机有1000台，
 *  那岂不是要开启1000个线程同时去采集，这么做程序早就爆了。
 *
 *  所以，我们采用的方式是：先创建所有线程，然后设置一个限定，同一时间最多5个线程，等这个线程结束工作后，其他线程才可以开始工作
 *  将同一时间工作的线程控制在一定数量。
 * @author lakeqiu
 */
public class CaptureService {
    /**
     * 存放监视器，一个监视器代表一个线程
     *
     * 最好是存放线程，这里这么写是为了省事
     */
    private final static LinkedList<Control> CONTROLS = new LinkedList<>();

    /**
     * 同一时间最大允许工作线程数量
     */
    private final static int MAX_WORKER = 5;

    public static void main(String[] args) {
        // 存放线程以便遍历使用join方法，下面创建线程采用stream，而stream使用一次后不能用了，
        // 所以需要存起来
        List<Thread> worker = new ArrayList<>();

        Arrays.asList("M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9", "M10").stream()
                .map(CaptureService::createCaptureThread)
                .forEach(t -> {
                    t.start();
                    worker.add(t);
                });

        worker.stream().forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Optional.of("数据采集工作已经全部完成")
                .ifPresent(System.out::println);
    }


    /**
     * 这个方法的精髓在于：
     *  因为每个线程采集的数据主机是不一样的，所以创建一个监视器当锁，当需要
     *  控制线程数量时才使用synchronize，业务代码不上锁，效率高。
     *
     *  记住，多线程尽量减少锁住的代码
     * @param name 线程名字
     * @return 一个还没执行的线程
     */
    private static Thread createCaptureThread(String name){
        return new Thread(() -> {
            Optional.of("线程[" + Thread.currentThread().getName() + "]准备就绪，即将开始工作")
                    .ifPresent(System.out::println);

            synchronized (CONTROLS){
                // 检查工作中的线程是否大于等于最大允许同时工作线程
                while (CONTROLS.size() >= MAX_WORKER){
                    // 已经大于了
                    try {
                        // 进入等待，等其他线程工作完成结束通知吧
                        CONTROLS.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // 工作中的线程小于最大允许同时工作线程，先把自己加入工作线程中
                CONTROLS.add(new Control());
            }
            // 业务逻辑代码（放外面提高效率，如果放同步代码块就变单线程了，还有什么意义？）
            Optional.of("线程[" + Thread.currentThread().getName() + "]采集数据中。。。")
                    .ifPresent(System.out::println);

            try {
                // 睡眠，模拟采集数据
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 工作结束
            // 每次操作共享变量，都必须上锁
            synchronized (CONTROLS){
                Optional.of("线程[" + Thread.currentThread().getName() + "]采集数据完成。")
                        .ifPresent(System.out::println);
                // 将自己从工作线程中剔除
                CONTROLS.removeFirst();
                // 唤醒其他还在等待工作的线程
                CONTROLS.notifyAll();
            }
        }, name);
    }


    /**
     * 作为一个辅助控制同一时间线程数量（锁住这个对象）
     * 监视器
     */
    private static class Control{

    }
}
