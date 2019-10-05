package com.lakeqiu.base.design.charpter13.version2;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Per是“每一”的意思，所以thread per message解释过来就是“每个消息一个线程”，message在这里可以看做是“命令”或“请求”的意思，
 * 对每隔命令或请求，分配一个线程，有这个线程执行。
 *
 * 使用thread-pre-message模式时，“委托消息的一端”与“执行消息的一端”会是不同的线程，也就像是委托消息的线程，
 * 对执行消息的线程说“这个任务交给你了”。
 *
 * 通俗来讲就是每来一个任务就创建一个线程去执行（web中处理请求就是这样）
 *
 * 线程池改造版本
 * @author lakeqiu
 */
public class MessageHandler {
    /**
     * 创建一个线程池，里面有5个线程
     */
    private final static Executor EXECUTOR = Executors.newFixedThreadPool(5);

    public void request(Message message){
        // 将任务放进去
        EXECUTOR.execute(() -> {
            System.out.println(Thread.currentThread().getName() + "接收到任务 --> " + message.getValue());
            System.out.println(Thread.currentThread().getName() + " --> " + "任务开始了");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " --> " + "任务结束了");
        });
    }

    public void shutdown(){
        ((ExecutorService)EXECUTOR).shutdown();
    }
}
