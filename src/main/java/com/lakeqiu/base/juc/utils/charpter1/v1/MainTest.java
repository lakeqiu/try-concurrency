package com.lakeqiu.base.juc.utils.charpter1.v1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lakeqiu
 */
public class MainTest {
    /**
     * 生成表
     * 模拟传过来的表
     * @param event 判断是哪个事件的
     * @return
     */
    private static List<Table> capture(Event event){
        List<Table> list = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            list.add(new Table("table-" + event.getId() + "-" + i, i * 1000));
        }

        return list;
    }

    public static void main(String[] args) {
        // 创建事件
        Event[] events = {new Event(1), new Event(2)};
        // 线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (Event event : events) {
            // 模拟传输过来的表数据
            List<Table> tables = capture(event);
            for (Table table : tables) {
                TrustColumnSchemaRunnable trustColumnSchemaRunnable = new TrustColumnSchemaRunnable(table);
                TrustSourceRecordCountRunnable trustSourceRecordCountRunnable = new TrustSourceRecordCountRunnable(table);
                // 开始执行校验任务，但此时这个任务的平散执行的
                executorService.submit(trustColumnSchemaRunnable);
                executorService.submit(trustSourceRecordCountRunnable);
            }
        }
    }
}
