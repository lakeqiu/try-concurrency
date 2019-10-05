package com.lakeqiu.base.design.charpter9;

import java.util.LinkedList;

/**
 * 请求队列类
 * @author lakeqiu
 */
public class RequestQueue {

    private final LinkedList<Request> queue = new LinkedList<>();

    /**
     * 服务器调用
     * 从队列中获取请求
     * @return 请求
     */
    public Request getRequest(){
        synchronized (queue){
            while (queue.size() <= 0){
                try {
                    // 如果队列为空，则进入等待
                    queue.wait();
                } catch (InterruptedException e) {
                    return null;
                }
            }
            // 返回队列的第一个请求
            return queue.removeFirst();
        }
    }

    /**
     * 客户端调用
     * 将请求加入请求队列中
     * @param request 请求
     */
    public void putRequest(Request request){
        synchronized (queue){
            // 将请求加入队列中
            this.queue.addLast(request);
            // 唤醒其他等待线程（调用getRequest的线程）
            this.queue.notifyAll();
        }
    }
}
