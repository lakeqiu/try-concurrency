package com.lakeqiu.base.juc.executor.charpter3;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author lakeqiu
 */
public class ExecutorServiceApi {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        test1();
        future();
    }

    private static void test1() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        List<Callable<Integer>> list = IntStream.rangeClosed(1, 3).boxed().map(i -> (Callable<Integer>)() -> {
            System.out.println(i + " --> 完成任务");
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5));
            return i;
        }).collect(Collectors.toList());
        //  该方法表示执行任务列表，返回最早结束的正常执行的任务，如果超时，则不返回并且报错。
        Integer integer = service.invokeAny(list);
        System.out.println(integer);
        System.out.println("============");
    }

    private static void future() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<?> future = service.submit((Callable<? extends Object>) () -> {
            while (!Thread.interrupted()){
                System.out.println("12334");
                TimeUnit.SECONDS.sleep(2);
            }
            return 12;
        });

        TimeUnit.SECONDS.sleep(5);
        boolean cancel = future.cancel(true);
        System.out.println(cancel);
    }
}
