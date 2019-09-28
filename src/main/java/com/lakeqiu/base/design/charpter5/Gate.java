package com.lakeqiu.base.design.charpter5;

/**
 * 单线程执行模式
 * @author lakeqiu
 */
public class Gate {
    /**
     * 大门通过人数
     */
    private int counter = 0;
    /**
     * 通过的人
     */
    private String name = "NoBody";
    /**
     * 通过的人从哪里来
     */
    private String address = "NoWhere";

    /**
     * 临界值
     *
     * @param name
     * @param address
     */
    public synchronized void pass(String name, String address) {
        this.counter++;
        this.name = name;
        this.address = address;
        verify();
    }

    /**
     * 会进行检查，如果不符合要求，就不让通过
     */
    private void verify() {
        if (this.name.charAt(0) != this.address.charAt(0)) {
            System.out.println("*******BROKEN********" + toString());
        }
    }

    @Override
    public synchronized String toString() {
        return "No." + counter + ":" + name + "," + address;
    }
}
