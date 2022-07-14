package org.kata.bankaccount.domain.model;

import org.kata.bankaccount.domain.exception.InsufficientBalanceException;
import org.kata.bankaccount.domain.port.spi.BankAccPersistencePort;

public class Account {
    private final History history;

    public Account(BankAccPersistencePort bankAccPersistencePort) {
        this.history = new History(bankAccPersistencePort);
    }

    public Operation deposit(int depositAmount) {
        return history.addDeposit(depositAmount);
    }

    public Operation withdraw(int withdrawalAmount) {
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
