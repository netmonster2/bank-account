package org.kata.bankaccount.api;

import org.kata.bankaccount.domain.port.api.BankAccServicePort;
import org.kata.bankaccount.domain.port.api.impl.BankAccServiceImpl;
import org.kata.bankaccount.domain.port.spi.BankAccPersistencePort;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class ApiTestConfiguration {

    @MockBean(name = "bankAccPersistenceImpl")
    private BankAccPersistencePort bankAccPersistencePort;

    public BankAccServicePort servicePort() {
        return new BankAccServiceImpl(bankAccPersistencePort);
    }

}
