package com.zeta;

public class CurrentAccount extends Account{
    public CurrentAccount(int number) {
        super(number);
    }

    @Override
    public float deposit(float amount) {
        return 0;
    }

    @Override
    public float withdraw(float amount) {
        return 0;
    }
}
