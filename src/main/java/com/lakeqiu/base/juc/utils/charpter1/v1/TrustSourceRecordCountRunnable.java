package com.lakeqiu.base.juc.utils.charpter1.v1;

/**
 * 负责校验SourceRecordCount（表传过来的条数对不对）的逻辑代码
 * @author lakeqiu
 */
public class TrustSourceRecordCountRunnable implements Runnable {
    private final Table table;

    public TrustSourceRecordCountRunnable(Table table) {
        this.table = table;
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
        table.setTargetCount(table.getSourceRecordCount());

        System.out.println(table.getTableName() + " --> 校验完SourceRecordCount");
    }
}
