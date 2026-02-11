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
    private static int readPositiveInt(Scanner sc) {
        int number;

        while (true) {
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input. Please enter a positive number: ");
                sc.next();
            }

            number = sc.nextInt();

            if (number > 0) {
                return number;
            } else {
                System.out.print("Number must be greater than 0. Try again: ");
            }
        }
    }
    private static void handleCheckBalance(BankAccount account) {
        logger.accept("Balance checked. Current balance: ₹" + account.getBalance());
    }

    private static void handleDeposit(Scanner sc, ExecutorService executor, BankAccount account) {
        System.out.print("Enter amount to deposit: ₹");
        int dep = readPositiveInt(sc);
        Validator.validate(dep);
        logger.accept("Deposit Successful: ₹" + dep);
        executor.execute(() -> account.deposit(dep));
    }

    private static void handleWithdraw(Scanner sc, ExecutorService executor, BankAccount account) {
        System.out.print("Enter amount to withdraw: ₹");
        int withdraw = readPositiveInt(sc);
        Validator.validate(withdraw);
        executor.execute(() -> {
            account.withdraw(withdraw);
            lowBalanceAlert.accept(account);
        });
        logger.accept("Withdraw requested: ₹" + withdraw);
    }

    private static void handleParallelWithdrawals(ExecutorService executor, BankAccount account) {
        int half = account.getBalance() / 2;
        System.out.println("Simulating two parallel withdrawals of ₹" + half);
        executor.execute(new WithdrawTask(account, half));
        executor.execute(new WithdrawTask(account, half));
    }

    private static void handleRequestLoan(Scanner sc, BankAccount account) {
        System.out.println("Enter the Amount you require for loan: ");
        int amount = readPositiveInt(sc);
        System.out.println("Enter the tenure in months");
        int tenure = readPositiveInt(sc);
        System.out.println("Enter the Interest Rate");
        int interestRate = readPositiveInt(sc);
        account.issueLoan(tenure, amount, interestRate);
        logger.accept("Loan Issued Successfully");
    }

    private static void handleShowLoanDetails(BankAccount account) {
        account.showLoanDetails();
        logger.accept("Loan Details Requested");
    }

    private static void handlePayLoanEMI(Scanner sc, BankAccount account) {
        System.out.print("Enter EMI amount to pay: ₹");
        double emiAmount = readPositiveInt(sc);
        account.payLoanEMI(emiAmount);
    }

    private static void handleCloseLoan(BankAccount account) {
        account.closeLoan();
    }

    private static void handleCreateAccount(Scanner sc, Map<Integer, BankAccount> accounts) {
        System.out.print("Enter new account Number: ");
        int newAccountNumber = readPositiveInt(sc);
        if (accounts.containsKey(newAccountNumber)) {
            System.out.println("Account number already exists.");
            return;
        }
        System.out.print("Enter initial bank balance: ₹");
        int newInitBalance = readPositiveInt(sc);
        BankAccount newAcc = new BankAccount(newInitBalance, newAccountNumber);
        accounts.put(newAccountNumber, newAcc);
        logger.accept("Account created: " + newAccountNumber);
    }

    private static BankAccount handleSwitchAccount(Scanner sc, Map<Integer, BankAccount> accounts) {
        System.out.print("Enter account Number to switch to: ");
        int switchAcc = readPositiveInt(sc);
        if (!accounts.containsKey(switchAcc)) {
            System.out.println("Account not found.");
            return null;
        }
        logger.accept("Switched to account: " + switchAcc);
        return accounts.get(switchAcc);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter account Number:");
        int accountNumber = readPositiveInt(sc);
        System.out.print("Enter initial bank balance: ₹");
        int initialBalance = readPositiveInt(sc);

        while (initialBalance < 0) {
            System.out.println("Initial balance must be non-negative. Enter again:");
            initialBalance = readPositiveInt(sc);
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

            int choice = readPositiveInt(sc);
            try {
                switch (choice) {
                    case 1:handleCheckBalance(current[0]);break;
                    case 2:handleDeposit(sc, executor, current[0]);break;
                    case 3:handleWithdraw(sc, executor, current[0]);break;
                    case 4:handleParallelWithdrawals(executor, current[0]);break;
                    case 5:handleRequestLoan(sc, current[0]);break;
                    case 6:handleShowLoanDetails(current[0]);break;
                    case 7:handlePayLoanEMI(sc, current[0]);break;
                    case 8:handleCloseLoan(current[0]);break;
                    case 9:accountSummary.accept(current[0]);break;
                    case 10:handleCreateAccount(sc, accounts);break;
                    case 11:
                        BankAccount switched = handleSwitchAccount(sc, accounts);
                        if (switched != null) {
                            current[0] = switched;
                            currentAccountNumber = switched.getAccountNumber();
                        }
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