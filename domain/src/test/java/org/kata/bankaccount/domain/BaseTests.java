package org.kata.bankaccount.domain;

import org.junit.jupiter.api.BeforeEach;
import org.kata.bankaccount.domain.model.Account;
import org.kata.bankaccount.domain.port.api.BankAccServicePort;
import org.kata.bankaccount.domain.port.api.impl.BankAccServiceImpl;
import org.kata.bankaccount.domain.stub.BankAccPersistenceStub;

public class BaseTests {
    protected Account bankAccount;

    @BeforeEach
    public void setUp() {
        BankAccServicePort bankAccServicePort = new BankAccServiceImpl(new BankAccPersistenceStub());
        bankAccount = bankAccServicePort.getBankAccount();
    }
}
