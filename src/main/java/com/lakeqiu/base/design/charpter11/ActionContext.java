package com.lakeqiu.base.design.charpter11;

/**
 * 当前线程中许多方法需要使用到的
 *
 * 使用了单例模式
 * @author lakeqiu
 */
public class ActionContext {
    /**
     * 线程容器，用来存放Context上下文，让当前线程其他方法可以容易获取到
     */
    private static final ThreadLocal<Context> THREAD_LOCAL = new ThreadLocal<Context>(){
        @Override
        protected Context initialValue() {
            return new Context();
        }
    };

    /**
     * 静态内部类
     */
    private static class ActionHolder{
        private final static ActionContext actionContext = new ActionContext();
    }

    /**
     * 获取单例
     * @return ActionContext
     */
    public static ActionContext actionContext(){
        return ActionHolder.actionContext;
    }

    /**
     * 获取Context上下文
     * @return Context
     */
    public Context getContext(){
        return THREAD_LOCAL.get();
    }

    /**
     * 构造方法私有
     */
    private ActionContext(){

    }
}
