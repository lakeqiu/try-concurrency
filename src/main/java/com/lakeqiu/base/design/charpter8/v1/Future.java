package com.lakeqiu.base.design.charpter8.v1;

/**
 * 获取执行结果的方法
 * @author lakeqiu
 */
public interface Future<T> {
    T get() throws InterruptedException;
}
