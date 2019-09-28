package com.lakeqiu.base.design.charpter1;

/**
 * 版本7：枚举
 *  这借助JDK1.5中添加的枚举来实现单例模式。不仅能避免多线程同步问题，而且还能防止反序列化重新创建
 * 新的对象。
 *
 *  这种方式是Effective Java作者Josh Bloch提倡的方式
 * @author lakeqiu
 */
public class SingletonObject7 {
    private SingletonObject7() {
    }

    private enum Singleton{
        INSTANCE;

        private final SingletonObject7 instance;

        Singleton(){
            instance = new SingletonObject7();
        }

        public SingletonObject7 getInstance(){
            return instance;
        }
    }

    public SingletonObject7 getInstance(){
        return Singleton.INSTANCE.getInstance();
    }
}
