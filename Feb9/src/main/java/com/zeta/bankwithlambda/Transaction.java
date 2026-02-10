package com.zeta.bankwithlambda;

import java.time.LocalDateTime;

public class Transaction {

    public enum Status { SUCCESS, FAILED }

    private Status status;
    private double amount; // +credit, -debit
    private LocalDateTime timestamp;
    private Integer otherAccountNumber;

    public Transaction(Status status, double amount, Integer otherAccountNumber) {
        this.status = status;
        this.amount = amount;
        this.otherAccountNumber = otherAccountNumber;
        this.timestamp = LocalDateTime.now();
    }

    public Status getStatus() { return status; }
    public double getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public Integer getOtherAccountNumber() { return otherAccountNumber; }
}
