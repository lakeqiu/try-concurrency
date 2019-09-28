package com.lakeqiu.base.concurrency.charpter4;

/**
 * @author lakeqiu
 */
public class ClassLock implements Runnable{
    @Override
    public void run() {
        synchronized (A.class){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new ClassLock()).start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        A a = new A();
        System.out.println(a);
    }
}

class A{
}
