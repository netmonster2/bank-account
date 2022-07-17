package org.kata.bankaccount.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kata.bankaccount.domain.exception.InsufficientBalanceException;
import org.kata.bankaccount.domain.exception.InvalidOperationAmountException;
import org.kata.bankaccount.domain.util.TestUtils;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WithdrawalTests extends BaseTests {

    @DisplayName("When I withdraw from my account and I have a sufficient balance, the account balance decreases")
    @Test
    public void moneyWithdrawalSufficientBalance() {
        int randomInitialDeposit = TestUtils.getRandomInt(500, 1000);
        int randomWithdrawalAmount = TestUtils.getRandomInt(10, 400);

        bankAccount.deposit(randomInitialDeposit);
        int newBalance = bankAccount.withdraw(randomWithdrawalAmount).getBalance();

        assertEquals(randomInitialDeposit - randomWithdrawalAmount, newBalance,
                () -> String.format("The account balance didn't decrease of %s after withdrawal. " +
                        "The new account balance is incorrect", randomWithdrawalAmount));
    }

    @DisplayName("When I withdraw from my account and I have an insufficient balance, an exception is raised")
    @Test
    public void moneyWithdrawalInsufficientBalance() {
        int randomInitialDeposit = TestUtils.getRandomInt(10, 400);
        int randomWithdrawalAmount = TestUtils.getRandomInt(500, 1000);

        int balanceBeforeWithdraw = bankAccount.deposit(randomInitialDeposit).getBalance();

        assertAll("The withdrawal should raise an exception and the account balance should stay untouched",
                () -> assertThrows(InsufficientBalanceException.class,
                        () -> bankAccount.withdraw(randomWithdrawalAmount),
                        "The withdrawal didn't raise an exception with an insufficient balance"),
                () -> assertEquals(balanceBeforeWithdraw, bankAccount.getBalance(),
                        "The account balance after withdrawal with an insufficient balance has changed. "));
    }

    @DisplayName("When I withdraw from my account a 0 amount, an exception is raised")
    @Test
    public void moneyWithdraw0AmountException() {
        int withdrawAmount = 0;

        assertThrows(InvalidOperationAmountException.class, () -> bankAccount.withdraw(withdrawAmount),
                "The withdrawal didn't raise an exception with 0 amount");
    }

    @DisplayName("When I withdraw from my account a negative amount, an exception is raised")
    @Test
    public void moneyWithdrawNegativeAmountException() {
        int withdrawAmount = -100;

        assertThrows(InvalidOperationAmountException.class, () -> bankAccount.withdraw(withdrawAmount),
                "The withdrawal didn't raise an exception with negative amount");
    }
}

