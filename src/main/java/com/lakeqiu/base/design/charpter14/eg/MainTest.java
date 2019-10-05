package com.lakeqiu.base.design.charpter14.eg;

/**
 * @author lakeqiu
 */
public class MainTest {
    public static void main(String[] args) {
        AppServer server = new AppServer(15780);
        server.start();
    }
}
