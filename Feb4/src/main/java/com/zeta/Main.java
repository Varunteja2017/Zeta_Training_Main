package com.zeta;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Bank bank= new Bank();
        Customer customer1= new Customer("Varun");
        Customer customer2=new Customer("Zeta");
        bank.issuecard(customer1,CardProvider.Emerald);
        bank.issuecard(customer2,CardProvider.Infenium);
        System.out.println(bank.cards);
    }
}