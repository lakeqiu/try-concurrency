package com.lakeqiu.base.design.charpter1;

/**
 * 版本3：synchronize
 *  虽然解决了线程安全性问题，但是效率严重降低了
 *  获取instance时每次只能一个线程获取，严重降低了效率
 * @author lakeqiu
 */
public class SingletonObject3 {
    private static SingletonObject3 instance;

    private SingletonObject3() {
    }

    public synchronized static SingletonObject3 getInstance() {
        if (SingletonObject3.instance == null){
            SingletonObject3.instance = new SingletonObject3();
        }

        return SingletonObject3.instance;
    }
}
