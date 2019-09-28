package com.lakeqiu.base.concurrency.charpter1.version3.strategy;

/**
 * 策略接口
 * @author lakeqiu
 */
@FunctionalInterface
public interface CalculatorStrategy {
    /**
     * 策略方法，计算工资（不同实现税率不同）
     * @param salary 工资
     * @param bonus 奖金
     * @return 结果
     */
    double calculate(double salary, double bonus);
}
