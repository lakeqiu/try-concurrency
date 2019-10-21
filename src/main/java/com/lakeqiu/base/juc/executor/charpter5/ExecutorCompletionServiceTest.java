package com.lakeqiu.base.juc.executor.charpter5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author lakeqiu
 */
public class ExecutorCompletionServiceTest {
    public static void main(String[] args) {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(4, 6, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2),
                r -> new Thread(r), new ThreadPoolExecutor.AbortPolicy());

        // 例子1
        List<Runnable> runnables = IntStream.rangeClosed(1, 5).boxed().map(ExecutorCompletionServiceTest::task).collect(Collectors.toList());
        // 存放结果
        List<Future<?>> futureList = new ArrayList<>();
        runnables.forEach(r -> futureList.add(poolExecutor.submit(r)));

        // 这里有一个非常严重的问题，就是在设置中，下标越小的执行任务的时间越长，
        // 这里的获取结果是从0开始获取的，导致后面的任务虽然已经执行完了，但是还要
        // 等待其他未执行完的任务，白白耗费了资源。
        IntStream.range(0, 5).forEach(r -> {
            try {
                System.out.println(futureList.get(r).get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        ExecutorCompletionService<Void> service = new ExecutorCompletionService<>(poolExecutor);
    }

    private static Runnable task(int i){
        return () -> {
            try {
                System.out.println(i + " --> 开始执行任务");
                TimeUnit.SECONDS.sleep(i * -2 + 20);
                System.out.println(i + " --> 任务完成");
            } catch (InterruptedException e) {
                System.out.println(i + " --> 被打断了");
            }
        };
    };
}
