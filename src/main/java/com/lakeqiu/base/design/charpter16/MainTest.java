package com.lakeqiu.base.design.charpter16;

/**
 * @author lakeqiu
 */
public class MainTest {
    public static void main(String[] args) {
        ActiveObject activeObject = ActiveObjectFactory.creatActiveObject();
        new MakerClientThread(activeObject, "Alice").start();
        new MakerClientThread(activeObject, "Bobby").start();

        new DisplayClientThread(activeObject, "Chris").start();

    }
}
