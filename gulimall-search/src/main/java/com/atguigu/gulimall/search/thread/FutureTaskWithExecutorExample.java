package com.atguigu.gulimall.search.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author djl
 * @create 2021/6/20 16:08
 */
public class FutureTaskWithExecutorExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(new Runnable() {
            @Override
            public void run() {
                try {
                    //simulating long running task
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("task finished");

            }
        }, "The result");

        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(futureTask);
        String s = futureTask.get();
        System.out.println(s);
        es.shutdown();
    }
}
