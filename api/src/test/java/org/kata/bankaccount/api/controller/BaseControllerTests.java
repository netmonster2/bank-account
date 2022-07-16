package org.kata.bankaccount.api.controller;

import org.kata.bankaccount.domain.port.spi.BankAccPersistencePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
public class BaseControllerTests {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected BankAccPersistencePort bankAccPersistencePort;


}
