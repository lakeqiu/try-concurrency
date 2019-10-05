package com.lakeqiu.base.design.charpter10;

/**
 * @author lakeqiu
 */
public class ThreadLocalTest {
    /**
     *  ThreadLocal设置默认值
     */
    private ThreadLocal<String> threadLocal = new ThreadLocal<String>(){
        @Override
        protected String initialValue() {
            return "空值";
        }
    };

    public static void main(String[] args) {

    }
}
