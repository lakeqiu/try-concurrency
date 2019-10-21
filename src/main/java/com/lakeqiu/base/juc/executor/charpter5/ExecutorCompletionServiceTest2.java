package com.lakeqiu.base.juc.executor.charpter5;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author lakeqiu
 */
public class ExecutorCompletionServiceTest2 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(4, 6, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2),
                r -> new Thread(r), new ThreadPoolExecutor.AbortPolicy());

        // 泛型为返回值
        ExecutorCompletionService<Object> service = new ExecutorCompletionService<>(poolExecutor);

        List<Runnable> runnables = IntStream.rangeClosed(0, 3).boxed().map(ExecutorCompletionServiceTest2::task).collect(Collectors.toList());
        // 将Runnable接口的任务转为Callable接口
        runnables.forEach(r -> service.submit(Executors.callable(r)));

        Future<?> future;

        // 如果改任务还没有完成，调用take()方法是不会被阻塞住的，而是返回null
        while ((future = service.take()) != null){
            System.out.println(future.get());
        }
    }

    private static Runnable task(int i){
        return () -> {
            try {
                System.out.println(i + " --> 开始执行任务");
                TimeUnit.SECONDS.sleep(i * -2 + 15);
                System.out.println(i + " --> 任务完成");
            } catch (InterruptedException e) {
                System.out.println(i + " --> 被打断了");
            }
        };
    };
}
