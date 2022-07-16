package org.kata.bankaccount.api.config;

import org.kata.bankaccount.domain.port.api.BankAccServicePort;
import org.kata.bankaccount.domain.port.api.impl.BankAccServiceImpl;
import org.kata.bankaccount.domain.port.spi.BankAccPersistencePort;
import org.kata.bankaccount.persistence.adapter.BankAccPersistenceImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "org.kata.bankaccount.persistence")
@EntityScan(basePackages = "org.kata.bankaccount.persistence")
public class ApiConfig {

    @Bean("bankAccPersistenceImpl")
    public BankAccPersistencePort bankAccPersistence() {
        return new BankAccPersistenceImpl();
    }

    @Bean
    public BankAccServicePort bankAccService() {
        return new BankAccServiceImpl(bankAccPersistence());
    }
}
