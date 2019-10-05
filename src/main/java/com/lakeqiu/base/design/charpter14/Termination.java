package com.lakeqiu.base.design.charpter14;

/**
 * 又称两阶段终止模式(承诺)，有时候，我们停止线程时并不会去主动释放资源，而这个模式就可以让线程结束时主动释放资源。
 * @author lakeqiu
 */
public class Termination extends Thread{
    /**
     * 释放结束线程标志
     */
    private volatile boolean flag = false;
    /**
     * 没什么作用
     */
    private int count = 0;

    @Override
    public void run() {
        try {
            while (!flag){
                System.out.println(Thread.currentThread().getName() + " --> " + count++);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            // e.printStackTrace();
        }finally {
            System.out.println("释放线程资源");
        }
    }

    public void shutdown(){
        this.flag = true;
        this.interrupt();
    }
}
