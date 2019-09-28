package com.lakeqiu.base.design.charpter1;

/**
 * 单例模式：饿汉式
 *  优点：不存在多线程安全问题
 *  缺点：不能懒加载，不能确定会不会用到就创建实例，有点浪费内存
 * @author lakeqiu
 */
public class SingletonObject1 {

    private static SingletonObject1 instance = new SingletonObject1();

    private SingletonObject1() {
    }

    public static SingletonObject1 getInstance() {
        return SingletonObject1.instance;
    }
}
