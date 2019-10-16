package com.lakeqiu.base.juc.utils.charpter9;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

/**
 * forkJoin例子：计算1加到1000000
 * @author lakeqiu
 */
public class CountTask extends RecursiveTask<Integer> {
    /**
     * 阈值
     */
    private final static int THRESHOLD = 100;
    private int start;
    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建fork-join实例
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // 创建任务
        CountTask task = new CountTask(1, 1000000);
        // 提交任务
        ForkJoinTask<Integer> result = forkJoinPool.submit(task);

        System.out.println(result.get());
    }

    @Override
    protected Integer compute() {
        // 如果任务量小于阈值，就执行任务
        if ((end - start) <= THRESHOLD){
            return IntStream.rangeClosed(start, end).sum();
        }else {
            // 任务量大于阈值，进行拆分
            int middle = (end + start) / 2;
            CountTask leftTask = new CountTask(start, middle);
            CountTask rightTask = new CountTask(middle + 1, end);

            // 执行子任务（会再创建一个线程）
            leftTask.fork();
            rightTask.fork();

            // 等待任务执行完并返回处理结果
            Integer leftResult = leftTask.join();
            Integer rightResult = rightTask.join();

            // 返回结果
            return leftResult + rightResult;
        }

    }
}
