package com.zeta.thread;

public class Worker  extends Thread{
    public Worker(String name){
        super(name);
    }
    public void run(){
        for(int i=1;i<10;++i){
            try{
                System.out.println("thread"+Thread.currentThread());
                Thread.sleep(1000);

            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
