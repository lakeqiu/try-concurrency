package com.lakeqiu.base.design.charpter8.v2;

/**
 * 版本2
 * 测试类
 * @author lakeqiu
 */
public class MainTest {
    public static void main(String[] args) throws InterruptedException {
        FutureService service = new FutureService();
        Future<String> future = service.submit(() -> {
            System.out.println("任务开始");
            try {
                Thread.sleep(2000);
                System.out.println("任务结束，返回结果");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "1234567890";
        }, System.out::println);

        System.out.println("主线程");
        Thread.sleep(1900);
        /*while (true){
            System.out.println("gigigi");
            Thread.sleep(10);
        }*/
    }
}
