package com.zeta.bankwithlambda;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class BankingApp {
    static Consumer<String> logger = msg ->
            System.out.println(Thread.currentThread().getName() + " Thread:" + msg);
    static Consumer<BankAccount> lowBalanceAlert = account -> {
        if (account.getBalance() < 1000) {
            logger.accept("Warning: Low balance!");
        }
    };

    private static int readInt(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number:");
            sc.next();
        }
        return sc.nextInt();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter account Number:");
        int accountNumber = readInt(sc);
        System.out.print("Enter initial bank balance: ₹");
        int initialBalance = readInt(sc);

        while (initialBalance < 0) {
            System.out.println("Initial balance must be non-negative. Enter again:");
            initialBalance = readInt(sc);
        }

        Map<Integer, BankAccount> accounts = new HashMap<>();

        BankAccount[] current = new BankAccount[]{new BankAccount(initialBalance, accountNumber)};
        accounts.put(accountNumber, current[0]);
        int currentAccountNumber = accountNumber;

        ExecutorService executor = Executors.newFixedThreadPool(3);

        Consumer<BankAccount> accountSummary = acc ->
                System.out.println("Acc No: " + acc.getAccountNumber() +
                        " | Balance: ₹" + acc.getBalance());

        while (true) {
            System.out.println("Current Account: " + currentAccountNumber);
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Simulate Parallel Withdrawals");
            System.out.println("5. Request Loan");
            System.out.println("6. Show Loan Details");
            System.out.println("7. Pay Loan EMI");
            System.out.println("8. Close Loan");
            System.out.println("9. Show Account Details");
            System.out.println("10. Create Account");
            System.out.println("11. Switch Account");
            System.out.println("12. Exit");
            System.out.print("Enter your choice: ");

            int choice = readInt(sc);
            try {
                switch (choice) {
                    case 1:
                        logger.accept("Balance checked. Current balance: ₹" + current[0].getBalance());
                        break;

                    case 2:
                        System.out.print("Enter amount to deposit: ₹");
                        int dep = readInt(sc);
                        Validator.validate(dep);
                        logger.accept("Deposit Successful: ₹" + dep);
                        executor.execute(() -> current[0].deposit(dep));
                        break;

                    case 3:
                        System.out.print("Enter amount to withdraw: ₹");
                        int withdraw = readInt(sc);
                        Validator.validate(withdraw);
                        executor.execute(() -> {
                            current[0].withdraw(withdraw);
                            lowBalanceAlert.accept(current[0]);
                        });
                        logger.accept("Withdraw requested: ₹" + withdraw);
                        break;

                    case 4: {
                        int half = current[0].getBalance() / 2;
                        System.out.println("Simulating two parallel withdrawals of ₹" + half);
                        executor.execute(new WithdrawTask(current[0], half));
                        executor.execute(new WithdrawTask(current[0], half));
                        break;
                    }

                    case 5:
                        System.out.println("Enter the Amount you require for loan: ");
                        int amount = readInt(sc);
                        System.out.println("Enter the tenure in months");
                        int tenure = readInt(sc);
                        System.out.println("Enter the Interest Rate");
                        int interestRate = readInt(sc);
                        current[0].issueLoan(tenure, amount, interestRate);
                        logger.accept("Loan Issued Successfully");
                        break;

                    case 6:
                        current[0].showLoanDetails();
                        logger.accept("Loan Details Requested");
                        break;

                    case 7:
                        System.out.print("Enter EMI amount to pay: ₹");
                        double emiAmount = readInt(sc);
                        current[0].payLoanEMI(emiAmount);
                        break;

                    case 8:
                        current[0].closeLoan();
                        break;

                    case 9:
                        accountSummary.accept(current[0]);
                        break;

                    case 10:
                        System.out.print("Enter new account Number: ");
                        int newAccountNumber = readInt(sc);
                        if (accounts.containsKey(newAccountNumber)) {
                            System.out.println("Account number already exists.");
                            break;
                        }
                        System.out.print("Enter initial bank balance: ₹");
                        int newInitBalance = readInt(sc);
                        BankAccount newAcc = new BankAccount(newInitBalance, newAccountNumber);
                        accounts.put(newAccountNumber, newAcc);
                        logger.accept("Account created: " + newAccountNumber);
                        break;

                    case 11:
                        System.out.print("Enter account Number to switch to: ");
                        int switchAcc = readInt(sc);
                        if (!accounts.containsKey(switchAcc)) {
                            System.out.println("Account not found.");
                            break;
                        }
                        current[0] = accounts.get(switchAcc);
                        currentAccountNumber = switchAcc;
                        logger.accept("Switched to account: " + switchAcc);
                        break;

                    case 12:
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