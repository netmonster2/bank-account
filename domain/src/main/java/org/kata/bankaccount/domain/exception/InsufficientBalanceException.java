package org.kata.bankaccount.domain.exception;

/**
 * Thrown to indicate that the account balance is insufficient for the actual operation.
 */
public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(int balance) {
        super(String.format("Account balance is insufficient. Actual balance is: %s", balance));
    }
}
