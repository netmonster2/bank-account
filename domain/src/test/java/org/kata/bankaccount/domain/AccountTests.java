package org.kata.bankaccount.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kata.bankaccount.domain.model.Account;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTests {

    @DisplayName("When I make a deposit in my account, the account balance increases")
    @Test
    public void moneyDeposit() {
        int randomDepositAmount = ThreadLocalRandom.current().nextInt(10, 1000);
        Account bankAccount = new Account(0);
        bankAccount.deposit(randomDepositAmount);
        assertEquals(randomDepositAmount, bankAccount.getBalance(),
                () -> String.format("The account balance didn't increase of %s after deposit", randomDepositAmount));
    }
}

