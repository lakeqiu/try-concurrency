package com.lakeqiu.base.design.charpter7.v2;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 比较不可变对象与synchronize的效率
 * @author lakeqiu
 */
public class CompareImmutable {

    public static void main(String[] args) {
        List<Thread> threads = new LinkedList<>();
        /*Person person = new Person();
        person.setName("Person");*/

        ImmuPerson person = new ImmuPerson("ImmuPerson");

        long l = System.currentTimeMillis();
        IntStream.rangeClosed(0, 5).forEach(i -> {
            Thread t = MyThread.createThread(person);
            t.start();
            threads.add(t);
        });

        threads.stream().forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(System.currentTimeMillis() - l);
    }
}

final class ImmuPerson{
    private final String name;

    public ImmuPerson(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ImmuPerson{" +
                "name='" + name + '\'' +
                '}';
    }
}

class Person{
    private String name;

    public synchronized void setName(String name) {
        this.name = name;
    }

    @Override
    public synchronized String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}

class MyThread{
    public static Thread createThread(ImmuPerson person){
        return new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < 100000; i++){
                    System.out.println(person.toString());
                }
            }
        };
    }
}
