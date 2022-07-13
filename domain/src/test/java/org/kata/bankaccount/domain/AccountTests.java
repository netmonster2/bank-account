package org.kata.bankaccount.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kata.bankaccount.domain.exception.InsufficientBalanceException;
import org.kata.bankaccount.domain.model.Account;
import org.kata.bankaccount.domain.util.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTests {

    private Account bankAccount;

    @BeforeEach
    public void setUp() {
        bankAccount = new Account();
    }

    @DisplayName("When I make a deposit in my account, the account balance increases")
    @Test
    public void moneyDeposit() {
        int randomDepositAmount = TestUtils.getRandomInt(10, 1000);

        int oldBalance = bankAccount.getBalance();

        bankAccount.deposit(randomDepositAmount);
        int newBalance = bankAccount.getBalance();

        assertEquals(randomDepositAmount, newBalance - oldBalance,
                () -> String.format("The account balance didn't increase of %s after deposit. " +
                        "The new account balance is incorrect", randomDepositAmount));
    }

    @DisplayName("When I withdraw from my account and I have a sufficient balance, the account balance decreases")
    @Test
    public void moneyWithdrawalSufficientBalance() {
        int randomInitialDeposit = TestUtils.getRandomInt(500, 1000);
        int randomWithdrawalAmount = TestUtils.getRandomInt(10, 400);

        bankAccount.deposit(randomInitialDeposit);
        bankAccount.withdraw(randomWithdrawalAmount);
        int newBalance = bankAccount.getBalance();

        assertEquals(randomInitialDeposit - randomWithdrawalAmount, newBalance,
                () -> String.format("The account balance didn't decrease of %s after withdrawal. " +
                        "The new account balance is incorrect", randomWithdrawalAmount));
    }

    @DisplayName("When I withdraw from my account and I have an insufficient balance, an exception is raised")
    @Test
    public void moneyWithdrawalInsufficientBalance() {
        int randomInitialDeposit = TestUtils.getRandomInt(10, 400);
        int randomWithdrawalAmount = TestUtils.getRandomInt(500, 1000);

        bankAccount.deposit(randomInitialDeposit);

        assertThrows(InsufficientBalanceException.class,
                () -> bankAccount.withdraw(randomWithdrawalAmount),
                "The withdrawal didn't raise an exception with an insufficient balance");

        int newBalance = bankAccount.getBalance();

        assertEquals(randomInitialDeposit - randomWithdrawalAmount, newBalance,
                () -> String.format("The account balance didn't decrease of %s after withdrawal. " +
                        "The new account balance is incorrect", randomWithdrawalAmount));
    }
}

