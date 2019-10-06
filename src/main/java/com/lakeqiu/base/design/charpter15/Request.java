package com.lakeqiu.base.design.charpter15;

/**
 * 请求
 * @author lakeqiu
 */
public class Request {
    /**
     * 请求名字、编号
     */
    private final String name;
    private final int number;

    public Request(String name, int number) {
        this.name = name;
        this.number = number;
    }

    /**
     * 任务的业务代码
     */
    public void execute(){
        System.out.println(Thread.currentThread().getName() + "执行了 --> " + this);
    }

    @Override
    public String toString() {
        return "Request{" +
                "name='" + name + '\'' +
                ", number=" + number +
                '}';
    }
}
