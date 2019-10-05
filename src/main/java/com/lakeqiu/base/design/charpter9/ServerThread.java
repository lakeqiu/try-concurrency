package com.lakeqiu.base.design.charpter9;

/**
 * 服务器，负责将请求从队列中取出来
 * @author lakeqiu
 */
public class ServerThread extends Thread {
    private final RequestQueue queue;
    /**
     * 标志位，用来辅助是否结束执行
     * 默认为false
     */
    private volatile boolean flag = false;

    public ServerThread(RequestQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!flag){
            // 获取通知，判断通知是否为空
            Request request = queue.getRequest();
            if (null == request){
                System.out.println("请求为空");
                continue;
            }
            System.out.println("Server -> Client:" + request.getName());

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void close(){
        this.flag = true;
        this.interrupt();
    }
}
