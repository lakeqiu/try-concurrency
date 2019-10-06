package com.lakeqiu.base.design.charpter16;

import java.util.LinkedList;

/**
 * 用来存取methodRequest
 * @author lakeqiu
 */
public class ActivationQueue {
    /**
     * 最大存放methodRequest个数
     */
    private final static int MAX_METHOD_REQUEST_SIZE = 100;
    /**
     * 存放methodQueue集合
     */
    private final LinkedList<MethodRequest> methodQueue;

    public ActivationQueue() {
        this.methodQueue = new LinkedList<>();
    }

    /**
     * 添加methodRequest
     * @param methodRequest
     */
    public synchronized void put(MethodRequest methodRequest){
        // 如果容器已经满了
        while (this.methodQueue.size() >= MAX_METHOD_REQUEST_SIZE){
            try {
                // 进入等待
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 容器没满，将methodRequest加入其中
        this.methodQueue.addLast(methodRequest);
        this.notifyAll();
    }

    /**
     * 取出MethodRequest
     * @return
     */
    public synchronized MethodRequest take(){
        // 如果容器中没有methodRequest
        while (this.methodQueue.isEmpty()){
            try {
                // 进入等待状态
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 还有，取出返回
        MethodRequest methodRequest = this.methodQueue.removeFirst();
        // 锁住的方法一定要有这句去唤醒其他线程，告诉其他线程我已经执行完了
        this.notifyAll();
        return methodRequest;
    }
}
