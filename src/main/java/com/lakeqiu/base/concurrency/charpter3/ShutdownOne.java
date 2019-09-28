package com.lakeqiu.base.concurrency.charpter3;

/**
 * 优雅结束线程的生命例子1
 *  设置一个标志，没此循环的时候判断一下
 * @author lakeqiu
 */
public class ShutdownOne {


    public static void main(String[] args) throws InterruptedException {
        ThreadOne threadOne = new ThreadOne();
        new Thread(threadOne).start();

        Thread.sleep(1);

        threadOne.shutdown();
    }


    static class ThreadOne implements Runnable{
        // 是否结束线程标志
        private static boolean flag = true;

        @Override
        public void run() {
            while (flag){
                // 线程业务逻辑
                System.out.println("执行线程任务逻辑中");
            }

            // 线程要结束后的后续处理逻辑
            System.out.println("线程即将结束，清理现场中");
        }

        public void shutdown(){
            flag = false;
        }
    }
}