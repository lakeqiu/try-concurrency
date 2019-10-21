package com.lakeqiu.base.juc.collections.charpter1;

import java.util.stream.IntStream;

/**
 * @author lakeqiu
 */
public class MyLinkedList<E> {
    private Node<E> first = null;
    private Node<E> end;
    private int size = 0;

    public int size(){
        return this.size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void add(E e){
        final Node<E> node = new Node<>(e);
        if (this.first == null){
            this.first = node;
            this.end = node;
        }
        this.end.next = node;
        this.end = node;
        this.size++;
    }

    public void addFirst(E e){
        Node<E> node = new Node<>(e);
        node.next = this.first;
        this.size++;
        this.first = node;
    }

    public E removeFirst(){
        if (this.isEmpty()){
            throw new RuntimeException("为空");
        }
        Node<E> eNode = this.first;
        this.first = eNode.next;
        this.size--;
        return eNode.value;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer("[");
        Node node = this.first;
        while (node != null){
            buffer.append(node.toString() + ", ");
            node = node.next;
        }
        buffer.replace(buffer.length()-2, buffer.length(), "]");
        return new String(buffer);
    }

    private static class Node<E>{
        E value;
        Node<E> next;

        public Node(E value) {
            this.value = value;
        }

        @Override
        public String toString() {
            if (null != value){
                return value.toString();
            }
            return "null";
        }
    }

    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        IntStream.range(0, 10).boxed().forEach(i -> list.add(i));
        IntStream.range(10, 20).boxed().forEach(i -> list.addFirst(i));
        IntStream.range(10, 20).boxed().forEach(i -> System.out.println(list.removeFirst()));
        System.out.println(list);
        System.out.println(list.size());
    }
}
