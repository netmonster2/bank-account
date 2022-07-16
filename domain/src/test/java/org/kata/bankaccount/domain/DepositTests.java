package org.kata.bankaccount.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kata.bankaccount.domain.util.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepositTests extends BaseTests {

    @DisplayName("When I make a deposit in my account, the account balance increases")
    @Test
    public void moneyDeposit() {
        int randomDepositAmount = TestUtils.getRandomInt(10, 1000);

        int oldBalance = bankAccount.getBalance();

        int newBalance = bankAccount.deposit(randomDepositAmount).getBalance();

        assertEquals(randomDepositAmount, newBalance - oldBalance,
                () -> String.format("The account balance didn't increase of %s after deposit. " +
                        "The new account balance is incorrect", randomDepositAmount));
    }
}

