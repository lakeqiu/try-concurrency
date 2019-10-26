package com.lakeqiu.base.juc.collections.concurrents;

import java.util.stream.IntStream;

/**
 * 自定义队列，线程不安全版
 * @author lakeqiu
 */
public class MyQueue<E> {

    private Node<E> first, last;
    private int size;

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    /**
     * 获取头部元素，不会删除
     * @return E
     */
    public E peekFirst(){
        return (null == this.first) ? null : this.first.getElement();
    }

    /**
     * 获取尾部元素，不会删除
     * @return E
     */
    public E peekLast(){
        return (null == this.last) ? null : this.last.getElement();
    }

    public void addLast(E element){
        Node<E> newNode = new Node<>(element);
        // 如果头部元素为空
        if (null == this.first){
            newNode.setNext(null);
            this.first = newNode;
            this.last = newNode;
        }
        // 因为if中设置了只有一个元素的话，头部和尾部元素都是同一个，
        // 所以在头部元素不为空的情况下，尾部元素不会为空
        this.last.setNext(newNode);
        this.last = newNode;
        this.size++;
    }

    public E removeFirst(){
        // 如果为空，返回null
        if (isEmpty()){
            return null;
        }
        E val = this.first.getElement();
        this.first = this.first.getNext();
        this.size--;
        // 如果size为0，说明没有元素了，需要把last节点置为空
        // 至于为什么，看addLast方法。
        if (this.size == 0){
            this.last = null;
        }
        return val;
    }

    @Override
    public String toString() {
        if (isEmpty()){
            return "[]";
        }
        StringBuffer buffer = new StringBuffer("[");
        Node node = this.first;
        while (null != node){
            buffer.append(node.toString() + ", ");
            node = node.next;
        }
        buffer.replace(buffer.length() - 2, buffer.length(), "]");
        return new String(buffer);
    }

    private static class Node<E> {
        private final E element;
        private Node next;

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

    public static void main(String[] args) {
        MyQueue<Integer> queue = new MyQueue<>();

        IntStream.range(0,8).forEach(queue::addLast);

        System.out.println(queue.toString());

        IntStream.range(0, 9).forEach(i -> queue.removeFirst());

        System.out.println(queue.toString());
    }
}
