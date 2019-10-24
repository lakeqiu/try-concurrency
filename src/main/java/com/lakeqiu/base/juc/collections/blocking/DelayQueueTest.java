package com.lakeqiu.base.juc.collections.blocking;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author lakeqiu
 */
public class DelayQueueTest {
    public static void main(String[] args) throws InterruptedException {
        DelayQueue<DelayElement<String>> delayQueue = new DelayQueue<>();

        delayQueue.add(new DelayElement<String>("123", 2000));
        delayQueue.add(new DelayElement<String>("13", 1000));
        delayQueue.add(new DelayElement<String>("23", 500));

        System.out.println(delayQueue.take());
        System.out.println(delayQueue.take());
        System.out.println(delayQueue.take());
    }

    static class DelayElement<E> implements Delayed{
        /**
         * 值、过期时间
         */
        private final E e;
        private final long expireTime;

        public DelayElement(E e, long expireTime) {
            this.e = e;
            this.expireTime = System.currentTimeMillis() + expireTime;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long diff = expireTime - System.currentTimeMillis();
            return unit.convert(diff, TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            DelayElement that = (DelayElement) o;
            long diff = this.expireTime - that.expireTime;
            if (diff <= 0){
                return -1;
            }
            return 1;
        }

        @Override
        public String toString() {
            return "DelayElement{" +
                    "e=" + e +
                    ", expireTime=" + expireTime +
                    '}';
        }
    }
}
