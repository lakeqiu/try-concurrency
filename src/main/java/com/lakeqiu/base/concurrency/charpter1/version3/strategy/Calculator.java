package com.lakeqiu.base.concurrency.charpter1.version3.strategy;

/**
 * 工资计算器
 * @author lakeqiu
 */
public class Calculator {
    /**
     * 基本工资
     */
    private double salary;
    /**
     * 奖金
     */
    private double bonus;

    /**
     * 策略接口
     */
    private CalculatorStrategy calculatorStrategy;

    public Calculator(double salary, double bonus, CalculatorStrategy calculatorStrategy) {
        this.salary = salary;
        this.bonus = bonus;
        this.calculatorStrategy = calculatorStrategy;
    }

    /**
     * 计算工资
     * @return
     */
    public double calculate(){
        return calculatorStrategy.calculate(salary, bonus);
    }
}
