package com.lakeqiu.base.concurrency.charpter6.version1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author lakeqiu
 */
public class BooleanLock implements Lock {
    /**
     * 表示是否已经加上锁的标志
     */
    private boolean initValue;

    /**
     * 储存被阻塞的线程
     */
    private Collection<Thread> blockTreadCollection = new ArrayList<>();

    /**
     * 储存当前工作线程
     */
    private Thread currentThread;

    public BooleanLock() {
        // 初始化initValue
        this.initValue = false;
    }

    /**
     * 上锁
     * @throws InterruptedException 被打断时抛出异常
     */
    @Override
    public synchronized void lock() throws InterruptedException {
        // 检查是否已经有其他线程有锁了
        while (initValue){
            // 已经有其他线程持有锁了
            // 把自己加入等待队列
            blockTreadCollection.add(Thread.currentThread());
            // 进入等待，等待其他线程唤醒
            this.wait();
        }

        // 可以获取锁了，将自己从等待队列里剔除
        blockTreadCollection.remove(Thread.currentThread());
        // 将initValue置为true，表示已经有线程持有锁了
        this.initValue = true;
        // 将自己置为当前工作线程
        this.currentThread = Thread.currentThread();
    }

    /**
     * 上锁，超过mills没拿到放弃
     * @param mills 上锁时间
     * @throws InterruptedException 被打断时抛出异常
     * @throws TimeOutException 延时时抛出异常
     */
    @Override
    public void lock(long mills) throws InterruptedException, TimeOutException {

    }

    @Override
    public synchronized void unLock() {
        // 将initValue置为false，表示放弃锁
        this.initValue = false;
        System.out.println(Thread.currentThread().getName() + " --> 放弃了锁");
        // 唤醒其他等待的线程
        this.notifyAll();
    }

    /**
     * 获取被阻塞的线程，即等待的线程
     * @return 结果
     */
    @Override
    public Collection<Thread> getBlockThread() {
        // 因为如果直接把容器返回去的话，其他线程就可以随意修改存放等待线程的容器，非常不安全
        // 故此，这个方法可以防止这种事，只要其他线程修改，就会抛出错误
        return Collections.unmodifiableCollection(blockTreadCollection);
    }

    /**
     * 获取被阻塞的线程数量
     * @return int
     */
    @Override
    public int getBlockSize() {
        return blockTreadCollection.size();
    }
}
