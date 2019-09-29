package com.lakeqiu.base.design.charpter8.v2;

/**
 * 真正做事的接口，具体业务由用户实现接口写
 * @author lakeqiu
 */
public interface FutureTask<T> {
    T call();
}
