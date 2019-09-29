package com.lakeqiu.base.design.charpter6.version2;

/**
 * @author lakeqiu
 */
public class SharedData {
    private final char[] buffer;

    private final ReadWriteLock lock = new ReadWriteLock();

    public SharedData(int size) {
        this.buffer = new char[size];
        for (int i = 0; i < size; i++){
            this.buffer[i] = '*';
        }
    }

    public char[] read() throws InterruptedException {
        try {
            // 获取读锁
            lock.readLock();
            // 开始读取工作
            return this.doread();
        }finally {
            // 释放读锁
            lock.readUnlock();
        }
    }

    private char[] doread() throws InterruptedException {
        char[] newBuffer = new char[this.buffer.length];
        for (int i = 0; i < this.buffer.length; i++){
            newBuffer[i] = buffer[i];
        }

        Thread.sleep(50);
        return newBuffer;
    }

    public void write(char c) throws InterruptedException {
        try {
            // 获取写锁
            lock.writerLock();
            // 开始写入工作
            dowrite(c);
        }finally {
            lock.writerUnlock();
        }
    }

    private void dowrite(char c) throws InterruptedException {
        for (int i = 0; i < this.buffer.length; i++){
            this.buffer[i] = c;
            Thread.sleep(10);
        }
    }
}
