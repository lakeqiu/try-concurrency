package com.lakeqiu.base.juc.executor.charpter5;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 这个的技巧就是在任务里设置一个标志位，当任务执行完成就设置一些标志位
 * 这样遍历后就知道哪些任务没有完成了
 * @author lakeqiu
 */
public class ExecutorCompletionServiceTest3 {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(4, 6, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(7),
                r -> new Thread(r), new ThreadPoolExecutor.AbortPolicy());

        ExecutorCompletionService<Integer> service = new ExecutorCompletionService<>(poolExecutor);
        // 将任务弄成集合
        List<MyTask<Integer>> tasks = IntStream.rangeClosed(1, 10).boxed().map(MyTask::new).collect(Collectors.toList());
        // 执行任务
        tasks.forEach(service::submit);
        TimeUnit.SECONDS.sleep(5);
        poolExecutor.shutdownNow();
        // 根据标志位判断哪些任务没有完成并输出
        tasks.stream().filter(c -> !c.isSuccess()).forEach(System.out::println);

    }

    private static class MyTask<V> implements Callable {
        private final V value;
        private boolean success = false;

        public MyTask(V value) {
            this.value = value;
        }

        @Override
        public V call() throws Exception {
            System.out.println(value + " --> 开始执行任务");
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
            System.out.println(value + " --> 结束任务");
            success = true;
            return value;
        }

        public boolean isSuccess(){
            return success;
        }
    }
}
