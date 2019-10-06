package com.lakeqiu.base.design.charpter16;

/**
 * 对应ActiveObject的每一个方法
 * @author lakeqiu
 */
public abstract class MethodRequest {
    /**
     * 实际执行方法逻辑的类、期货
     * 要让子类用，注意权限修饰符
     */
    protected final Servant servant;
    protected final FutureResult futureResult;

    protected MethodRequest(Servant servant, FutureResult futureResult) {
        this.servant = servant;
        this.futureResult = futureResult;
    }

    /**
     * ActiveObject里的类实际由这个方法来执行
     */
    public abstract void execute();

}
