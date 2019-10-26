package com.lakeqiu.base.juc.collections.concurrents;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @author lakeqiu
 */
public class MyLockFreeQueue<E> {
    private AtomicReference<Node<E>> first, last;
    private AtomicInteger size;

    public MyLockFreeQueue() {
        // 添加为空的时候为什么是这样添加，不能直接null
        Node<E> node = new Node<>(null);
        this.first = new AtomicReference<>(node);
        this.last = new AtomicReference<>(node);
        this.size = new AtomicInteger(0);
    }

    /**
     * 在尾部添加元素
     * 这段代码是如何保证线程安全的呢？ CAS
     *  假如当前队列last节点为：o1，这时候有两个线程
     *
     *  T1                              T2
     *
     *  n1 = new Node
     *  getAndSet方法取得节点为o1
     *  t1的时间片用完了
     *                                  T2开始
     *                                  n2 = new Node
     *                                  getAndSet方法取得节点为o1
     *                                  修改节点时发现取得节点也为o1，故修改节点为n2
     *  修改节点时发现取得节点为n2
     *  与之前取得节点o1不同
     *  重新获取修改
     *
     * @param element 元素
     */
    public void addLast(E element){
        if (null == element){
            throw new RuntimeException("值不能为空");
        }
        Node<E> newNode = new Node<>(element);
        if (this.first.get() == null){
            this.first.compareAndSet(null, newNode);
        }
        Node<E> preNode = this.last.getAndSet(newNode);
        preNode.setNext(newNode);
        // 保证了重排序不会将size加的操作放到未修改成功时，
        // size++ 则保证不了
        size.incrementAndGet();
    }

    /**
     * 移除头部元素并返回
     * @return E
     */
    public E removeFirst(){
        Node<E> headNode, valueNode;
        do {
            // 一个存放头节点，一个存放头节点的下一个节点（将要修改为头节点）
            headNode = first.get();
            valueNode = headNode.getNext();
        // while(如果头节点没有被修改过，就将之前获取的头节点的下一个节点设置为头节点)
        }while (valueNode != null && this.first.compareAndSet(headNode, valueNode));

        E e = (valueNode == null) ? null : valueNode.getElement();

        // 加快valueNode的回收
        if (valueNode != null){
            valueNode = null;
        }
        // size - 1
        this.size.decrementAndGet();

        return e;
    }

    public boolean isEmpty(){
        return this.size.get() == 0;
    }

    private static class Node<E> {
        private final E element;
        // 一个变化，为什么要加？
        private volatile Node next;

        public Node(E element) {
            this.element = element;
            this.next = null;
        }

        public E getElement() {
            return element;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return (element == null) ? "" : element.toString();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final MyLockFreeQueue<String> queue = new MyLockFreeQueue<>();

        IntStream.range(0, 5).boxed().forEach(i -> {
            new Thread(() -> {
                for (int j = 0; j< 1000; j++){
                    String s = UUID.randomUUID().toString();
                    queue.addLast(s);
                    System.out.println(s);
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        });

        TimeUnit.SECONDS.sleep(5);
        IntStream.range(0, 2).boxed().forEach(i -> {
            new Thread(() -> {
                while (!queue.isEmpty()){
                    String s = queue.removeFirst();
                    System.out.println("[" + i + "] --> " + s);
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        });
    }
}
