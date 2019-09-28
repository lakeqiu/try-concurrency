package com.lakeqiu.base.concurrency.charpter7;

/**
 * @author lakeqiu
 */
public class ThreadException {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            int a = 0;
            try {
                Thread.sleep(1000);
                a = 10 / a;
                System.out.println("aaaaaa");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread.start();

        // 在run方法中异常只能进行捕获，但是有一些异常我们完全不知道，捕获不了，这时候
        // 我们应该用下面的方法就可以捕获到了
        thread.setUncaughtExceptionHandler((t, e) -> {
            System.out.println(t + " 抛异常了，异常为 ——> " + e);
        });
    }
}
