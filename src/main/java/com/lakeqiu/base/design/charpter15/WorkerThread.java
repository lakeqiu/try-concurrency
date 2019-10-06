package com.lakeqiu.base.design.charpter15;

/**
 * 工作线程，从流水线中取出请求
 * @author lakeqiu
 */
public class WorkerThread extends Thread {
    private final Channel channel;

    public WorkerThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        while (true){
            // 取出任务并执行
            channel.take().execute();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
