package com.lakeqiu.base.design.charpter16;

/**
 * 用来将调用ActiveObject的方法装换给对象执行
 * 包访问权限
 * @author lakeqiu
 */
class ActiveObjectProxy implements ActiveObject {
    private final Servant servant;
    private final SchedulerThread schedulerThread;

    public ActiveObjectProxy(Servant servant, SchedulerThread schedulerThread) {
        this.servant = servant;
        this.schedulerThread = schedulerThread;
    }

    @Override
    public Result makeString(int count, char fillChar) {
        FutureResult futureResult = new FutureResult();
        // 存进去，schedulerThread有一个线程会一直执行轮询（也可以改造为线程池）
        schedulerThread.invoke(new MakeStringRequest(servant, futureResult, count, fillChar));
        return futureResult;
    }

    @Override
    public void displayText(String text) {
        FutureResult futureResult = new FutureResult();
        schedulerThread.invoke(new DisplayStringRequest(servant, futureResult, text));
    }
}
