package com.lakeqiu.base.design.charpter4.observer;

/**
 * 观察者
 * @author lakeqiu
 */
public class SimpleObserver extends Observer {
    public SimpleObserver(Subject subject) {
        super(subject);
    }

    @Override
    public void update() {
        System.out.println("SimpleObserver:" + this.subject.getState());
    }
}
