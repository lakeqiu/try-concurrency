package com.lakeqiu.base.concurrency.charpter1.version3.strategy;

/**
 * @author lakeqiu
 */
public class CalculatorMain {

    public static void main(String[] args) {
        Calculator calculator = new Calculator(20000d, 10000d, new SampleCalculatorStrategy());

        System.out.println(calculator.calculate());
    }
}
