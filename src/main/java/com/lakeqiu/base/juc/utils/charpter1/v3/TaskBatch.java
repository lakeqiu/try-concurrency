package com.lakeqiu.base.juc.utils.charpter1.v3;

import java.util.concurrent.CountDownLatch;

/**
 * @author lakeqiu
 */
public class TaskBatch implements Watch {
    private CountDownLatch latch;
    //
    private TaskGroup taskGroup;

    public TaskBatch(int size, TaskGroup taskGroup) {
        this.taskGroup = taskGroup;
        this.latch = new CountDownLatch(size);
    }

    @Override
    public void done(Table table) {
        // 校验线程校验完成后通知这个，由TaskBatch等待一个表校验完成后进行通知
        latch.countDown();
        if (latch.getCount() == 0){
            System.out.println(table.getTableName() + " --> 校验完了 --> " + table);

            // 1、通知TaskGroup这个表已经做完了
            taskGroup.done(table);
        }
    }
}
