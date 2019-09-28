package com.lakeqiu.base.design.charpter1;

/**
 * 版本5：解决版本4可能会导致的空指针异常
 *  添加了volatile关键字
 *  这个关键字保证了内存可见性，现在暂时可以理解为其要求在读的时候，如果有在写，就会等到写完成才去读
 *  这个就可以先让初始化完成
 * @author lakeqiu
 */
public class SingletonObject5 {
    /**
     * 添加了volatile关键字
     */
    private static volatile SingletonObject5 instance;

    private SingletonObject5() {
    }

    public static SingletonObject5 getInstance() {
        if (instance == null){
            synchronized (SingletonObject5.class){
                if (instance == null){
                    instance = new SingletonObject5();
                }
            }
        }
        return instance;
    }
}
