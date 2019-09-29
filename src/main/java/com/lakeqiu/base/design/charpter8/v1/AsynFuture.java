package com.lakeqiu.base.design.charpter8.v1;

/**
 * Future实现类
 * 继承Future类，用来给用户调用返回执行结果
 *  如果用户调用了get方法，其会先判断执行任务的线程是否已经执行完成并返回结果
 *  是的话直接将结果返回给用户，不是的话等待执行完成再返回给用户
 * @author lakeqiu
 */
public class AsynFuture<T> implements Future<T> {
    /**
     * 一个标志，判断任务是否已经执行完成
     */
    private boolean done = false;
    /**
     * 执行结果
     */
    private T result;

    /**
     * FutureService执行完成后会调用这个方法
     * 将结果给进来
     * @param result 结果
     */
    public void done(T result){
        // 由于下面的get方法会操作到done以及result，故需要上锁
        synchronized (this){
            // 获取结果，并将标志位改为执行完成
            this.result = result;
            this.done = true;
            // 唤醒其他线程(调用这个的是执行方法的线程，而调用get方法获取结果的是主线程或其他线程)
            this.notifyAll();
        }
    }


    @Override
    public T get() throws InterruptedException {
        synchronized (this){
            // 判断执行完成了没有，如果没有，就进入等待状态，等待执行任务线程的唤醒
            while (!done){
                this.wait();
            }
        }
        // 任务执行完成，返回结果
        return this.result;
    }
}
