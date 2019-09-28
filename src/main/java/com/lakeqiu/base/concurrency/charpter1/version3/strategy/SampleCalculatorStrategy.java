package com.lakeqiu.base.concurrency.charpter1.version3.strategy;

/**
 * 策略实例类
 * @author lakeqiu
 */
public class SampleCalculatorStrategy implements CalculatorStrategy {
    @Override
    public double calculate(double salary, double bonus) {
        return salary * 0.9 + bonus * 1.0;
    }
}
