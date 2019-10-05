package com.lakeqiu.base.design.charpter10;

/**
 * @author lakeqiu
 */
public class MyMainTest {
    private static MyThreadLocal<String> threadLocal = new MyThreadLocal<String>(){
        @Override
        public String initialValue() {
            return "空值";
        }
    };

    public static void main(String[] args) {
        threadLocal.set("Thread-main");
        System.out.println(threadLocal.get());
    }
}
