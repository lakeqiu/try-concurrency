package com.lakeqiu.base.concurrency.charpter6.version2;

import java.util.Collection;

/**
 * 打造自己精致的锁
 * @author lakeqiu
 */
public interface Lock {
    /**
     * 自定义错误
     */
    class TimeOutException extends Exception{
        public TimeOutException(String message) {
            super(message);
        }
    }

    /**
     * 上锁
     * @throws InterruptedException 被打断时抛出异常
     */
    void lock() throws InterruptedException;

    /**
     * 上锁，超过mills没拿到放弃
     * @param mills 上锁时间
     * @throws InterruptedException 被打断时抛出异常
     * @throws TimeOutException 延时时抛出异常
     */
    void lock(long mills) throws InterruptedException, TimeOutException;

    /**
     * 释放锁
     */
    void unLock();

    /**
     * 获取被阻塞的线程，即等待的线程
     * @return 结果
     */
    Collection<Thread> getBlockThread();

    /**
     * 获取被阻塞的线程数量
     * @return int
     */
    int getBlockSize();
}
