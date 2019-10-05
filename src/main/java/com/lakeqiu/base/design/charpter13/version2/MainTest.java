package com.lakeqiu.base.design.charpter13.version2;

import java.util.stream.IntStream;

/**
 * @author lakeqiu
 */
public class MainTest {
    public static void main(String[] args) {
        MessageHandler handler = new MessageHandler();
        IntStream.rangeClosed(1, 10).forEach(i ->
                handler.request(new Message("任务" + i))
        );
        // 线程池会将已经放进去的任务执行完成再关闭
        handler.shutdown();
    }
}
