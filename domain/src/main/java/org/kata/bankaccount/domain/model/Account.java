package org.kata.bankaccount.domain.model;

import org.kata.bankaccount.domain.exception.InsufficientBalanceException;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private int balance;
    private final List<Operation> operationList;
    private final History history;

    public Account() {
        this.balance = 0;
        this.operationList = new ArrayList<>();
        this.history = new History();
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
        history.addOperation(new Operation(Operation.Type.WITHDRAW, balance, -1 * withdrawalAmount));
    }

    public List<Operation> getOperationsList() {
        return operationList;
    }

    public History getHistory() {
        return history;
    }
}
