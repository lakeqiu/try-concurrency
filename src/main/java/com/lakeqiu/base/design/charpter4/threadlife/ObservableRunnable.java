package com.lakeqiu.base.design.charpter4.threadlife;

import java.util.List;

/**
 * 被观察者
 * @author lakeqiu
 */
public abstract class ObservableRunnable implements Runnable {
    final LifeCycleListener listener;


    /**
     * 传入观察者
     * @param listener 观察者
     */
    public ObservableRunnable(LifeCycleListener listener) {
        this.listener = listener;
    }

    /**
     * 通知回调方法
     * @param runnableEvent
     */
    public void notifyChange(final RunnableEvent runnableEvent){
        listener.onEvent(runnableEvent);
    }

    /**
     * 枚举状态类
     */
    public enum RunnableState{
        RUNNING, ERROR, DONE
    }

    public static class RunnableEvent{
        /**
         * 线程状态
         */
        private final RunnableState runnableState;
        /**
         * 当前线程
         */
        private final Thread thread;
        /**
         * 错误原因
         */
        private final Throwable cause;

        public RunnableEvent(RunnableState runnableState, Thread thread, Throwable cause) {
            this.runnableState = runnableState;
            this.thread = thread;
            this.cause = cause;
        }

        public RunnableState getRunnableState() {
            return runnableState;
        }

        public Thread getThread() {
            return thread;
        }

        public Throwable getCause() {
            return cause;
        }
    }
}
