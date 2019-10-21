package com.lakeqiu.base.juc.executor.charpter6;

import com.sun.org.apache.regexp.internal.RE;

import java.util.Optional;
import java.util.concurrent.*;

/**
 * 这个例子说明了使用runAfterBoth可以让几个不同的任务同时进行，
 * @author lakeqiu
 */
public class CompletableFutureTest2 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        /*ExecutorService service = Executors.newFixedThreadPool(4);
        CompletableFuture.supplyAsync(() -> {
            System.out.println("开始解析基础信息");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }, service).runAfterBoth(CompletableFuture.supplyAsync(() -> {
            System.out.println("开始解析进阶信息");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 2;
        }), () -> System.out.println("======="));*/

//        System.out.println(anyOf().get());
//        test();
//        System.out.println("909090909");


//        test3();

        test4();
        test5();
        test6();
        Thread.currentThread().join();
    }


    private static Future<?> anyOf(){
        return CompletableFuture.anyOf(CompletableFuture.supplyAsync(() -> {
            System.out.println("1111");
            return 1;
        }).whenComplete((v, t) -> System.out.println(v)),
                CompletableFuture.supplyAsync(() -> {
            System.out.println("222");
            return 2;
        }).whenComplete((v, t) -> System.out.println(v)));
    }

    private static void test(){
        CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " Hellow");
            return "1212";
        }).thenApply(s -> {
                    System.out.println(Thread.currentThread().getName() + s + "momo");
                    return Thread.currentThread().getName() + s;
                })
                .whenComplete((v, t) -> {
                    try {
                        System.out.println(Thread.currentThread().getName() + "=====");
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(v);
                });
    }

    private static void test2(){
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> "Hellow").handleAsync((v, t) -> {
            Optional.of(t).ifPresent(System.out::println);
            return v.length();
        });
    }

    private static void test3() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hellow");
        TimeUnit.SECONDS.sleep(1);
        future.complete("wowo");
        System.out.println(future.get());
    }

    /**
     * thenCompose
     */
    private static void test4(){
        CompletableFuture.supplyAsync(() -> "Hellow").thenCompose(s -> CompletableFuture
                .supplyAsync(() -> s + " World")).whenCompleteAsync((v, t) -> System.out.println(v));
    }

    /**
     * thenCombine
     */
    private static void test5(){
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hellow");
        // s2才是future的
        CompletableFuture.supplyAsync(() -> " World").thenCombine(future, (s1, s2) -> s1 + s2)
                .whenCompleteAsync((v, t) -> System.out.println(v));
    }

    /**
     * thenAcceptBothAsync
     */
    private static void test6(){
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hellow");
        // s2才是future的,与thenCombine其内部可以写输出语句
        CompletableFuture.supplyAsync(() -> " World").thenAcceptBothAsync(future, (s1, s2) -> System.out.println(s1 + s2));
    }
}
