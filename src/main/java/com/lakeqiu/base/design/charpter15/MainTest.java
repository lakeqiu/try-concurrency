package com.lakeqiu.base.design.charpter15;

import java.util.stream.IntStream;

/**
 * @author lakeqiu
 */
public class MainTest {
    public static void main(String[] args) {
        Channel channel = new Channel(5);
        channel.work();

        IntStream.rangeClosed(1, 5).forEach(i ->
                new TransportThread("搬运工" + i, channel).start()
        );
    }
}
