package org.kata.bankaccount.api.controller;

import org.kata.bankaccount.domain.port.api.BankAccServicePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected BankAccServicePort bankAccServicePort;

}
