package com.lakeqiu.base.design.charpter8.v1;

/**
 * 版本1还是存在一些问题，
 * - get方法是轮询，不停的去问，很浪费时间
 * - 版本1结果需要自己去获取，可不可以让执行线程自己送过来，并执行我想要的对结果执行的代码
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
        });

        System.out.println("主线程");
        System.out.println(future.get());
    }
}
