package com.lakeqiu.base.design.charpter16;

/**
 * 负责调用execute方法处理 MethodRequest对象的类
 *
 * 执行异步方法中间的桥梁
 * @author lakeqiu
 */
public class SchedulerThread extends Thread {
    private final ActivationQueue activationQueue;

    public SchedulerThread(ActivationQueue activationQueue) {
        this.activationQueue = activationQueue;
    }

    /**
     * 向ActivationQueue中存入methodRequest
     * @param methodRequest
     */
    public void invoke(MethodRequest methodRequest){
        this.activationQueue.put(methodRequest);
    }

    /**
     * 调用execute方法处理 MethodRequest
     */
    @Override
    public void run() {
        while (true){
            this.activationQueue.take().execute();
        }
    }
}
