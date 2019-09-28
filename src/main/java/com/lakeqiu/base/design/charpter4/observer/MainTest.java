package com.lakeqiu.base.design.charpter4.observer;

import java.util.stream.IntStream;

/**
 * 测试类
 * @author lakeqiu
 */
public class MainTest {

    public static void main(String[] args) {
        Subject subject = new Subject();

        OtherObserver otherObserver = new OtherObserver(subject);
        SimpleObserver simpleObserver = new SimpleObserver(subject);

        IntStream.rangeClosed(1,10).forEach(subject::setState);
    }
}
