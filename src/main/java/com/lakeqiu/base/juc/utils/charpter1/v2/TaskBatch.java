package com.lakeqiu.base.juc.utils.charpter1.v2;

import java.util.concurrent.CountDownLatch;

/**
 * @author lakeqiu
 */
public class TaskBatch implements Watch {
    private CountDownLatch latch;

    public TaskBatch(int size) {
        this.latch = new CountDownLatch(size);
    }

    @Override
    public void done(Table table) {
        // 校验线程校验完成后通知这个，由TaskBatch等待一个表校验完成后进行通知
        latch.countDown();
        if (latch.getCount() == 0){
            System.out.println(table.getTableName() + " --> 校验完了 --> " + table);
        }
    }
}
