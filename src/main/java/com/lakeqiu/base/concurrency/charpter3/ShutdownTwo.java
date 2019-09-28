package com.lakeqiu.base.concurrency.charpter3;

/**
 * 优雅结束线程生命2
 *  利用可以设置打断标志的api，每次执行的时候判断一下
 * @author lakeqiu
 */
public class ShutdownTwo {


    public static void main(String[] args) throws InterruptedException {
        ThreadTwo threadTwo = new ThreadTwo();

        Thread thread = new Thread(threadTwo);
        thread.start();

        Thread.sleep(1);

        thread.interrupt();
    }


    static class ThreadTwo implements Runnable{

        @Override
        public void run() {
            // 注意，如果没有被打断，interrupted()方法返回的是false
            while (!Thread.interrupted()){
                // 线程业务逻辑
                System.out.println("执行线程任务逻辑中");

                /*if (!Thread.interrupted()){
                    // 也可以while(true),然后里面立马判断
                    // break是为了不立即返回，可以执行后续处理逻辑
                    break;
                }*/
            }

            // 线程要结束后的后续处理逻辑
            System.out.println("线程即将结束，清理现场中");
        }
    }
}
