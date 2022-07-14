package org.kata.bankaccount.domain.model;

import org.kata.bankaccount.domain.exception.InsufficientBalanceException;

public class Account {
    private final History history;

    public Account() {
        this.history = new History();
    }

    public int deposit(int depositAmount) {
        return history.addDeposit(depositAmount);
    }

    public int withdraw(int withdrawalAmount) {
        int currentBalance = getBalance();
        if (withdrawalAmount > currentBalance)
            throw new InsufficientBalanceException(currentBalance);

        return history.addWithdrawal(withdrawalAmount);
    }

    public int getBalance() {
        return history.calculateBalance();
    }

    public History getHistory() {
        return history;
    }
}
