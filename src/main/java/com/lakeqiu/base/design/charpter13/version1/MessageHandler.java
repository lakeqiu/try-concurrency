package com.lakeqiu.base.design.charpter13.version1;

/**
 * Per是“每一”的意思，所以thread per message解释过来就是“每个消息一个线程”，message在这里可以看做是“命令”或“请求”的意思，
 * 对每隔命令或请求，分配一个线程，有这个线程执行。
 *
 * 使用thread-pre-message模式时，“委托消息的一端”与“执行消息的一端”会是不同的线程，也就像是委托消息的线程，
 * 对执行消息的线程说“这个任务交给你了”。
 *
 * 通俗来讲就是每来一个任务就创建一个线程去执行（web中处理请求就是这样）
 *
 * 当时这个版本手动开辟线程太浪费资源了，所以我们使用线程池改造一下
 * @author lakeqiu
 */
public class MessageHandler {
    public void request(Message message){
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "接收到任务 --> " + message.getValue());
            System.out.println(Thread.currentThread().getName() + " --> " + "任务开始了");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " --> " + "任务结束了");
        }).start();
    }
}
