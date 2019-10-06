package com.lakeqiu.base.design.charpter16;

/**
 * 单例模式，负责创建主动对象
 * @author lakeqiu
 */
public final class ActiveObjectFactory {
    private ActiveObjectFactory() {

    }

    public static ActiveObject creatActiveObject(){
        Servant servant = new Servant();
        ActivationQueue activationQueue = new ActivationQueue();
        SchedulerThread schedulerThread = new SchedulerThread(activationQueue);
        ActiveObjectProxy proxy = new ActiveObjectProxy(servant, schedulerThread);
        // 将线程启动
        schedulerThread.start();
        return proxy;
    }
}
