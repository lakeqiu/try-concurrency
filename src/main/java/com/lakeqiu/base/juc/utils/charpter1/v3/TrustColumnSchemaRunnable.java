package com.lakeqiu.base.juc.utils.charpter1.v3;

/**
 * 负责校验ColumnSchema（数据）的逻辑代码
 * @author lakeqiu
 */
public class TrustColumnSchemaRunnable implements Runnable {

    private final Table table;

    private final TaskBatch taskBatch;

    public TrustColumnSchemaRunnable(Table table, TaskBatch taskBatch) {
        this.table = table;
        this.taskBatch = taskBatch;
    }

    @Override
    public void run() {
        try {
            // 模拟传输数据
            // 数据很大，肯定没有传输那么快
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 校验
        table.setColumnSchema(table.getTargetColumnSchema());
        // 通知已经校验完了
        taskBatch.done(table);
        System.out.println(table.getTableName() + " --> 校验完ColumnSchema");
    }
}
