package org.kata.bankaccount.domain.exception;

public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(int balance) {
        super(String.format("Account balance is insufficient. Actual balance is: %s", balance));
    }
}
