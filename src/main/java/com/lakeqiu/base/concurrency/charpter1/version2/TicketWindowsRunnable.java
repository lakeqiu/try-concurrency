package com.lakeqiu.base.concurrency.charpter1.version2;

/**
 * 柜台
 * @author lakeqiu
 */
public class TicketWindowsRunnable implements Runnable {
    /**
     * 队伍最大数量
     */
    private static int MAX = 50;

    /**
     * 已经排了多少个
     */
    private int index = 0;


    @Override
    public void run() {
        // 队伍未达到最大数量，可以继承排队
        while (index < MAX){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "-->当前号码是-->" + (++index));
        }
    }
}
