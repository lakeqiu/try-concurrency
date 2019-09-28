package com.lakeqiu.base.concurrency.charpter2;

/**
 * 如果栈帧比较大（局部变量表比较大等），那么栈可以压的帧就比较少
 * @author lakeqiu
 */
public class ThreadExplain {

    /**
     * int i;byte[] bytes放在方法区
     * 数据放在堆里
     */
    private static int i = 0;
    private byte[] bytes = new byte[1024];

    /**
     * 虚拟机会启动一个线程（主线程），启动main函数
     *
     * 虚拟机栈每个线程一个
     */
    public static void main(String[] args) {
        // 放在局部变量表
        int j = 0;
        // bytes放在局部变量表中，只不过是个数组，实例放在堆里
        byte[] bytes = new byte[1024];

        try {
            add(1);
        }catch (Error e){
            System.out.println(i);
        }

    }

    static void add(int j){
        ++i;
        add(j + 1);
    }
}
