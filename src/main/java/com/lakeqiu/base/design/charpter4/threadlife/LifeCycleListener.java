package com.lakeqiu.base.design.charpter4.threadlife;

/**
 * 观察者
 * @author lakeqiu
 */
public interface LifeCycleListener {
    final Object LOCK = new Object();

    /**
     * 观察者要实现的方法
     * @param event 状态改变信息
     */
    void onEvent(ObservableRunnable.RunnableEvent event);
}
