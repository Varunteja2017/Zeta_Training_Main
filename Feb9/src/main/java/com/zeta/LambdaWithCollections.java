package com.zeta;

import java.util.ArrayList;
import java.util.List;

public class LambdaWithCollections {
    public static void main(String[] args) {
        showWithArrayList();
    }

    private static void showWithArrayList() {
        List<Account> accounts=new ArrayList<>();
        for (  int i=0; i<10; i++){
            accounts.add(new SavingAccount(i+1));
        }
        accounts.forEach(account -> System.out.println(account.getNumber()));
        System.out.println("====after sorting===");
        accounts.sort((account1,account2)->account2.getNumber()-account1.getNumber());
        accounts.removeIf(account ->account.getNumber()%2==0);
        accounts.forEach(account-> System.out.println(account.getNumber()));
    }
}
