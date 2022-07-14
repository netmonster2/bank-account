package org.kata.bankaccount.domain.port.api.impl;

import org.kata.bankaccount.domain.model.Account;
import org.kata.bankaccount.domain.port.api.BankAccServicePort;
import org.kata.bankaccount.domain.port.spi.BankAccPersistencePort;

public class BankAccServiceImpl implements BankAccServicePort {

    private final BankAccPersistencePort bankAccPersistencePort;

    private Account account;

    public BankAccServiceImpl(BankAccPersistencePort bankAccPersistencePort) {
        this.bankAccPersistencePort = bankAccPersistencePort;
    }

    @Override
    public Account getBankAccount() {
        if (account == null)
            account = new Account(bankAccPersistencePort);
        return account;
    }
}
