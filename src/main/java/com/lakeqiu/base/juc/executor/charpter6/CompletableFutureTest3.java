package com.lakeqiu.base.juc.executor.charpter6;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author lakeqiu
 */
public class CompletableFutureTest3 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        complete();
        Thread.currentThread().join();
    }

    private static void acceptEither(){
        CompletableFuture.supplyAsync(() -> {
            System.out.println("开始 --> 11111");
            try {
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "11111";
        }).acceptEither(CompletableFuture.supplyAsync(() -> {
            System.out.println("开始 --> 22222");
            try {
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "22222";
        }), System.out::println);
    }

    private static void complete() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            int i = 0;
            while (i < 10){
                i = i + 1 - 1;
            }
            System.out.println(Thread.currentThread().getName() +  " --> 1111");
            return 1;
        });
        boolean b = future.complete(2);
        System.out.println(b);
        System.out.println(future.get());
    }
}
