package com.lakeqiu.base.design.charpter7.v1;

/**
 * @author lakeqiu
 */
public class UserPersonThread implements Runnable {
    private Person person;

    public UserPersonThread(Person person) {
        this.person = person;
    }

    @Override
    public void run() {
        while (true){
            System.out.println(Thread.currentThread().getName() + "-->" + person.toString());
        }
    }
}
