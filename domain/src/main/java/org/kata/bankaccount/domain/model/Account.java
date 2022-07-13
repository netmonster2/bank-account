package org.kata.bankaccount.domain.model;

import org.kata.bankaccount.domain.exception.InsufficientBalanceException;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private int balance;
    private final List<Operation> operations;

    public Account() {
        this.balance = 0;
        this.operations = new ArrayList<>();
    }

    public void deposit(int depositAmount) {
        balance += depositAmount;
        operations.add(new Operation());
    }

    public int getBalance() {
        return balance;
    }

    public void withdraw(int withdrawalAmount) {
        if (withdrawalAmount > balance)
            throw new InsufficientBalanceException(balance);
        balance -= withdrawalAmount;
        operations.add(new Operation());
    }

    public List<Operation> getOperations() {
        return operations;
    }
}
