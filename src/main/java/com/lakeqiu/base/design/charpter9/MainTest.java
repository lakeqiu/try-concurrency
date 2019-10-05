package com.lakeqiu.base.design.charpter9;

/**
 * @author lakeqiu
 */
public class MainTest {
    public static void main(String[] args) {
        RequestQueue queue = new RequestQueue();
        new ClientThread(queue, "请求").start();
        new ServerThread(queue).start();
    }
}
