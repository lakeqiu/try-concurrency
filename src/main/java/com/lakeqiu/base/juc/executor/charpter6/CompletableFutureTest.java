package com.lakeqiu.base.juc.executor.charpter6;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 大体流程就是异步调用getValue方法等到任务结果后将结果传到printValue处理最后whenComplete方法会返回
 * v(最终返回结果)， t（中间出现的异常）
 * @author lakeqiu
 */
public class CompletableFutureTest {
    public static void main(String[] args) throws InterruptedException {
        IntStream.range(0, 5).boxed().
                forEach(i -> CompletableFuture.supplyAsync(CompletableFutureTest::getValue)
                        .thenAccept(CompletableFutureTest::printValue)
                .whenComplete((v, t) -> System.out.println(v + "完成")));

        Thread.currentThread().join();
    }


    private static Integer getValue() {
        int value = ThreadLocalRandom.current().nextInt(10);
        System.out.println(Thread.currentThread().getName() + " --> 开始睡眠：" + value);
        try {
            TimeUnit.SECONDS.sleep(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " --> 结束睡眠：" + value);
        return value;
    }

    private static Integer printValue(Integer value) {
        System.out.println(Thread.currentThread().getName() + " --> 开始打印：" + value);
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " --> 结束打印：" + value);
        return value;
    }
}
