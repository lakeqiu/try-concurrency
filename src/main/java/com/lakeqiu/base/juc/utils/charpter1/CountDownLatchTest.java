package com.lakeqiu.base.juc.utils.charpter1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 假如有多个任务使用多线程去执行（可以看出有个任务为了执行效率拆分成多个），那么，
 * 怎么让主线程等待这多个任务执行完得到结果接下来去执行呢？
 * @author lakeqiu
 */
public class CountDownLatchTest {
    private static final CountDownLatch latch = new CountDownLatch(10);
    private static final ExecutorService executors = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws Exception {
        int[] data = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int i = 0; i < 10; i++){
            executors.execute(new TaskRunnable(data, i, latch));
        }

        latch.await();
        executors.shutdown();
        System.out.println("主线程结束");
    }
}

class TaskRunnable implements Runnable{
    /**
     * 数组，下标，CountDownLatch
     */
    private final int[] data;
    private final int index;
    private final CountDownLatch latch;

    public TaskRunnable(int[] data, int index, CountDownLatch latch) {
        this.data = data;
        this.index = index;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            // 模仿其他耗时操作
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int value = data[index];
        if (value%2 == 0){
            data[index] = value * 2;
        }else {
            data[index] = value * 10;
        }
        System.out.println(Thread.currentThread().getName() + " --> NO." + index + "工作完成");

        // 标志当前任务完成
        latch.countDown();
    }
}
