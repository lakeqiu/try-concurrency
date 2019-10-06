package com.lakeqiu.base.design.charpter16;

/**
 * 实现ActiveObject中MakeString方法的类
 * @author lakeqiu
 */
public class MakeStringRequest extends MethodRequest {
    /**
     * 要填到的字节个数、字符
     */
    private final int count;
    private final char fillChar;

    public MakeStringRequest(Servant servant, FutureResult futureResult, int count, char fillChar) {
        super(servant, futureResult);
        this.count = count;
        this.fillChar = fillChar;
    }

    @Override
    public void execute() {
        Result result = super.servant.makeString(count, fillChar);
        // 可能生产还没结束，先将result加入期货中，让用户去取
        super.futureResult.setResult(result);
    }
}
