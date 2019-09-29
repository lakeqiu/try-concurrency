package com.lakeqiu.base.design.charpter6.version2;

/**
 * 这个程序存在一个比较不好的地方，就是写入者几乎抢不到锁
 * @author lakeqiu
 */
public class MainTest {
    public static void main(String[] args) {
        final SharedData sharedData = new SharedData(10);
        new ReaderWorker(sharedData).start();
        new ReaderWorker(sharedData).start();
        new ReaderWorker(sharedData).start();
        new ReaderWorker(sharedData).start();
        new ReaderWorker(sharedData).start();
        new WriterWorker(sharedData, "qwertyuiopasdfg").start();
        new WriterWorker(sharedData, "QWERTYUIOPASDFG").start();

    }
}
