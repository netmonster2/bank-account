package org.kata.bankaccount.api.controller;

import org.kata.bankaccount.domain.exception.InvalidOperationAmountException;
import org.kata.bankaccount.domain.port.spi.BankAccPersistencePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.ExceptionHandler;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
public class BaseControllerTests {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected BankAccPersistencePort bankAccPersistencePort;

    @ExceptionHandler(InvalidOperationAmountException.class)
    public void handleOperationException(InvalidOperationAmountException exception) {

    }


}
