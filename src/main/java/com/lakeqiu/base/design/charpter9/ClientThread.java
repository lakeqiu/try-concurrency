package com.lakeqiu.base.design.charpter9;

/**
 * 客户端，负责将请求装入队列中
 * @author lakeqiu
 */
public class ClientThread extends Thread {

    private final RequestQueue queue;
    /**
     * 要装入队列的信息
     */
    private final String value;

    public ClientThread(RequestQueue queue, String value) {
        this.queue = queue;
        this.value = value;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++){
            System.out.println("[" + i + "]Client -> Server:" + value);
            queue.putRequest(new Request(value));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
