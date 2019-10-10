package com.lakeqiu.base.juc.atomic.charpter6;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 使用Unsafe绕过Person的构造初始化获取Person
 * @author lakeqiu
 */
public class UnsafeTest {
    public static void main(String[] args) throws Exception {
        // 使用Unsafe绕过Person的构造初始化获取Person
        Unsafe unsafe = getUnsafe();
        Person person = (Person) unsafe.allocateInstance(Person.class);
        System.out.println(person.getL());
    }

    /**
     * 获取Unsafe
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    static Unsafe getUnsafe() throws NoSuchFieldException, IllegalAccessException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        return (Unsafe) f.get(null);
    }


    static class Person{
        private long l = 0;

        public Person(){
            l = 10;
            System.out.println("999999");
        }

        public long getL() {
            return l;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "l=" + l +
                    '}';
        }
    }
}
