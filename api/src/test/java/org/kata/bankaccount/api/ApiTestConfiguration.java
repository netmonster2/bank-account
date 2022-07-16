package org.kata.bankaccount.api;

import org.kata.bankaccount.domain.port.api.BankAccServicePort;
import org.kata.bankaccount.domain.port.api.impl.BankAccServiceImpl;
import org.kata.bankaccount.domain.port.spi.BankAccPersistencePort;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ApiTestConfiguration {

    @MockBean
    private BankAccPersistencePort bankAccPersistencePort;

    @Bean
    public BankAccServicePort servicePort() {
        return new BankAccServiceImpl(bankAccPersistencePort);
    }

}
