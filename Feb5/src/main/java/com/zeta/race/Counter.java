package com.zeta.race;

public class Counter {
    public int count=0;
    void increment(){
        System.out.println(count);
        synchronized (this){
            for(;;)
                count++;
        }
    }
    public synchronized int getValue(){
        return count;
    }
}
