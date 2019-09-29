package com.lakeqiu.base.design.charpter6.version1;

/**
 * 获取读锁、写锁的类
 * @author lakeqiu
 */
public class ReadWriteLock {
    /**
     * 阅读者数量
     */
    private int readingReaders = 0;
    /**
     * 等待阅读者数量
     */
    private int waitingReaders = 0;
    /**
     * 写入者数量
     */
    private int writingWriters = 0;
    /**
     * 等待写入者数量
     */
    private int waitingWriters = 0;

    /**
     * 获取读锁
     */
    public synchronized void readLock() throws InterruptedException {
        // 不知道能不能获取读锁，所以先加入等待获取读锁的队列中
        this.waitingReaders++;
        try {
            // 只要还有人在写入中，就进行等待
            while (writingWriters > 0){
                this.wait();
            }
            // 没人在写入了，获取读锁,把阅读者数量加1
            this.readingReaders++;
        }finally {
            // 获取锁完了，将自己从等待队列中剔除
            this.waitingReaders--;
        }
    }

    /**
     * 释放读锁
     */
    public synchronized void readUnlock(){
        // 将自己从阅读队列里剔除
        this.readingReaders--;
        // 唤醒其他线程
        this.notifyAll();
    }

    /**
     * 获取写锁
     */
    public synchronized void writerLock() throws InterruptedException {
        // 不知道能不能获取锁成功，所以先自己加入等待队列
        this.waitingWriters++;
        try {
            // 只要还有人在写入或读取，就进行等待
            while (readingReaders > 0 || writingWriters >0 ){
                this.wait();
            }
            // 没有写入或读取，可以获取锁了，把自己加入写入者队列
            this.writingWriters++;
        }finally {
            // 获取锁完了，将自己从等待队列中剔除
            this.waitingWriters--;
        }
    }

    /**
     * 释放写锁
     */
    public synchronized void writerUnlock(){
        // 将自己从写入者队列中剔除
        this.writingWriters--;
        // 唤醒其他线程
        this.notifyAll();
    }
}
