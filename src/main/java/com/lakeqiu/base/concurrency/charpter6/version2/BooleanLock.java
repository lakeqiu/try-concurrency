package com.lakeqiu.base.concurrency.charpter6.version2;

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
    public synchronized void lock(long mills) throws InterruptedException, TimeOutException {
        // 如果mills小于等于0，默认使用lock
        if (mills <= 0){
            lock();
        }

        // 剩余等待时间
        long hasRemaining = mills;
        // 在这个时间前没拿到锁就应该放弃锁了
        long endTime = System.currentTimeMillis() + mills;

        // 判断锁是否被其他线程持有，是的话进入while代码块
        while (initValue){
            // 先判断是不是超时了
            if (hasRemaining <= 0){
                throw new TimeOutException("超时了，放弃锁");
            }
            // 把自己加入等待队列
            blockTreadCollection.add(Thread.currentThread());
            // 等待mills时间后醒过来，再次进入while就可以抛出异常了
            this.wait(mills);
            // 如果被唤醒还没拿到锁，重新计算剩余等待时间
            hasRemaining = endTime - System.currentTimeMillis();
        }

        // 锁没被其他线程持有
        // 把锁锁上
        this.initValue = true;
        // 把自己从等待队列里移除
        blockTreadCollection.remove(Thread.currentThread());
        // 把自己设置为工作线程
        this.currentThread = Thread.currentThread();
    }

    /**
     * 释放锁
     */
    @Override
    public synchronized void unLock() {
        // 判断要释放锁的线程是不是持有锁的线程
        if (Thread.currentThread() == currentThread){
            // 将initValue置为false，表示放弃锁
            this.initValue = false;
            System.out.println(Thread.currentThread().getName() + " --> 放弃了锁");
            // 唤醒其他等待的线程
            this.notifyAll();
        }
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
