package com.lakeqiu.base.juc.utils.charpter3;

import java.util.concurrent.Exchanger;

/**
 * @author lakeqiu
 */
public class ExchangeTest {
    public static void main(String[] args) {
        Exchanger<Object> exchanger = new Exchanger<>();

        new Thread(() -> {
            System.out.println("-----A------");
            try {
                Object exchange = exchanger.exchange("这是A生产的");
                System.out.println("A --> " + exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
                Object exchange = exchanger.exchange("这是B生产的");
                System.out.println("B --> " + exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
