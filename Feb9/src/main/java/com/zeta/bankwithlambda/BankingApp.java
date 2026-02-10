package com.zeta.bankwithlambda;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
public class BankingApp {
    static Consumer<String> logger = msg ->
            System.out.println(Thread.currentThread().getName() + msg);
    static Consumer<BankAccount> lowBalanceAlert = account -> {
        if (account.getBalance() < 1000) {
            logger.accept("Warning: Low balance!");
        }
    };
    private static int readInt(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number:");
            sc.next(); // discard wrong input
        }
        return sc.nextInt();
    }



    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter account Number:");
        int accountNumber = readInt(sc);
        System.out.print("Enter initial bank balance: ₹");
        int initialBalance = readInt(sc);
        while (true) {
            try {
                if (initialBalance < 0) {
                    throw new IllegalArgumentException("Enter a positive initial Balance:");
                } else {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Enter Again");
                initialBalance=readInt(sc);
                e.printStackTrace();
            }
        }

        BankAccount account = new BankAccount(initialBalance, accountNumber);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        Consumer<BankAccount> accountSummary = acc ->
                System.out.println("Acc No: " + acc.getAccountNumber() +
                        " | Balance: ₹" + acc.getBalance());

        while (true) {
                System.out.println("\nBanking Service");
                System.out.println("1. Check Balance");
                System.out.println("2. Deposit Money");
                System.out.println("3. Withdraw Money");
                System.out.println("4. Simulate Parallel Withdrawals");
                System.out.println("5.Request Loan:");
                System.out.println("6. Show Loan Details");
                System.out.println("7. Pay Loan EMI");
                System.out.println("8.Close Loan");
                System.out.println("9.Show Account Details:");
                System.out.println("10. Exit");
                System.out.print("Enter your choice: ");

                int choice = readInt(sc);
                try {
                    switch (choice) {

                        case 1:
                            System.out.println("Current Balance: ₹" + account.getBalance());
                            logger.accept("Balance checked. Current balance: ₹" + account.getBalance());
                            break;

                        case 2:
                            System.out.print("Enter amount to deposit: ₹");
                            int dep = readInt(sc);
                            Validator.validate(dep);
                            logger.accept("Deposit Successful: ₹" + dep);
                            executor.execute(() -> account.deposit(dep));
                            break;

                        case 3:
                            System.out.print("Enter amount to withdraw: ₹");
                            int withdraw =readInt(sc);
                            Validator.validate(withdraw);
                            executor.execute(() -> {
                                account.withdraw(withdraw);
                                lowBalanceAlert.accept(account);
                            });


                            logger.accept("Withdraw requested: ₹" + withdraw);
                            break;

                        case 4:

                            System.out.println("Simulating two parallel withdrawals of ₹" + (initialBalance / 2));

                            executor.execute(new WithdrawTask(account, initialBalance / 2));
                            executor.execute(new WithdrawTask(account, initialBalance / 2));

                            break;
                        case 5:
                            System.out.println("Enter the Amount you required for loan: ");
                            int amount = readInt(sc);
                            System.out.println("Enter the tenure in months");
                            int tenure = readInt(sc);
                            System.out.println("Enter the Interest Rate");
                            int interestRate=readInt(sc);
                            account.issueLoan(tenure, amount, interestRate);
                            logger.accept("Loan Issued Successfully");
                            break;
                        case 6:
                            account.showLoanDetails();
                            logger.accept("Loan Details Requested");
                            break;
                        case 7:
                            System.out.print("Enter EMI amount to pay: ₹");
                            double emiAmount = readInt(sc);;
                            account.payLoanEMI(emiAmount);
                            break;
                        case 8:
                            account.closeLoan();
                            break;
                        case 9:
                            accountSummary.accept(account);
                            break;
                        case 10:
                            System.out.println("Shutting down banking system...");
                            executor.shutdown();
                            sc.close();
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Invalid choice! Try again.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();


                }


        }
    }
}