package com.zeta.bankwithlambda;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionExample {
    private static List<BankAccount> accountsList = new ArrayList<>();

    private static BankAccount findAccount(int accountNumber) {
        return accountsList.stream()
                .filter(acc -> acc.getAccountNumber() == accountNumber)
                .findFirst()
                .orElse(null);
    }

    private static BankAccount CreateAccount(int accountNumber, Scanner sc) {
        BankAccount account = findAccount(accountNumber);
        if (account != null) {
            System.out.println("Account found! Balance: ₹" + account.getBalance());
            return account;
        } else {
            System.out.println("Account not found. Creating new account with initial balance 0.");
            BankAccount newAccount = new BankAccount(0, accountNumber);
            accountsList.add(newAccount);
            return newAccount;
        }
    }

    private static void displayTransactions(BankAccount account) {
        System.out.println("\n--- Transactions of Account " + account.getAccountNumber() + " ---");
        List<Transaction> transactions = account.getTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction.getTimestamp() + "    Amount: ₹" + transaction.getAmount() + 
                        "    Other Acc: " + transaction.getOtherAccountNumber() + "      " + transaction.getStatus());
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Bank Transaction System ===");
        System.out.print("Enter your account number: ");
        int account1Number = sc.nextInt();

        BankAccount account1 = CreateAccount(account1Number, sc);

        while (true) {
            System.out.println("\n--- Account Menu ---");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. View Transactions");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1:
                        System.out.println("Current Balance: ₹" + account1.getBalance());
                        break;

                    case 2:
                        System.out.print("Enter amount to deposit: ₹");
                        int depositAmount = sc.nextInt();
                        if (depositAmount > 0) {
                            account1.deposit(depositAmount);
                            System.out.println("Deposit successful! New balance: ₹" + account1.getBalance());
                        } else {
                            System.out.println("Invalid amount!");
                        }
                        break;

                    case 3:
                        System.out.print("Enter amount to withdraw: ₹");
                        int withdrawAmount = sc.nextInt();
                        if (withdrawAmount > 0) {
                            account1.withdraw(withdrawAmount);
                            System.out.println("Withdrawal successful! New balance: ₹" + account1.getBalance());
                        } else {
                            System.out.println("Invalid amount!");
                        }
                        break;

                    case 4:
                        System.out.print("Enter recipient account number: ");
                        int account2Number = sc.nextInt();
                        BankAccount account2 = findAccount(account2Number);

                        if (account2 == null) {
                            System.out.print("Account not found. Do you want to create it? (yes/no): ");
                            String confirm = sc.next();
                            if (confirm.equalsIgnoreCase("yes")) {
                                account2 = new BankAccount(0, account2Number);
                                accountsList.add(account2);
                                System.out.println("New account created with initial balance 0.");
                            } else {
                                System.out.println("Transfer cancelled.");
                                break;
                            }
                        }

                        System.out.print("Enter transfer amount: ₹");
                        int transferAmount = sc.nextInt();
                        if (transferAmount > 0) {
                            try {
                                Thread t = new Thread(new TransferTask(account1, account2, transferAmount));
                                t.start();
                                t.join();
                                System.out.println("Transfer successful!");
                                System.out.println("Your Balance: ₹" + account1.getBalance());
                                System.out.println("Recipient Balance: ₹" + account2.getBalance());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("Invalid amount!");
                        }
                        break;

                    case 5:
                        displayTransactions(account1);
                        break;

                    case 6:
                        System.out.println("Thank you for using Bank Transaction System. Goodbye!");
                        sc.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice! Try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}

