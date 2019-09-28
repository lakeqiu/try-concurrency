package com.lakeqiu.base.design.charpter4.threadlife;

import java.util.Arrays;

/**
 * @author lakeqiu
 */
public class MainTest {
    public static void main(String[] args) {
        new ThreadLifeCycleListener().concurrentQuery(Arrays.asList("1", "2", "3"));
    }
}
