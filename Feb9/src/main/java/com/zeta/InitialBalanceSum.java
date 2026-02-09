package com.zeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

class InitialBalanceSumTask extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 3;
    private final List<Account> accounts;
    private int start, end;

    InitialBalanceSumTask(List<Account> accounts, int start, int end) {
        this.accounts = accounts;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= THRESHOLD) {
            int sum = 0;
            for (int i = start; i < end; i++) {
                sum += accounts.get(i).getBalance();
            }
            return sum;
        } else {
            int mid = (start + end) / 2;
            InitialBalanceSumTask left = new InitialBalanceSumTask(accounts, start, mid);
            InitialBalanceSumTask right = new InitialBalanceSumTask(accounts, mid, end);

            left.fork();          // start async
            int rightResult = right.compute();
            int leftResult = left.join();

            return leftResult + rightResult;
        }
    }
}

public class InitialBalanceSum {
    public static void main(String[] args) {
        List<Account> accounts = new ArrayList<>();
        Random random= new Random();
        for (int i = 0; i<=20; i++){
            Account account=new SavingAccount(i);
            account.setBalance(random.nextInt(1000));
            accounts.add(account);
        }

        ForkJoinPool pool = new ForkJoinPool();
        int result = pool.invoke(new InitialBalanceSumTask(accounts, 0, accounts.size()));

        System.out.println("Sum = " + result);
    }
}