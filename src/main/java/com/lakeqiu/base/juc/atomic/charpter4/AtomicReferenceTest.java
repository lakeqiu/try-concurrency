package com.lakeqiu.base.juc.atomic.charpter4;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author lakeqiu
 */
public class AtomicReferenceTest {
    public static void main(String[] args) {
        AtomicReference<Person> atomicReference = new AtomicReference<>(new Person("lake", 20));
        System.out.println(atomicReference.get().toString());
    }

    static class Person{
       private String name;
       private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
