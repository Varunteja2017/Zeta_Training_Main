package com.zeta.race;

public class RaceDemo{
    public static void main(String[] args) throws InterruptedException {
        Counter counter=new Counter();
        Thread t1=new Worker1(counter);
        t1.start();
        System.out.println("from 1 "+counter.getValue());
        Thread t2=new Worker1(counter);
        System.out.println("from 2 "+counter.getValue());
        t2.start();
        t2.join();
        System.out.println("from 3 "+counter.getValue());
    }
}