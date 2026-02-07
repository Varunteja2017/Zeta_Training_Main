package com.zeta.thread;

class Buffer {
    private int data;
    private boolean available = false;

    synchronized void produce(int value) throws InterruptedException {
        while (available) wait(); 

        data = value;
        available = true;
        System.out.println("Produced: " + value);
        notify();
    }

    synchronized int consume() throws InterruptedException {
        while (!available) wait();

        available = false;
        System.out.println("Consumed: " + data);
        notify();
        return data;
    }
}

public class ProducerConsumerDemo {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 3; i++) {
                    buffer.produce(i);
                    Thread.sleep(500);
                }
            } catch (Exception e) {}
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 3; i++) {
                    buffer.consume();
                    Thread.sleep(700);
                }
            } catch (Exception e) {}
        });

        producer.start();
        consumer.start();
    }
}
