package com.lakeqiu.base.design.charpter1;

/**
 * 版本6：内部静态类
 *  - 这种方式采用了类装载的机制来保证初始化实例时只有一个线程。
 * - 静态内部类方式在Singleton类被装载时并不会立即实例化，而是在需要实例化时，调用getInstance方法，才会装载SingletonInstance类，从而完成Singleton的实例化。
 * - 类的静态属性只会在第一次加载类的时候初始化，所以在这里，JVM帮助我们保证了线程的安全性，在类进行初始化时，别的线程是无法进入的。
 * - 优点：避免了线程不安全，利用静态内部类特点实现延迟加载，效率高
 *
 * @author lakeqiu
 */
public class SingletonObject6 {
    private SingletonObject6() {
    }

    private static class InstanceHolder{
        private final static SingletonObject6 instance = new SingletonObject6();
    }

    public SingletonObject6 getInstance(){
        return InstanceHolder.instance;
    }
}
