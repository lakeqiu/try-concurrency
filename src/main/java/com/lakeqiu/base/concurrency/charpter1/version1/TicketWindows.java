package com.lakeqiu.base.concurrency.charpter1.version1;

/**
 *  柜台
 * @author lakeqiu
 */
public class TicketWindows extends Thread{
    /**
     * 柜台名字
     */
    private String name;

    /**
     * 队伍最大数量
     */
    private static int MAX = 50;

    /**
     * 已经排了多少个
     */
    private static int index = 0;

    public TicketWindows(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        // 队伍未达到最大数量，可以继承排队
        while (index < MAX){
            System.out.println(name + "-->当前号码是-->" + (++index));
        }
    }
}
