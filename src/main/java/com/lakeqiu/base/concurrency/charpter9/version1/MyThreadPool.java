package com.lakeqiu.base.concurrency.charpter9.version1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 自定义线程池
 * @author lakeqiu
 */
public class MyThreadPool {
    /**
     * 用户指定创建线程池时里面线程数量
     */
    private int size;
    /**
     * 默认线程数量
     */
    private final static int DEFAULT_SIZE = 10;
    /**
     * 存放任务队列的容器
     */
    private final static LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();
    /**
     * 创建线程时命名辅助用，标识是线程池创建的第几个线程
     */
    private static volatile int seq = 0;
    /**
     * 线程命名前缀
     */
    private final static String THREAD_PREFIX = "MY_THREAD_POOL-";
    /**
     * 这个线程池创建的所有线程的ThreadGroup
     */
    private final static ThreadGroup GROUP = new ThreadGroup("Pool_Group");
    /**
     * 存放创建的线程的容器
     */
    private final static List<WorkerTask> THREAD_QUEUE = new ArrayList<>();

    public MyThreadPool() {
        this(DEFAULT_SIZE);
    }

    public MyThreadPool(int size) {
        this.size = size;
        init();
    }

    /**
     * 提供给用户，让用户传入任务
     * @param runnable 任务
     */
    public void submit(Runnable runnable){
        // 由于TASK_QUEUE是共享变量（在类里面也有读的操作，故需要锁上）
        synchronized (TASK_QUEUE){
            // 加入队伍末尾
            TASK_QUEUE.addLast(runnable);
            // 唤醒等待的线程，有任务了
            TASK_QUEUE.notifyAll();
        }
    }

    /**
     * 初始化方法
     */
    private void init(){
        // 创建默认线程数量或用户指定数量
        for (int i = 0; i< size; i++){
            createWorkerTask();
        }
    }

    /**
     * 创建线程方法
     */
    private void createWorkerTask(){
        // 创建线程
        WorkerTask workerTask = new WorkerTask(GROUP, THREAD_PREFIX + (seq++));
        // 启动线程，此时因为任务队列里没有任务，故线程会进入wait状态
        workerTask.start();
        // 加入容器中
        THREAD_QUEUE.add(workerTask);
    }

    /**
     * 线程（任务）状态枚举类
     */
    private enum TaskState{
        // 就绪， 运行， 阻塞， 死亡
        FREE, RUNNING, BLOCK, DEAD
    }

    private static class WorkerTask extends Thread{
        // 线程创建后状态为准备就绪
        private volatile TaskState taskState = TaskState.FREE;

        /**
         * 创建线程需要标识ThreadGroup和name
         * 就像JDK那样，标识一下这是这个线程池创建的嘛
         * @param group 线程组
         * @param name 线程名字
         */
        public WorkerTask(ThreadGroup group, String name){
            super(group, name);
        }

        /**
         * 返回线程（任务）状态
         * @return 任务状态
         */
        public TaskState getTaskState() {
            return this.taskState;
        }

        /**
         * 从任务队列里取出任务执行
         */
        @Override
        public void run() {
            // 判断要执行的线程的状态是否已经死亡
            // 有时候用户会有大量任务，故需要临时开辟线程，等任务完成后杀死
            // 没死亡，进入代码块
            OUTER:
            while (this.taskState != TaskState.DEAD){
                Runnable runnable;
                // 锁住任务队列
                synchronized (TASK_QUEUE){
                    // 如果任务队列里没有任务的话，就进入等待状态
                    while (TASK_QUEUE.isEmpty()){
                        try {
                            // 将当前线程设置为阻塞状态
                            taskState = TaskState.BLOCK;
                            // 线程进入等待
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            // 如果中间出现打断异常，重新从While代码开始
                            break OUTER;
                        }
                    }
                    // 任务队列不为空，从任务队列里获取任务
                    runnable = TASK_QUEUE.removeFirst();
                }
                // 获取到了任务，执行的任务不是共享的，不用锁了
                // 获取到的任务不为空的话
                if (runnable != null){
                    // 将当前线程（任务）置为运行状态
                    taskState = TaskState.RUNNING;
                    // 执行线程任务
                    runnable.run();
                    // 任务已经执行完，将线程（任务）状态置准备
                    taskState = TaskState.FREE;
                }
            }
        }

        /**
         * 结束线程
         */
        public void close(){
            // 将线程（任务）置为死亡状态
            taskState = TaskState.DEAD;
        }
    }
}
