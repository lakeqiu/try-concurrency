package com.lakeqiu.base.design.charpter1;

/**
 * 单例模式：
 *  懒汉式：
 *  有点：需要时加载，不会浪费内存
 *  缺点：线程不安全
 * @author lakeqiu
 */
public class SingletonObject2 {
    private static SingletonObject2 instance;

    private SingletonObject2() {
    }

    public static SingletonObject2 getInstance() {
        /**
         * 如果线程a,b同时来到1处，线程a刚判断完要执行2，时间片却消耗完了
         * 这时候轮到线程b，由于线程a还没有创建实例，故线程b同样可以进入执行2
         * 这时候加上线程a创建的实例，就有两个实例了
         */


        // 1
        if (SingletonObject2.instance == null){
            // 2
            SingletonObject2.instance = new SingletonObject2();
        }

        return SingletonObject2.instance;
    }
}
