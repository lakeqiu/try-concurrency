package com.lakeqiu.base.design.charpter8.v1;

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
}
