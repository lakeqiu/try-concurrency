package com.lakeqiu.base.design.charpter1;

/**
 *  版本4：双重验证
 *  先进行判断instance是否为空，为空的话再进入，锁住共享变量，再一次判断是否为空，为空的话再来创建实例
 *  这样的话就避免了线程安全性问题与效率问题
 *
 *  缺点：可能会引起空指针异常
 *  分析：假如有两个线程a，b，线程a执行到1代码处，其会去初始化，假如这个实例的初始化花费的时间比较长，
 *  这时，线程b来到了2处，判断到instance不为空，于是直接拿去用，这时候instance还没有初始化完成，所以会报错
 *  JDK可能会帮我们优化而改变代码顺序而导致的
 *  @author lakeqiu
 */
public class SingletonObject4 {
    private static SingletonObject4 instance;

    private SingletonObject4() {
    }

    public static SingletonObject4 getInstance() {
        // 2
        if (instance == null){
            synchronized (SingletonObject4.class){
                if (instance == null){
                    // 1
                    SingletonObject4.instance = new SingletonObject4();
                }
            }
        }
        return instance;
    }
}
