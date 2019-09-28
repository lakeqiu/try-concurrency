package com.lakeqiu.base.concurrency.charpter1.version1;

import java.util.Optional;

/**
 *  采用多线程方式模拟银行排队叫号
 *  版本1
 *  银行类
 * @author lakeqiu
 */
public class Blank {
    public static void main(String[] args) {
        TicketWindows windows = new TicketWindows("柜台1");
        // 如果windows.getName()不为空则打印
        Optional.of(windows.getName()).ifPresent(System.out::println);
        /*new TicketWindows("柜台2").start();
        new TicketWindows("柜台3").start();*/
    }
}