package com.lakeqiu.base.concurrency.charpter4;

/**
 * @author lakeqiu
 */
public class TicketWindowsRunnable implements Runnable {
    private static int MAX = 50;

    private int index = 0;
    /**
     * 在方法里锁住这个，比方法上加synchronize锁住整个对象损耗的性能低
     */
    private static Object MONITOR = new Object();


    @Override
    public void run() { // 这里的代码如果锁的是方法，那么就只有一个线程完成这个工作
        /*// 存在安全性问题的代码
        while (index < MAX){ // 1
                try {
                    // 模拟中间业务逻辑代码
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }// 2

                System.out.println(Thread.currentThread().getName() + "当前叫号-->" + index); // 3
        }*/

        /*// 如果不喜欢新建一个对象锁住，也可以采用这样一个方法
        while (true){
            if (ticket()){
                break;
            }
        }*/

        while (index < MAX){ // 1
            synchronized (MONITOR){
                try {
                    // 模拟中间业务逻辑代码
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }// 2

                System.out.println(Thread.currentThread().getName() + "当前叫号-->" + (++index)); // 3
            }
        }
    }

    public void get(){
        index = index + 100;
    }

    /**
     * 如果不喜欢新建一个对象锁住，也可以采用这样一个方法
     * @return
     */
    private synchronized boolean ticket(){
        // 先判断还需不需要操作
        if (index > MAX){
            return true;
        }
        try {
            // 模拟中间业务逻辑代码
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }// 2

        System.out.println(Thread.currentThread().getName() + "当前叫号-->" + index);

        return false;
    }
}
