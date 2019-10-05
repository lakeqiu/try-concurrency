package com.lakeqiu.base.design.charpter11;

/**
 * 当前线程函数许多方法需要使用到，但又不想一个一个方法传下去的
 *
 * 所以存在Context上下文中，而这个有存在线程上下文中，故可以不用传参取到
 * @author lakeqiu
 */
public class Context {
    private String name;

    private String cardId;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
