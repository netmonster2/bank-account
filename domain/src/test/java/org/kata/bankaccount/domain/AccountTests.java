package org.kata.bankaccount.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kata.bankaccount.domain.exception.InsufficientBalanceException;
import org.kata.bankaccount.domain.model.Account;
import org.kata.bankaccount.domain.model.Operation;
import org.kata.bankaccount.domain.util.TestUtils;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        int newBalance = bankAccount.deposit(randomDepositAmount);

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
        int newBalance = bankAccount.withdraw(randomWithdrawalAmount);

        assertEquals(randomInitialDeposit - randomWithdrawalAmount, newBalance,
                () -> String.format("The account balance didn't decrease of %s after withdrawal. " +
                        "The new account balance is incorrect", randomWithdrawalAmount));
    }

    @DisplayName("When I withdraw from my account and I have an insufficient balance, an exception is raised")
    @Test
    public void moneyWithdrawalInsufficientBalance() {
        int randomInitialDeposit = TestUtils.getRandomInt(10, 400);
        int randomWithdrawalAmount = TestUtils.getRandomInt(500, 1000);

        int balanceBeforeWithdraw = bankAccount.deposit(randomInitialDeposit);

        assertAll("The withdrawal should raise an exception and the account balance should stay untouched",
                () -> assertThrows(InsufficientBalanceException.class,
                        () -> bankAccount.withdraw(randomWithdrawalAmount),
                        "The withdrawal didn't raise an exception with an insufficient balance"),
                () -> assertEquals(balanceBeforeWithdraw, bankAccount.getBalance(),
                        "The account balance after withdrawal with an insufficient balance has changed. "));
    }

    @DisplayName("When I do some operations on my account, the history size follows")
    @Test
    public void checkHistoryOperationsSize() {
        int randomFirstDeposit = TestUtils.getRandomInt(400, 500);
        int randomWithdrawal = TestUtils.getRandomInt(10, 300);
        int randomSecondDeposit = TestUtils.getRandomInt(10, 500);

        bankAccount.deposit(randomFirstDeposit);
        bankAccount.withdraw(randomWithdrawal);
        bankAccount.deposit(randomSecondDeposit);

        assertEquals(3, bankAccount.getHistory().getOperationList().size(),
                "The number of account operations is incorrect");
    }

    @DisplayName("When I withdraw from my account and I have a sufficient balance, it will be the last operation")
    @Test
    public void lastOperationAfterWithdrawal() {
        int randomInitialDeposit = TestUtils.getRandomInt(500, 1000);
        int randomWithdrawalAmount = TestUtils.getRandomInt(10, 400);
        Date startDate = new Date();

        bankAccount.deposit(randomInitialDeposit);
        bankAccount.withdraw(randomWithdrawalAmount);

        int expectedBalance = randomInitialDeposit - randomWithdrawalAmount;
        Date endDate = new Date();

        Operation lastOperation = bankAccount.getHistory().getLastOperation();

        assertAll("The last operation details needs to be the same as the withdrawal",
                () -> assertEquals(lastOperation.getAmount(), -1 * randomWithdrawalAmount,
                        "The last operation amount is incorrect"),
                () -> assertEquals(lastOperation.getType(), Operation.Type.WITHDRAW,
                        "The last operation type is incorrect"),
                () -> assertEquals(expectedBalance, lastOperation.getBalance(),
                        "The last operation balance is incorrect"),
                () -> assertTrue(lastOperation.getDate().equals(startDate)
                                || lastOperation.getDate().equals(endDate)
                                || (lastOperation.getDate().after(startDate)
                                && lastOperation.getDate().before(endDate)),
                        () -> String.format("The last operation date is incorrect. It should be between [%s and %s]. " +
                                "Actual value is: %s", startDate, endDate, lastOperation.getDate()))
        );
    }
}

