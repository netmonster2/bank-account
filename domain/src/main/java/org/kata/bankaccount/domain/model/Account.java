package org.kata.bankaccount.domain.model;

import org.kata.bankaccount.domain.exception.InsufficientBalanceException;

public class Account {
    private int balance;

    public Account() {
        this.balance = 0;
    }

    public void deposit(int depositAmount) {
        balance += depositAmount;
    }

    public int getBalance() {
        return balance;
    }

    public void withdraw(int withdrawalAmount) {
        if (withdrawalAmount > balance)
            throw new InsufficientBalanceException(balance);
        balance -= withdrawalAmount;
    }
}
