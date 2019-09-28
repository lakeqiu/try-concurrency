package com.lakeqiu.base.concurrency.charpter9.version2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 自定义线程池
 *  版本2：添加最大任务数，但加入的任务的大于最大任务数时，执行拒绝策略
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
    /**
     * 默认任务最大数为2000
     */
    private final static int DEFAULT_TASK_QUEUE_SIZE = 2000;
    /**
     * 任务最大数
     */
    private final int taskQueueSize;
    /**
     * 默认拒绝策略：抛出异常
     */
    public final static DiscardPolicy DEFAULT_DISCARD_POLICY = () -> {
        throw new DiscardException("任务数已经达到最大！");
    };
    /**
     * 拒绝策略
     */
    private final DiscardPolicy discardPolicy;
    /**
     * 线程池是否处于关闭状态标志
     */
    private volatile boolean destroy = false;

    public MyThreadPool() {
        this(DEFAULT_SIZE, DEFAULT_TASK_QUEUE_SIZE, DEFAULT_DISCARD_POLICY);
    }

    public MyThreadPool(int size, int taskQueueSize, DiscardPolicy discardPolicy) {
        this.size = size;
        this.taskQueueSize = taskQueueSize;
        this.discardPolicy = discardPolicy;
        init();
    }

    /**
     * 提供给用户，让用户传入任务
     * @param runnable 任务
     */
    public void submit(Runnable runnable){
        // 判断线程池是否以及关闭
        if (this.destroy){
            // 无效状态异常
            throw new IllegalStateException("线程池已经关闭");
        }
        // 由于TASK_QUEUE是共享变量（在类里面也有读的操作，故需要锁上）
        synchronized (TASK_QUEUE){
            // 判断任务是否达到最大，是的话执行拒绝策略
            if (TASK_QUEUE.size() >= taskQueueSize){
                discardPolicy.discard();
            }
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
     * 关闭线程池：
     *  先关闭阻塞状态的线程，处于执行任务的线程先让其执行完任务，再关闭
     * @throws InterruptedException 异常
     */
    public void shutdown() throws InterruptedException {
        // 先判断所有任务是否已经完成，不是的等待其完成
        while (!TASK_QUEUE.isEmpty()){
            Thread.sleep(50);
        }

        // 所有任务已经完成，接下来把线程池内的线程全部杀死
        // 获取线程池内还存在的线程数量
        int intVal = THREAD_QUEUE.size();
        // 只要还有线程活着，就进行杀死线程
        while (intVal > 0){
            // 遍历存活所有线程
            for (WorkerTask workerTask : THREAD_QUEUE) {
                // 如果线程处于阻塞状态
                if (workerTask.taskState == TaskState.BLOCK){
                    // 将在等待状态的线程强制打断，重新进行判断
                    workerTask.interrupt();
                    // 关闭线程
                    // 方法内部就是将线程置为死亡状态，这样就不会进入while循代码，直接执行完run方法，结束生命
                    workerTask.close();
                    // 存活线程数减1
                    intVal--;
                }else { // 如果线程处于运行状态
                    // 让其将任务执行完，进入if代码段
                    Thread.sleep(10);
                }
            }
        }
        // 将线程池标志设置为关闭状态
        this.destroy = true;

        System.out.println("线程池关闭成功");
    }

    public int getSize() {
        return size;
    }

    public int getTaskQueueSize() {
        return taskQueueSize;
    }

    public boolean isDestroy() {
        return destroy;
    }

    /**
     * 拒绝异常
     *  给默认拒绝策略用，任务队列达到最大，拒绝新任务异常
     */
    public static class DiscardException extends RuntimeException{
        public DiscardException(String message){
            super(message);
        }
    }

    /**
     * 策略接口
     */
    public interface DiscardPolicy{
        /**
         * 拒绝策略方法
         * @throws DiscardException 抛出的异常信息
         */
        void discard() throws DiscardException;
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
