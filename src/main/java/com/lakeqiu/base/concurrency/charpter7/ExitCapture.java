package com.lakeqiu.base.concurrency.charpter7;

/**
 * 让线程在被kill掉或出现异常时能通知一下并完成释放资源的功能
 * @author lakeqiu
 */
public class ExitCapture {
    public static void main(String[] args) throws InterruptedException {
        // TODO 这个钩子方法是怎么添加上去的
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " --> 出现异常");
            try {
                notifyAndRelease();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        int i = 0;
        while (true){
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " --> 工作中");
            i++;
            if (i > 5){
               throw new RuntimeException("error");
            }
        }

    }

    private static void notifyAndRelease() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " --> 通知管理员");
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName() + " --> 开始释放资源");
    }
}
