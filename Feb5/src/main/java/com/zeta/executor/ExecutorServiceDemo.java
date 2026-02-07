package com.zeta.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Worker2 implements Runnable{

    @Override
    public void run() {
        System.out.println("worker");
    }
}
public class ExecutorServiceDemo {
    public static void main(String[] args) {
        ExecutorService executor= Executors.newSingleThreadExecutor();
        executor.submit(new Worker2());
        executor.shutdown();
    }
}
