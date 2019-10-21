package com.lakeqiu.base.juc.executor.charpter2;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author lakeqiu
 */
public class MyTaskTest {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        IntStream.rangeClosed(1, 10).forEach(i -> threadPool.submit(new MyTask(i) {
            @Override
            protected void error(Throwable cause) {
                System.out.println(i + " --> 任务出错 --> " + cause);
            }

            @Override
            protected void doSuccess() {
                System.out.println(i + " --> 任务结束");
            }

            @Override
            protected void doTask() {
                System.out.println(i + " --> 任务开始");
                try {
                    TimeUnit.SECONDS.sleep(2);
                    if (new Random(System.currentTimeMillis()).nextBoolean()){
                        int j = 10/0;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void doinit() {
                System.out.println(i + " --> 初始化完成");
            }
        }));

        threadPool.shutdown();
    }
}
