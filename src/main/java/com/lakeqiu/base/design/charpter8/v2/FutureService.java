package com.lakeqiu.base.design.charpter8.v2;

import java.util.function.Consumer;

/**
 * 将Future与FutureTask接口组合起来
 * @author lakeqiu
 */
public class FutureService {

    public <T> Future<T> submit(final FutureTask<T> task){
        AsynFuture<T> asynFuture = new AsynFuture<>();
        // 创建一个线程执行任务
        new Thread(() -> {
            // 执行任务
            T result = task.call();
            // 将结果给ASynFuture，让用户可以调用AsynFuture的get方法时获取结果
            asynFuture.done(result);
        }).start();
        // 返回AsynFuture
        return asynFuture;
    }

    /**
     * 版本2方法，参数添加一个消费者接口，用户传进一个方法（其类要实现消费者接口）去消费执行结果
     * @param task 任务
     * @param consumer 消费者
     * @param <T>
     * @return
     */
    public <T> Future<T> submit(final FutureTask<T> task, final Consumer<T> consumer){
        AsynFuture<T> asynFuture = new AsynFuture<>();
        // 创建一个线程执行任务
        new Thread(() -> {
            // 执行任务
            T result = task.call();
            // 将结果给ASynFuture，让用户可以调用AsynFuture的get方法时获取结果
            asynFuture.done(result);
            // 消费者消费结果
            consumer.accept(result);
        }).start();
        // 返回AsynFuture
        return asynFuture;
    }
}
