package com.zeta;

public class BankAccount {
    private int balance;
    LoanAccount loan;
    int accountNumber;

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

    public void issueLoan(int tenure, int principle, int interestRate) {

        if (loan != null && loan.getStatus() == LoanStatus.ACTIVE) {  // FIXED
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

    public void validateAmount(float amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
    }

    public synchronized int getBalance() {
        return this.balance;
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
    public synchronized void payLoanEMI(double amount) {
        if (loan == null || loan.getStatus() == LoanStatus.CLOSED) {
            throw new IllegalStateException("No active loans found.");
        }

        if (balance < amount) {
            throw new IllegalArgumentException("Not enough balance to pay EMI.");
        }

        balance -= amount;
        loan.payEMI(amount);
    }

}
