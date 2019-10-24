package com.lakeqiu.base.juc.collections.charpter1;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * @author lakeqiu
 */
public class MySkipList {
    /**
     * 用来标识是数据节点还是头尾节点
     */
    private final static byte HEAD_NODE = -1;
    private final static byte DATA_NODE = 0;
    private final static byte TAIL_NODE = -1;

    private static class Node{
        /**
         * 值
         * 上下左右节点
         * 标识节点类型
         */
        private Integer value;
        private Node up = null, down, right, left;
        private byte bit;

        public Node(Integer value) {
            this(value, HEAD_NODE);
        }

        public Node(Integer value, byte bit) {
            this.value = value;
            this.bit = bit;
        }
    }

    /**
     * 头节点
     * 尾节点
     * 长度
     * 高度
     * 随机算法
     */
    private Node head;
    private Node tail;
    private int size;
    private int height;
    private Random random;

    public MySkipList() {
        // 设置头尾节点
        this.head = new Node(null, HEAD_NODE);
        this.tail = new Node(null, TAIL_NODE);
        // 头尾节点建立联系，将跳表连接起来
        this.head.right = this.tail;
        this.tail.left = this.head;
        // 初始化值
        this.size = 0;
        this.height = 0;
        random = new Random(System.currentTimeMillis());
    }

    public boolean isEmpty(){
        return this.size == 0;
    }


    private Node find(Integer element){
        // 将当前节点设置为头节点
        Node current = this.head;
        for (;;){
            // 检查当前节点是否为尾节点、与要寻找的值是否小于等于当前节点的值
            while (current.right.bit != TAIL_NODE && current.right.value <= element){
                // 是的话，进行下一个节点的对比，直到找出大于要寻找的值的节点
                current = current.right;
            }
            // 这层查找完成后，进行下一层的寻找
            if (current.down != null){
                current = current.down;
            }else {
                // 当前层是最后一层，退出循环
                break;
            }
        }
        // 返回的current<=element
        return current;
    }

    public boolean contains(Integer element){
        Node node = find(element);
        return node.value.equals(element);
    }

    public Integer get(Integer element){
        Node node = find(element);
        return node.value.equals(element) ? node.value : null;
    }

    public void add(Integer element){
        // 找寻这个元素应该存放的位置
        Node node = find(element);
        // 创建新的节点
        Node newNode = new Node(element, DATA_NODE);
        // 设置关系
        newNode.left = node;
        newNode.right = node.right;
        node.right.left = newNode;
        node.right = newNode;

        // 当前层为第0层
        int currentHeight = 0;
        // 确定是否要提一层
        while (random.nextDouble() < 0.3d){
            if (currentHeight >= this.height){
                // 高度增加一层
                this.height++;

                // 如果提级之后的级数超过当前高度
                // 设置头尾节点
                Node dumpHead = new Node(null, HEAD_NODE);
                Node dumpTail = new Node(null, TAIL_NODE);

                dumpHead.right = dumpTail;
                dumpHead.down = this.head;
                this.head.up = dumpHead;

                dumpTail.left = dumpHead;
                dumpTail.down = this.tail;
                this.tail.up = dumpTail;

                // 重新设置最高级的头尾节点
                this.head = dumpHead;
                this.tail = dumpTail;

            }

            // 向前遍历找寻是上一层元素的节点
            while (node != null && node.up == null){
                node = node.left;
            }
            // 找寻到了上一层节点,取得上一层节点信息
            node = node.up;

            // 修改关系
            Node upNode = new Node(element, DATA_NODE);
            upNode.left = node;
            upNode.right = node.right;
            node.right.left = upNode;
            node.right = upNode;

            // 设置其与下一层自己的关系
            upNode.down = newNode;
            newNode.up = upNode;
            // 如果要再一次提级的话，upNode就变成了newNode
            newNode = upNode;

            // 层级加1
            currentHeight++;
        }

        this.size++;
    }

    public void dumpSkipList(){
        Node temp = this.head;
        int i = this.height + 1;
        while (temp != null){
            System.out.printf("总高度 [%d] 当前高度 [%d]", this.height + 1, i--);
            Node node = temp.right;
            while (node.bit == DATA_NODE){
                System.out.print("->" + node.value);
                node = node.right;
            }
            System.out.println();
            temp = temp.down;
        }
        System.out.println("=================================");
    }

    public static void main(String[] args) {
        MySkipList list = new MySkipList();
        IntStream.range(0, 1000).boxed().forEach(i -> list.add(i));
//        list.add(1);
        Integer integer = list.get(11);
        list.dumpSkipList();
    }
}
