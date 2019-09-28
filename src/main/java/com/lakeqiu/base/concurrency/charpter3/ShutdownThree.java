package com.lakeqiu.base.concurrency.charpter3;

/**
 * 结束线程生命例子3
 *  把要执行任务的线程设置为守护线程，再创建一个用户线程去调用它并等待期执行结束，
 *  到时候我们只要结束用户线程，那么执行任务的线程作为其守护线程，也会结束。
 *
 *  结束用户线程通过interrupt方法打断。（用户线程会使用join方法等待守护线程结束）
 * @author lakeqiu
 */
public class ShutdownThree implements Runnable{
    @Override
    public void run() {
        while (true){
            // 执行非常耗时的操作，如写入大文件等，被阻塞在这里了
            // 前面的两种方式是再一次循环时判断，但这个没有再一次循环判断的机会，该怎么立即结束该线程呢？
            try {
                Thread.sleep(1000);
                System.out.println("任务结束");
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ShutdownThree three = new ShutdownThree();
        ThreadThree threadThree = new ThreadThree();

        threadThree.execute(three);

        threadThree.shutdown(10000);
    }
}

class ThreadThree{
    /**
     * 执行线程
     */
    private Thread executeThread;
    /**
     * 是否要执行结束标志
     */
    private boolean finished = false;

    /**
     * 创建线程执行任务
     * @param task 要执行的任务
     */
    public void execute(Runnable task){
        // 创建用户线程
        executeThread = new Thread() {
            @Override
            public void run() {
                // 创建执行任务的线程
                Thread runner = new Thread(task);
                // 设置为守护线程
                runner.setDaemon(true);

                runner.start();

                try {
                    // 等待守护线程执行结束并将标志位设置为true
                    runner.join();
                    // 保证守护线程提前结束的话，用户线程也结束，没必要到我们设定的等待时间才去结束他
                    finished = true;
                } catch (InterruptedException e) {
//                    e.printStackTrace();
                }
            }
        };
        // 启动用户线程
        executeThread.start();
    }

    /**
     * 结束线程，也可以设定等待时间，如果过去等待时间线程还没结束，则强制结束线程，
     * 值为0代表立即结束线程
     * @param mills 等待时间
     */
    public void shutdown(long mills){
        // 获取当前时间
        long currentTime = System.currentTimeMillis();
        // 如果用户线程还没结束，就进入
        // 当线程提前结束，finished就会变为true，就进不去while代码块了
        while (!finished){
            // 如果任务执行时间超过我们设置要等待的时间
            if (System.currentTimeMillis() - currentTime >= mills){
                System.out.println("任务超时-->结束任务");
                executeThread.interrupt();
                // 退出判断循环
                break;
            }

            // 保证守护线程提前结束的话，用户线程也结束，没必要到我们设定的等待时间才去结束他
            // TODO 不知道怎么保证用户线程提前结束的
            try {
                executeThread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("任务提前结束");
            }
        }

        // 当线程提前结束，finished
        finished = false;
    }

}
