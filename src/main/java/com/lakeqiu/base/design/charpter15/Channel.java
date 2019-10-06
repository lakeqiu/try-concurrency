package com.lakeqiu.base.design.charpter15;

import java.util.Arrays;

/**
 * 接收工作请求并将工作请求交给工人线程的类
 *
 * 类似于流水线
 * 有添加任务（请求）、取出任务（请求）的功能
 * @author lakeqiu
 */
public class Channel {
    /**
     * 最大任务（请求）数量
     */
    private final static int MAX_REQUEST = 100;
    /**
     * 存放请求的数组
     */
    private final Request[] requestQueue;
    /**
     * 辅助数组的下标，指向当前要取出的任务
     */
    private int head;
    /**
     * 辅助数组的下标，指向当前任务的末尾
     */
    private int tail;
    /**
     * 辅助数组的字段，表示当前有多少个任务
     */
    private int count;
    /**
     * 存放执行任务的线程（工人线程）的数组
     */
    private final WorkerThread[] threadPool;

    /**
     * 构造函数
     * @param workers 执行任务的线程数量
     */
    public Channel(int workers) {
        this.requestQueue = new  Request[MAX_REQUEST];
        this.head = 0;
        this.tail = 0;
        this.count = 0;
        this.threadPool = new WorkerThread[workers];
        init();
    }

    /**
     * 初始化方法，创建工人线程
     */
    private void init() {
        for (int i = 0; i < threadPool.length; i++){
            threadPool[i] = new WorkerThread("工人" + i, this);
        }
    }

    /**
     * 让工人线程接收任务开始工作
     */
    public void work(){
        Arrays.stream(threadPool).forEach(WorkerThread::start);
    }

    /**
     * 往流水线添加任务
     * @param request 任务
     */
    public synchronized void put(Request request){
        // 如果任务队列已经满了，就进入等待状态
        while (count >= requestQueue.length){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 将请求加入队列中
        requestQueue[tail] = request;
        // tail上移
        tail = (tail+1) % requestQueue.length;
        // 请求加1
        this.count++;
        // 唤醒等待的工人线程
        this.notifyAll();
    }

    /**
     * 取出请求（任务）
     * @return
     */
    public synchronized Request take(){
        // 如果流水线没有任务，则进入等待状态
        while (count <= 0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 有请求，取出请求
        Request request = requestQueue[head];
        // head加1
        head = (head + 1) % requestQueue.length;
        // count减1
        this.count--;
        // 唤醒其他等待线程
        this.notifyAll();

        return request;
    }
}
