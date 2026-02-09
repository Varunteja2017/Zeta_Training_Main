package com.zeta.bankwithlambda;

public class LoanAccount {

    private int tenure;
    private int interestRate;
    private int principal;
    private LoanStatus status;
    private double remainingBalance;
    private double emi;

    public LoanAccount(int tenure, int principal, int interestRate) {
        this.tenure = tenure;
        this.principal = principal;
        this.interestRate = interestRate;
        this.status = LoanStatus.ACTIVE;
        calculateEMI();
        this.remainingBalance = emi * tenure;
    }

    private void calculateEMI() {
        double monthlyRate = (interestRate / 100.0) / 12.0;
        emi = (principal * monthlyRate * Math.pow(1 + monthlyRate, tenure)) /
                (Math.pow(1 + monthlyRate, tenure) - 1);
    }

    public synchronized void payEMI(double amount) {
        if (status == LoanStatus.CLOSED) {
            throw new IllegalStateException("Loan already closed.");
        }

        if (amount < emi) {
            throw new IllegalArgumentException("Minimum EMI to be paid: ₹" + emi);
        }

        remainingBalance -= amount;

        if (remainingBalance <= 0) {
            remainingBalance = 0;
            status = LoanStatus.CLOSED;
            System.out.println("🎉 Loan fully paid and closed!");
        } else {
            System.out.println("EMI paid. Remaining loan balance: ₹" + remainingBalance);
        }
    }

    public double getEmi() { return emi; }
    public double getRemainingBalance() { return remainingBalance; }
    public LoanStatus getStatus() {
        return status;
    }
    public void closeLoan() {
        this.status = LoanStatus.CLOSED;
    }

}




