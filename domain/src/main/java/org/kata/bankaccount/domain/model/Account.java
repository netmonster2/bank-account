package org.kata.bankaccount.domain.model;

public class Account {
    private int initialBalance;

    public Account(int initialBalance) {
        this.initialBalance = initialBalance;
    }

    public void deposit(int depositAmount) {
        initialBalance += depositAmount;
    }

    public int getBalance() {
        return initialBalance;
    }
}
