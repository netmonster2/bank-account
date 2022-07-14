package org.kata.bankaccount.domain.model;

import org.kata.bankaccount.domain.exception.InsufficientBalanceException;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private int balance;
    private final List<Operation> operationList;

    public Account() {
        this.balance = 0;
        this.operationList = new ArrayList<>();
    }

    public void deposit(int depositAmount) {
        balance += depositAmount;
        operationList.add(new Operation());
    }

    public int getBalance() {
        return balance;
    }

    public void withdraw(int withdrawalAmount) {
        if (withdrawalAmount > balance)
            throw new InsufficientBalanceException(balance);
        balance -= withdrawalAmount;
        operationList.add(new Operation());
    }

    public List<Operation> getOperationsList() {
        return operationList;
    }

    public History getHistory() {
        return new History();
    }
}
