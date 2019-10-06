package com.lakeqiu.base.design.charpter16;

/**
 * 发出“生成字符串”请求的线程
 * @author lakeqiu
 */
public class MakerClientThread extends Thread {
    private final ActiveObject activeObject;
    private final char fillChar;

    public MakerClientThread(ActiveObject activeObject, String name) {
        super(name);
        this.activeObject = activeObject;
        this.fillChar = name.charAt(0);
    }

    @Override
    public void run() {
        try {
            for(int i = 0;true;i++){
                Result result = activeObject.makeString(i + 1, fillChar);
                Thread.sleep(20);
                String value = (String) result.getResultValue();
                System.out.println(Thread.currentThread().getName()+": value="+value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
