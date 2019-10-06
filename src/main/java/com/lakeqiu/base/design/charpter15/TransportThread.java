package com.lakeqiu.base.design.charpter15;

/**
 * 搬运工
 * 调用流水线功能往流水线添加请求
 * @author lakeqiu
 */
public class TransportThread extends Thread {
    /**
     * 流水线
     */
    private final Channel channel;

    public TransportThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    /**
     * 添加请求
     */
    @Override
    public void run() {
        int i = 0;
        while (true){
            // 添加任务
            channel.put(new Request(getName(), i++));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
