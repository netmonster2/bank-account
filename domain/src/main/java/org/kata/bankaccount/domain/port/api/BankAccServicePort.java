package org.kata.bankaccount.domain.port.api;

import org.kata.bankaccount.domain.model.Account;

public interface BankAccServicePort {

    /**
     * @return the bank account domain model
     */
    Account getBankAccount();
}
