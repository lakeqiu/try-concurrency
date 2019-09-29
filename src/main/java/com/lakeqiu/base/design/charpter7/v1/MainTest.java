package com.lakeqiu.base.design.charpter7.v1;

import java.util.Collections;
import java.util.stream.IntStream;

/**
 * @author lakeqiu
 */
public class MainTest {

    public static void main(String[] args) {
        Person person = new Person("ll", 19);
        IntStream.rangeClosed(0, 10).forEach(i ->
                new Thread(new UserPersonThread(person)).start());
    }
}
