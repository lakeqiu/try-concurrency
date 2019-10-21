package com.lakeqiu.base.juc.executor.charpter2;

/**
 *                  |-->Thread1
 *                  |-->Thread2
 * request--> db--> |-->Thread3
 *                  |-->Thread4
 *                  |-->Thread5
 * 如果有时候任务的代码是别人给我们的，但是我们又想要进行并行化处理，并且在
 * 任务前后做一些工作。这时候，我们可以继承Runnable，使用模板设计模式，将具体实现
 * 交由子类实现。
 * @author lakeqiu
 */
public abstract class MyTask implements Runnable {
    protected final int number;

    protected MyTask(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        try {
            this.doinit();
            this.doTask();
            this.doSuccess();
        }catch (Throwable cause){
            this.error(cause);
        }
    }

    /**
     * 执行过程中出错
     * @param cause
     */
    protected abstract void error(Throwable cause);

    /**
     * 任务成功后做什么
     */
    protected abstract void doSuccess();

    /**
     * 做任务
     */
    protected abstract void doTask();

    /**
     * 初始化
     */
    protected abstract void doinit();
}
