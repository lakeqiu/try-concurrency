package com.lakeqiu.base.design.charpter13.version2;

/**
 * 可以将其看成一个任务
 * @author lakeqiu
 */
public class Message {
    private final String value;

    public Message(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
