package com.lakeqiu.base.design.charpter12;

/**
 * 主要思路： 定义total代表要等待的线程数量，定义count表示已经完成工作的线程数量
 *          当每个线程完成任务之后，调用countDown方法告诉MyCountDown这个线程任务已经结束了
 *          而等待的线程（其调用await方法进入等待）会被countDown唤醒，并进行while判断，如果total等于count，
 *          则表示所有线程已经完成工作，等待线程进入接下来的工作；如果不等于，表示还有线程没有完成工作，调用wait方法
 *          继续进入等待状态
 * @author lakeqiu
 */
public class MyCountDown {
    /**
     * 让用户设置要等待的线程数量
     */
    private final int total;
    /**
     * 计算已经完成工作的线程数量
     * 完成工作的线程会在最后调用countDown方法让这个字段加1
     */
    private int count;

    public MyCountDown(int total) {
        this.total = total;
    }

    public void countDown(){
        synchronized (this){
            // count加1
            this.count++;
            // 唤醒等待的线程
            this.notifyAll();
        }
    }

    public void await() throws InterruptedException {
        synchronized (this){
            // count != total
            // 说明还有线程没有完成工作
            while (count != total){
                // 进入等待
                this.wait();
            }
        }
    }
}
