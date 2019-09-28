package com.lakeqiu.base.design.charpter4.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 被观察者
 * @author lakeqiu
 */
public class Subject {
    /**
     * 观察者列表
     */
    private List<Observer> observers = new ArrayList<>();

    private int state;

    /**
     * 添加观察者
     *  当实现接口或继承虚拟类时其构造方法会调用这个方法将自己添加进来
     * @param observer 观察者
     */
    public void attach(Observer observer){
        this.observers.add(observer);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        if (this.state == state){
            return;
        }
        this.state = state;
        // 状态变了，通知所有观察者
        notifyAllObserver();
    }

    /**
     * 执行所有观察者的方法
     */
    public void notifyAllObserver(){
        observers.stream().forEach(Observer::update);
    }
}
