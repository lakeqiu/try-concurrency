package com.lakeqiu.base.design.charpter4.threadlife;

import java.util.List;

/**
 * 观察者实现类
 * @author lakeqiu
 */
public class ThreadLifeCycleListener implements LifeCycleListener{

    public void concurrentQuery(List<String> ids) {
        if (ids == null || ids.isEmpty()){
            return;
        }
        // 被观察者的业务代码
        ids.stream().forEach(id -> new Thread(new ObservableRunnable(this) {
            @Override
            public void run() {
                try {
                    notifyChange(new RunnableEvent(RunnableState.RUNNING, Thread.currentThread(), null));
                    System.out.println("query for the id " + id);
                    Thread.sleep(1000L);
                    notifyChange(new RunnableEvent(RunnableState.DONE, Thread.currentThread(), null));
                } catch (Exception e) {
                    notifyChange(new RunnableEvent(RunnableState.ERROR, Thread.currentThread(), e));
                }
            }
        }, id).start());
    }

    @Override
    public void onEvent(ObservableRunnable.RunnableEvent event) {
        synchronized (LOCK){
            System.out.println("[线程" + event.getThread().getName() + "]状态改变了-->" + event.getRunnableState());

            // 如果出异常结束，输出异常原因
            if (event.getCause() != null){
                System.out.println("[线程" + event.getThread().getName() + "]出异常了-->" + event.getCause());
            }
        }
    }
}
