package com.lakeqiu.base.design.charpter4.observer;

/**
 * 观察者模式
 *  观察者
 * @author lakeqiu
 */
public abstract class Observer {

    protected Subject subject;

    public Observer(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    public abstract void update();
}
