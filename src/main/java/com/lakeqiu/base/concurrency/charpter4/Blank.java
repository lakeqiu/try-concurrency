package com.lakeqiu.base.concurrency.charpter4;


/**
 * 还记得我们之前谈过的银行问题吗？
 *  其实，之前的例子中存在一个非常大的安全隐患，就是线程安全问题。如果在经过判断进去while代码块后，
 *  中间还需要执行大量业务代码，那么输出叫号的结果是什么呢？请看下面代码：
 *
 *  怎么输出最大变成52了，不是应该是50吗？
 *  如果有a、b、c三个线程，当a判断通过来到2位置（此时index=49），时间片用完；轮到线程b，线程b同样也是来到2位置时间片
 *  就用完了，轮到线程c也是一样（因为a还没有执行到index++的代码，故b、c可以进去）。之后又轮到线程a了，线程a执行index++，
 *  index变为50，结束。线程b、c同理，使得index变为51、52。
 *
 *  其实这个问题就是原子性问题，因为中间的几步不是原子性操作，我们使用synchronize锁住对象后便可以是其变为原子性操作
 *  但是，synchronize锁住的代码就变为单线程，效率会下降
 * @author lakeqiu
 */
public class Blank {

    public static void main(String[] args) {
        TicketWindowsRunnable runnable = new TicketWindowsRunnable();

        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();

        runnable.get();
        System.out.println("111");
    }
}
