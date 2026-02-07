package com.zeta.thread;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Worker worker1 = new Worker("staff1");
        worker1.start();
        worker1.sleep(3000);
        Worker worker2=new Worker("staff2");
        worker2.start();
        worker1.wait(1000);
    }
}