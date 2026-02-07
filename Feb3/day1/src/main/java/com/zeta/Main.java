package com.zeta;

import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Account account1=new SavingAccount(123);
        Account account2=new SavingAccount(124);
//        account.type = ACCOUNT_TYPE.SAVINGS;
        Account current = new CurrentAccount(999);
        account1.deposit(100);
        account2.deposit(80);
        Bank bank = new Bank();
        System.out.println("Previous Balances are"+account1.getBalance()+" "+account2.getBalance());
        boolean transfered=bank.transfer(account1,account2,200);
        System.out.println("is transferred?: "+transfered);
        System.out.println("updated Balances are"+account1.getBalance()+" "+account2.getBalance());


        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(current);
        accounts.forEach(account3 -> {
            account3.deposit(2000);
            account3.withdraw(2000);
        });
    }
}