package com.lakeqiu.base.design.charpter16;


/**
 * 真正接收异步消息的主动对象
 * @author lakeqiu
 */
public class Servant implements ActiveObject {

    @Override
    public Result makeString(int count, char fillChar) {
        char[] chars = new char[count];
        for (int i = 0; i < count; i++){
            chars[i] = fillChar;
            // 模拟工作时间长
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new RealResult(new String(chars));
    }

    @Override
    public void displayText(String text) {
        System.out.println("显示 --> " + text);
    }
}
