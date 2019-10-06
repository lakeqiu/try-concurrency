package com.lakeqiu.base.design.charpter16;

/**
 * 实现ActiveObject中displayText方法的类
 * @author lakeqiu
 */
public class DisplayStringRequest extends MethodRequest {
    private final String text;

    protected DisplayStringRequest(Servant servant, FutureResult futureResult, String text) {
        super(servant, futureResult);
        this.text = text;
    }

    @Override
    public void execute() {
        super.servant.displayText(text);
    }
}
