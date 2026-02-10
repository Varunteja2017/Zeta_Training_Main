package com.zeta.bankwithlambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class BankAccount {
    private int balance;
    LoanAccount loan;
    int accountNumber;
    private List<Transaction> transactions = new ArrayList<>();
    public BankAccount(int balance, int accountNumber){
        this.balance=balance;
        this.accountNumber=accountNumber;
    }
    public synchronized void withdraw(float amount){
        System.out.println(Thread.currentThread().getName()+"Fetching Balance please wait...");
        validateAmount(amount);
        if (balance < amount) {
            throw new IllegalArgumentException(
                    "current Balance=" + balance + ", But the Requested amount=" + amount);
        } else {
            try{
                Thread.sleep(3000);
            }
            catch (Exception e){}
            balance-=amount;
        }

    }
    public synchronized  void deposit(int amount){
        try{
            Thread.sleep(3000);
        }
        catch (Exception e){}
        validateAmount(amount);
        balance+=amount;
    }
    static Predicate<Integer> loanEligibility = bal -> bal >= 5000;
    public void issueLoan(int tenure, int principle, int interestRate) {
        if (!loanEligibility.test(balance)) {
            throw new IllegalStateException("Not eligible for loan. Minimum balance ₹5000 required.");
        }
        else if(loan != null && loan.getStatus() == LoanStatus.ACTIVE) {  // FIXED
            throw new IllegalStateException("Active loan already exists.");
        }

        loan = new LoanAccount(tenure, principle, interestRate);
        System.out.println("Loan issued successfully.");
    }


    public void closeLoan() {
        if (loan==null || loan.getStatus() == LoanStatus.CLOSED ) {
            throw new IllegalStateException("No active loan to close.");
        }
        loan.closeLoan();
        System.out.println("Loan closed successfully.");
    }

    public static void validateAmount(float amount) {
        if(!validAmount.test(amount)) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        else if (!validTransactionAmount.test(amount)) {
            throw new IllegalArgumentException("Invalid transaction amount: " + amount);
        }
    }
    static Predicate<Float> validAmount = amt -> amt > 0;
    static Predicate<Float> maxLimit = amt -> amt <= 1_00_000;
    static Predicate<Float> validTransactionAmount = validAmount.and(maxLimit);


    public synchronized int getBalance() {
        return this.balance;
    }
    public int getAccountNumber(){
        return this.accountNumber;
    }
    public void showLoanDetails() {
        if (loan == null) {
            System.out.println("No loan taken.");
            return;
        }

        System.out.println("Loan Status: " + loan.getStatus());
        System.out.println("EMI Amount per month: ₹" + loan.getEmi());
        System.out.println("Remaining Balance to pay: ₹" + loan.getRemainingBalance());
    }

    Predicate<Double> hasEnoughBalance = amt -> balance >= amt;
    public synchronized void payLoanEMI(double amount) {
        if (loan == null || loan.getStatus() == LoanStatus.CLOSED) {
            throw new IllegalStateException("No active loans found.");
        }

        if (!hasEnoughBalance.test(amount)) {
            throw new IllegalArgumentException("Not enough balance to pay EMI.");
        }

        balance -= amount;
        loan.payEMI(amount);
    }

    public synchronized void addTransaction(Transaction t) {
        transactions.add(t);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

}
