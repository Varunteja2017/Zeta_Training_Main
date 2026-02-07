package com.zeta.bank;

class BankAccount {
    private int balance;

    BankAccount(int balance) {
        this.balance = balance;
    }

    synchronized void withdraw(int amount) {
        System.out.println(Thread.currentThread().getName() + " trying to withdraw " + amount);

        if (balance >= amount) {
            System.out.println("Approved for " + Thread.currentThread().getName());
            try { Thread.sleep(500); } catch (Exception e) {}
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + " completed withdrawal");
        } else {
            System.out.println("Insufficient balance for " + Thread.currentThread().getName());
        }
    }

    int getBalance() {
        return balance;
    }
}

public class BankDemo {
    public static void main(String[] args) throws Exception {
        BankAccount acc = new BankAccount(100);

        Thread t1 = new Thread(() -> acc.withdraw(80), "T1");
        Thread t2 = new Thread(() -> acc.withdraw(80), "T2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Final balance = " + acc.getBalance());
    }
}
