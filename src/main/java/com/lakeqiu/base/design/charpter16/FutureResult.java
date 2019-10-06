package com.lakeqiu.base.design.charpter16;

/**
 * 期货，用户拿到的还不是真正的货（还在生产中），
 * 需要调用getResultValue去取
 * @author lakeqiu
 */
public class FutureResult implements Result {
    private Result result;
    /**
     * 表示真正的货物是否已经准备好
     */
    private volatile boolean ready = false;

    public synchronized void setResult(Result result) {
        this.result = result;
        this.ready = true;
        // 真正的获取已经准备好了，唤醒其他线程
        this.notifyAll();
    }

    @Override
    public synchronized Object getResultValue() {
        while (!ready){
            // 货物还没准备好，进入等待状态
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.result.getResultValue();
    }
}
