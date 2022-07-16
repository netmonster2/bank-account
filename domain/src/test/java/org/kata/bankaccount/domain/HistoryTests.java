package org.kata.bankaccount.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kata.bankaccount.domain.exception.InsufficientBalanceException;
import org.kata.bankaccount.domain.model.Operation;
import org.kata.bankaccount.domain.util.TestUtils;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HistoryTests extends BaseTests {

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

        Operation lastOperation = bankAccount.getHistory().getLatestOperation();
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

    @DisplayName("When I withdraw from my account and I have an insufficient balance, no operation is added")
    @Test
    public void lastOperationAfterWithdrawalInsufficientBalance() {
        int randomInitialDeposit = TestUtils.getRandomInt(10, 400);
        int randomWithdrawalAmount = TestUtils.getRandomInt(500, 1000);

        bankAccount.deposit(randomInitialDeposit);

        assertAll("There should be no added operation after the initial deposit",
                () -> assertThrows(InsufficientBalanceException.class,
                        () -> bankAccount.withdraw(randomWithdrawalAmount),
                        "The withdrawal didn't raise an exception with an insufficient balance"),
                () -> assertEquals(1, bankAccount.getHistory().getOperationList().size(),
                        "History operations size is incorrect"));
    }

    @DisplayName("When I make a deposit in my account, it will be the last operation")
    @Test
    public void lastOperationAfterDeposit() {
        int randomInitialDeposit = TestUtils.getRandomInt(500, 1000);
        int randomWithdrawalAmount = TestUtils.getRandomInt(10, 400);
        Date startDate = new Date();

        bankAccount.deposit(randomInitialDeposit);
        bankAccount.withdraw(randomWithdrawalAmount);
        bankAccount.deposit(randomInitialDeposit);

        int expectedBalance = 2 * randomInitialDeposit - randomWithdrawalAmount;
        Date endDate = new Date();

        Operation lastOperation = bankAccount.getHistory().getLatestOperation();
        assertAll("The last operation details needs to be the same as the deposit",
                () -> assertEquals(lastOperation.getAmount(), randomInitialDeposit,
                        "The last operation amount is incorrect"),
                () -> assertEquals(lastOperation.getType(), Operation.Type.DEPOSIT,
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

    @DisplayName("When I make no operations in my account, the history is empty")
    @Test
    public void emptyOperationsHistory() {
        assertAll("The operations history needs to be empty",
                () -> assertEquals(0, bankAccount.getHistory().getOperationList().size(),
                        "The operations history is not empty."),
                () -> assertNull(bankAccount.getHistory().getLatestOperation(),
                        "The last operation is not null")
        );
    }
}

