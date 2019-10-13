package com.lakeqiu.base.juc.utils.charpter1.v3;

import java.util.concurrent.CountDownLatch;

/**
 * @author lakeqiu
 */
public class TaskGroup implements Watch {
    private CountDownLatch latch;
    private Event event;

    public TaskGroup(int size, Event event) {
        this.latch = new CountDownLatch(size);
        this.event = event;
    }

    @Override
    public void done(Table table) {
        latch.countDown();
        if (latch.getCount() == 0){
            System.out.println("Event --> " + event.getId() + "已经结束");
        }
    }
}
