package com.zeta.bankwithlambda;

public class TransferTask implements Runnable {

    private final BankAccount sender;
    private final BankAccount receiver;
    private final int amount;

    public TransferTask(BankAccount sender, BankAccount receiver, int amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }

    @Override
    public void run() {
        synchronized (sender) {
            if (sender.getBalance() >= amount) {

                sender.withdraw(amount);
                receiver.deposit(amount);

                sender.addTransaction(new Transaction(
                        Transaction.Status.SUCCESS,
                        -amount,
                        receiver.getAccountNumber()
                ));
                receiver.addTransaction(new Transaction(
                        Transaction.Status.SUCCESS,
                        amount,
                        sender.getAccountNumber()
                ));

            } else {
                sender.addTransaction(new Transaction(
                        Transaction.Status.FAILED,
                        0,
                        receiver.getAccountNumber()
                ));
            }
        }
    }
}
