package org.kata.bankaccount.domain.model;

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
        balance -= withdrawalAmount;
    }
}
