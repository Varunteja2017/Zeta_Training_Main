package com.zeta.race;

public class Worker1 extends Thread{
    private Counter counter;

    public Worker1(Counter counter){
        this.counter=counter;
    }
    public void run(){
        counter.increment();
    }
}
