package com.lakeqiu.base.design.charpter4.observer;

/**
 * 观察者
 * @author lakeqiu
 */
public class OtherObserver extends Observer {
    public OtherObserver(Subject subject) {
        super(subject);
    }

    @Override
    public void update() {
        System.out.println("OtherObserver:" + this.subject.getState());
    }
}
