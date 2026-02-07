package com.zeta;

public abstract class Account {
    private float balance;  //mutable
    ACCOUNT_TYPE type;
    private final int number;// immutable property
    Address address; //Has-A

    public abstract float deposit (float amount);
    public abstract float withdraw(float amount);

    public int getNumber() {
        return number;
    }

    public ACCOUNT_TYPE getType() {
        return type;
    }
    public void validateAmount(float amount){
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }
    public void setType(ACCOUNT_TYPE type) {
        this.type = type;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Account(int number) {
        this.number = number;
    }
}
