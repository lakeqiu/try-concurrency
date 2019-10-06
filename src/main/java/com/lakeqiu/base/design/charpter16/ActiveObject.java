package com.lakeqiu.base.design.charpter16;

/**
 * 接收异步消息的主动对象
 * @author lakeqiu
 */
public interface ActiveObject {
    /**
     * 组合字符
     * 如makeString(3, 'a')  --> "aaa"
     * @param count 字符个数
     * @param fillChar 字节
     * @return
     */
    Result makeString(int count, char fillChar);

    /**
     * 展示文本
     * @param text 文本
     */
    void displayText(String text);
}
