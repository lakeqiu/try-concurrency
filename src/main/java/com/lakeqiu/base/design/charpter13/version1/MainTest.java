package com.lakeqiu.base.design.charpter13.version1;

import java.util.stream.IntStream;

/**
 * @author lakeqiu
 */
public class MainTest {
    public static void main(String[] args) {
        MessageHandler handler = new MessageHandler();
        IntStream.rangeClosed(1, 5).forEach(i ->
                handler.request(new Message("任务" + i))
        );
    }
}
