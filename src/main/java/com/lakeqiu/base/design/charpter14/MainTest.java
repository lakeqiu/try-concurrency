package com.lakeqiu.base.design.charpter14;

/**
 * @author lakeqiu
 */
public class MainTest {
    public static void main(String[] args) throws InterruptedException {
        Termination termination = new Termination();
        termination.start();

        Thread.sleep(5000);
        termination.shutdown();
    }
}
