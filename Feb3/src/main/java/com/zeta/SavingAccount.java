package com.zeta;

public class SavingAccount extends Account{

    public SavingAccount(int i) {
        super(i);
    }

    @Override
    public float deposit(float amount) {
        float currentBalance = getBalance();
        setBalance(currentBalance + amount);
        return getBalance();
    }

    @Override
    public float withdraw(float amount) {
        float currentBalance = getBalance();
        validateAmount(amount);
        if(currentBalance < amount){
            throw new IllegalArgumentException("Insufficient Balance");
        }
        setBalance(currentBalance - amount);
        return getBalance();
    }
}
